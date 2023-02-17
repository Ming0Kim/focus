package com.bb.focus.api.controller;

import com.bb.focus.api.request.EvaluationItemReq;
import com.bb.focus.api.request.EvaluationSheetReq;
import com.bb.focus.api.response.EvaluationSheetItemRes;
import com.bb.focus.api.response.EvaluationSheetRes;
import com.bb.focus.api.service.DataProcessService;
import com.bb.focus.api.service.EvaluationPaperService;
import com.bb.focus.common.auth.FocusUserDetails;
import com.bb.focus.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "평가지 API", tags = {"EvaluationPaper"})
@RestController
@CrossOrigin("*")
@RequestMapping("/api/evaluation")
public class EvaluationPaperController {
    DataProcessService dataProcessService;
    EvaluationPaperService evaluationPaperService;


    @Autowired
    public EvaluationPaperController(DataProcessService dService, EvaluationPaperService eService){
        dataProcessService = dService;
        evaluationPaperService = eService;
    }


    @GetMapping("/sheets/list/{company-id}")
    public ResponseEntity<?> GetAllEvaluationSheet(@PathVariable(name="company-id")Long companyId){

        List<EvaluationSheetRes> evaluationSheetRes = evaluationPaperService.GetAllCompanyEvaluationSheet(companyId);
        if(evaluationSheetRes == null){
            return new ResponseEntity<String>("평가지 데이터 가져오기 실패",HttpStatus.OK);
        }
        return new ResponseEntity<List<EvaluationSheetRes>>(evaluationSheetRes,HttpStatus.OK);
    }

    @PostMapping("/sheets")
    public ResponseEntity<?> CreateSheet(
        @ApiIgnore Authentication authentication,
        @RequestBody @Valid @ApiParam(value = "평가지 이름", required = true) EvaluationSheetReq.Create evaluationSheetReq){

        FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
        Long companyAdminId = userDetails.getUser().getId();

        Long id = evaluationPaperService.CreateEvaluationSheet(companyAdminId, evaluationSheetReq.getSheetName());

        Map<String, Long> result = new HashMap<>();
        result.put("evaluation sheet id", id);

        return ResponseEntity.status(200).body(result);
    }
    @ApiOperation(value="평가지 아이템 생성", notes="기업관리자로부터 입력받은 정보로 평가지를 생성한다.")
    @PostMapping("/sheets/items/{evaluation-sheet-id}")
    public ResponseEntity<?> CreateSheetItem(
            @PathVariable(name="evaluation-sheet-id")long sheetId,
            @RequestBody @Valid @ApiParam(required = true) EvaluationItemReq evaluationItemReq){

        evaluationPaperService.CreateEvaluationItem(sheetId,evaluationItemReq);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }



    // 조회
    //평가지만 보여주는 셀

    @GetMapping("/sheets/{evaluation-sheet-id}")
    public ResponseEntity<?> GetEvaluationSheetInfo(@PathVariable(name="evaluation-sheet-id") Long sheetId){
        // id -> evaluationsheet 호출 -> list<items> -> ....
        List<EvaluationSheetItemRes> evaluationSheetItemResList =evaluationPaperService.GetEvaluationSheetItems(sheetId);
        if(evaluationSheetItemResList == null){
            return new ResponseEntity<String>("",HttpStatus.OK);
        }
        return new ResponseEntity<List<EvaluationSheetItemRes>>(evaluationSheetItemResList,HttpStatus.OK);
    }

    @GetMapping("/interview/{interview-room-id}")
    public ResponseEntity<?> GetInterviewRoomEvaluationItems(@PathVariable(name="interview-room-id")Long interviewRoomId ){

        List<EvaluationSheetItemRes> evaluationSheetItemResList=evaluationPaperService.GetRoomPerEvaluationItems(interviewRoomId);

        if(evaluationSheetItemResList == null){
            return new ResponseEntity<String>("Data Error",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<EvaluationSheetItemRes>>(evaluationSheetItemResList,HttpStatus.OK);
    }


    @PutMapping("/sheets/modifysheet/{sheet-id}")
    public ResponseEntity<?> ModifyEvaluationSheet(@PathVariable(name="sheet-id") Long sheetId, @RequestBody @Valid String sheetName){
        if(evaluationPaperService.modifyEvaluationSheet(sheetId,sheetName)){
            return new ResponseEntity<String>("",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("평가지 제목 변경 완료",HttpStatus.OK);
    }

    @PutMapping("/sheets/modifyitem/{sheet-id}/{sheetitem-id}")
    public ResponseEntity<?> ModifyEvaluationSheetItem(@PathVariable(name="sheet-id") Long sheetId,
                                                       @PathVariable(name="sheetitem-id") Long sheetItemId,
                                                       @RequestBody @Valid EvaluationItemReq itemReq)
    {
        if(!evaluationPaperService.modifyEvaluationItem(sheetId,sheetItemId,itemReq.getScore(),itemReq.getContent())){
            return new ResponseEntity<String>("평가항목 수정 실패",HttpStatus.OK);
        }

        return new ResponseEntity<String>("평가항목 수정 성공",HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping("/sheets/{evaluation-sheet-id}")
    public ResponseEntity<?> DeleteEvaluationSheet(@PathVariable(name="evaluation-sheet-id") Long sheetId)
    {
        if(evaluationPaperService.RemoveEvaluationSheet(sheetId)){
            return new ResponseEntity<String>("Delete Success", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Delete Fail",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/sheets/deleteitem/{sheetitem-id}")
    public ResponseEntity<?> DeleteEvaluationSheetItem(@PathVariable(name="sheetitem-id") Long itemId){
        evaluationPaperService.RemoveEvaluationItem(itemId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


}
