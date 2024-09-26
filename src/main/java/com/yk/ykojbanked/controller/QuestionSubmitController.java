package com.yk.ykojbanked.controller;

import com.yk.ykojbanked.common.BaseResponse;
import com.yk.ykojbanked.common.ErrorCode;
import com.yk.ykojbanked.common.ResultUtils;
import com.yk.ykojbanked.exception.BusinessException;
import com.yk.ykojbanked.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yk.ykojbanked.model.entity.User;
import com.yk.ykojbanked.service.QuestionSubmitService;
import com.yk.ykojbanked.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
* 
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次点提交题目的id
     */
    @PostMapping("/")
    public BaseResponse<Long> doThumb(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交题目
        final User loginUser = userService.getLoginUser(request);
        long questionId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest,loginUser);
        return ResultUtils.success(questionId);
    }

}
