package com.yk.ykojbanked.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yk.ykojbanked.common.ErrorCode;
import com.yk.ykojbanked.exception.BusinessException;
import com.yk.ykojbanked.mapper.QuestionSubmitMapper;
import com.yk.ykojbanked.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yk.ykojbanked.model.entity.Question;
import com.yk.ykojbanked.model.entity.QuestionSubmit;
import com.yk.ykojbanked.model.entity.User;
import com.yk.ykojbanked.model.enums.QuestionSubmitLanguageEnum;
import com.yk.ykojbanked.model.enums.QuestionSubmitStatusEnum;
import com.yk.ykojbanked.service.QuestionService;
import com.yk.ykojbanked.service.QuestionSubmitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 29136
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-09-19 23:41:46
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;
    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest 题目创建信息
     * @param loginUser                登录的用户
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //判断是否为设定语言
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum == null){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"编程语言错误");
        }
        long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setLanguage(language);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setUserId(userId);
        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");  //传入空的判题信息 json字符串
        boolean save = this.save(questionSubmit);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目提交失败");
        }
        return questionSubmit.getId();

    }
}




