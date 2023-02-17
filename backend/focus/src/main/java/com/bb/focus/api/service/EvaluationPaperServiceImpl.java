package com.bb.focus.api.service;

import com.bb.focus.api.request.EvaluationItemReq;
import com.bb.focus.api.response.EvaluationSheetItemRes;
import com.bb.focus.api.response.EvaluationSheetRes;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.evaluation.EvaluationItem;
import com.bb.focus.db.entity.evaluation.EvaluationSheet;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.interview.InterviewRoom;
import com.bb.focus.db.repository.CompanyAdminRepository;
import com.bb.focus.db.repository.EvaluationSheetItemRepository;
import com.bb.focus.db.repository.EvaluationSheetRepository;
import com.bb.focus.db.repository.InterviewRoomRepository;
import com.bb.focus.db.repository.ProcessRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluationPaperServiceImpl implements EvaluationPaperService {

    EvaluationSheetRepository sheetRepo;
    CompanyAdminRepository companyRepo;
    EvaluationSheetItemRepository evaluationSheetItemRepo;
    ProcessRepository processRepo;
    InterviewRoomRepository interviewRoomRepo;
    @Autowired
    public EvaluationPaperServiceImpl(EvaluationSheetRepository evaluationSheetRepository,
        CompanyAdminRepository companyAdminRepository,
        EvaluationSheetItemRepository evaluationSheetItemRepository,
        ProcessRepository processRepository,
        InterviewRoomRepository interviewRoomRepository){

        sheetRepo= evaluationSheetRepository;
        companyRepo = companyAdminRepository;
        evaluationSheetItemRepo = evaluationSheetItemRepository;
        processRepo = processRepository;
        interviewRoomRepo = interviewRoomRepository;

    }


    @Transactional
    public Long CreateEvaluationSheet(Long companyId, String sheetInfo){
        EvaluationSheet evaluationSheet = new EvaluationSheet();
        CompanyAdmin companyAdmin = companyRepo.findById(companyId).orElseThrow(IllegalAccessError::new);
        companyAdmin.addEvaluationSheet(evaluationSheet);
        evaluationSheet.setName(sheetInfo);
        // validation check
        EvaluationSheet saved = sheetRepo.save(evaluationSheet);

        return saved.getId();
    };
    @Transactional
    public boolean CreateEvaluationItem(Long sheetId, EvaluationItemReq evaluationItemReq){
        EvaluationItem evaluationItem = new EvaluationItem();
        EvaluationSheet evaluationSheet = sheetRepo.findById(sheetId).orElseThrow(IllegalAccessError::new);
        evaluationItem.setScaleContent(evaluationItemReq.getContent());
        evaluationItem.setScoreRange(evaluationItemReq.getScore());
        evaluationSheet.addEvaluationItem(evaluationItem);
        // validation check
        evaluationSheetItemRepo.save(evaluationItem);
        return true;
    }
    public List<EvaluationSheetItemRes> GetEvaluationSheetItems(Long evaluationSheetId){

        EvaluationSheet evaluationSheet = sheetRepo.findById(evaluationSheetId).orElseThrow(IllegalAccessError::new);

        List<EvaluationItem> evaluationItemList = evaluationSheet.getEvaluationItemList();
        List<EvaluationSheetItemRes> evaluationSheetItemResList = new ArrayList<>();

        for(EvaluationItem item:evaluationItemList){
            evaluationSheetItemResList.add(new EvaluationSheetItemRes(item));
        }
        return evaluationSheetItemResList;
    }
    @Transactional
    public boolean modifyEvaluationSheet(Long evaluationSheetId, String modifyName){
        if(modifyName.equals("") || modifyName == null){
            return false;
        }
        EvaluationSheet evaluationSheet = sheetRepo.findById(evaluationSheetId).orElseThrow(IllegalAccessError::new);

        if(evaluationSheet == null) {
            return false;
        }
        evaluationSheet.setName(modifyName);
        sheetRepo.save(evaluationSheet);
        return true;
    }
    @Transactional
    public boolean modifyEvaluationItem(Long evaluationSheetId, Long evaluationItemId, byte modifyRange, String modifyContent){
        // request에 item 추가해야함.
        if(evaluationItemId == null || evaluationSheetId == null || modifyRange < 0 || modifyContent == null){
            return false;
        }
        EvaluationSheet evaluationSheet=sheetRepo.findById(evaluationSheetId).orElseThrow(IllegalAccessError::new);

        for(EvaluationItem evaluationItem:evaluationSheet.getEvaluationItemList()){
            if(evaluationItem.getId() == evaluationItemId){
                evaluationItem.setScaleContent(modifyContent);
                evaluationItem.setScoreRange(modifyRange);
                evaluationSheetItemRepo.save(evaluationItem);
                return true;
            }
        }

        return false;
    }
    public List<EvaluationSheetItemRes> GetRoomPerEvaluationItems(Long interviewRoomId){
        List<EvaluationSheetItemRes> evaluationSheetItemResList = null;
        InterviewRoom interviewRoom =interviewRoomRepo.findById(interviewRoomId).orElseThrow(IllegalAccessError::new);
        Interview interview =interviewRoom.getInterview();
        evaluationSheetItemResList = GetEvaluationSheetItems(interview.getEvaluationSheet().getId());

        return evaluationSheetItemResList;
    }

    @Transactional
    public boolean RemoveEvaluationSheet(Long evaluationSheetId){

        sheetRepo.deleteById(evaluationSheetId);
        return true;
    };

    @Transactional
    public boolean RemoveEvaluationItem(Long evaluationSheetItemId){
        evaluationSheetItemRepo.deleteById(evaluationSheetItemId);

        return true;
    }

    @Override
    public List<EvaluationSheetRes> GetAllCompanyEvaluationSheet(Long companyId){

        List<EvaluationSheet> evaluationSheetList =sheetRepo.findAllByCompanyAdminId(companyId);
        List<EvaluationSheetRes> evaluationSheetResList = new ArrayList<>();
        for(int i=0; i<evaluationSheetList.size(); ++i){
            EvaluationSheetRes evaluationSheetRes = new EvaluationSheetRes(evaluationSheetList.get(i));
            evaluationSheetResList.add(evaluationSheetRes);
        }
        return evaluationSheetResList;
    }

}
