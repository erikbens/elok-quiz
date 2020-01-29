package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loyaltypartner.elok.quiz.model.QuestionFilter;
import com.loyaltypartner.elok.quiz.model.QuizQuestionDTO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

public interface IQuizController {

    @ApiOperation(value = "Gets a quiz for the given question filter.", nickname = "createQuizByQuestionFilter", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Quiz" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/quiz", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    public ResponseEntity<List<QuizQuestionDTO>> getQuizByQuestionFilter(
            @ApiParam(value = "questionFilter", required = false) @RequestBody(required = false) QuestionFilter questionFilter);

}
