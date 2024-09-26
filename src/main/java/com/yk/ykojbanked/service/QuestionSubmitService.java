package com.yk.ykojbanked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yk.ykojbanked.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yk.ykojbanked.model.entity.QuestionSubmit;
import com.yk.ykojbanked.model.entity.User;


/**
 * @author 29136
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2024-09-19 23:41:46
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目创建信息
     * @param loginUser                登录的用户
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

}
