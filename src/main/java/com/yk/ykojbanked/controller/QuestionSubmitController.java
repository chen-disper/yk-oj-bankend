package com.yk.ykojbanked.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yk.ykojbanked.annotation.AuthCheck;
import com.yk.ykojbanked.common.BaseResponse;
import com.yk.ykojbanked.common.ErrorCode;
import com.yk.ykojbanked.common.ResultUtils;
import com.yk.ykojbanked.constant.UserConstant;
import com.yk.ykojbanked.exception.BusinessException;
import com.yk.ykojbanked.model.dto.question.QuestionQueryRequest;
import com.yk.ykojbanked.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yk.ykojbanked.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yk.ykojbanked.model.entity.Question;
import com.yk.ykojbanked.model.entity.QuestionSubmit;
import com.yk.ykojbanked.model.entity.User;
import com.yk.ykojbanked.model.vo.QuestionSubmitVO;
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


    /**
     * 分页获取题目提交列表（除了管理员外，普通用户只能看到非答案、提交代码等公开信息）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 从数据库中查询原始的题目提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userService.getLoginUser(request);
        // 返回脱敏信息
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }
}
