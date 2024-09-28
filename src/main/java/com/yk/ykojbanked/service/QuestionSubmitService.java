package com.yk.ykojbanked.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yk.ykojbanked.model.dto.question.QuestionQueryRequest;
import com.yk.ykojbanked.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yk.ykojbanked.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yk.ykojbanked.model.entity.Question;
import com.yk.ykojbanked.model.entity.QuestionSubmit;
import com.yk.ykojbanked.model.entity.User;
import com.yk.ykojbanked.model.vo.QuestionSubmitVO;
import com.yk.ykojbanked.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;


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
    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
