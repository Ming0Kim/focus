package com.bb.focus.common.auth;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bb.focus.api.service.ApplicantService;
import com.bb.focus.api.service.CompanyAdminService;
import com.bb.focus.api.service.EvaluatorService;
import com.bb.focus.api.service.ServiceAdminService;
import com.bb.focus.common.util.JwtTokenUtil;
import com.bb.focus.common.util.ResponseBodyWriteUtil;
import com.bb.focus.db.entity.admin.ServiceAdmin;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.evaluator.Evaluator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 요청 헤더에 jwt 토큰이 있는 경우, 토큰 검증 및 인증 처리 로직 정의.
 */

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private CompanyAdminService companyAdminService;
    private ServiceAdminService serviceAdminService;
    private ApplicantService applicantService;
    private EvaluatorService evaluatorService;

    private RedisTemplate redisTemplate;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   CompanyAdminService companyAdminService, ServiceAdminService serviceAdminService,
                                   ApplicantService applicantService, EvaluatorService evaluatorService, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.companyAdminService = companyAdminService;
        this.serviceAdminService = serviceAdminService;
        this.applicantService = applicantService;
        this.evaluatorService = evaluatorService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // Read the Authorization header, where the JWT Token should be
        String header = request.getHeader(JwtTokenUtil.HEADER_STRING);
        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // If header is present, try grab user principal from database and perform authorization
            Authentication authentication = (Authentication) getAuthentication(request).get("jwtAuthentication");
            // jwt 토큰으로 부터 획득한 인증 정보(authentication) 설정.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            ResponseBodyWriteUtil.sendError(request, response, ex);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Transactional(readOnly = true)
    public HashMap getAuthentication(HttpServletRequest request) throws Exception {
        HashMap hm = new HashMap();
        String token = request.getHeader(JwtTokenUtil.HEADER_STRING);
        hm.put("token", token);
        // 요청 헤더에 Authorization 키값에 jwt 토큰이 포함된 경우에만, 토큰 검증 및 인증 처리 로직 실행.
        if (token != null) {
            // 이미 로그아웃 한 상태인지 확인
            String isLogout = (String) redisTemplate.opsForValue().get(token);
            if (!ObjectUtils.isEmpty(isLogout)) {
                return null;
            }
            JWTVerifier verifier = JwtTokenUtil.getVerifier();
            JwtTokenUtil.handleError(token);
            DecodedJWT decodedJWT = verifier.verify(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""));
            Long sequenceId = Long.parseLong(decodedJWT.getSubject());
            if (sequenceId != null) {
                // jwt 토큰에 포함된 계정 정보(userId) 통해 실제 디비에 해당 정보의 계정이 있는지 조회.
                CompanyAdmin companyAdmin = companyAdminService.getCompanyAdminById(sequenceId);
                User user = null;
                if (companyAdmin != null) {
                    // 식별된 정상 유저인 경우, 요청 context 내에서 참조 가능한 인증 정보(jwtAuthentication) 생성.
                    user = new User(companyAdmin.getId(), companyAdmin.getUserId(), companyAdmin.getPwd(),
                            companyAdmin.getUserRole());
                } else {
                    ServiceAdmin serviceAdmin = serviceAdminService.getServiceAdminById(sequenceId);
                    if (serviceAdmin != null) {
                        user = new User(serviceAdmin.getId(), serviceAdmin.getUserId(), serviceAdmin.getPwd(),
                                serviceAdmin.getUserRole());
                    } else {
                        Applicant applicant = applicantService.getApplicantById(sequenceId);
                        if (applicant != null) {
                            user = new User(applicant.getId(), applicant.getUserId(), applicant.getPwd(),
                                    applicant.getUserRole());
                        } else {
                            Evaluator evaluator = evaluatorService.getEvaluatorById(sequenceId);
                            if (evaluator != null) {
                                user = new User(evaluator.getId(), evaluator.getUserId(), evaluator.getPwd(),
                                        evaluator.getUserRole());
                            }
                        }
                    }
                }
                FocusUserDetails userDetails = new FocusUserDetails(user);
                UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(
                        sequenceId,
                        null, userDetails.getAuthorities());
                jwtAuthentication.setDetails(userDetails);
                hm.put("jwtAuthentication", jwtAuthentication);
                return hm;
            }
            return null;
        }
        return null;
    }
}
