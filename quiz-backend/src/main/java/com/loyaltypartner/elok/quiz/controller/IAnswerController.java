package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loyaltypartner.elok.quiz.model.Answer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api
public interface IAnswerController {

    @ApiOperation(value = "Finds a specific answer by the primary id.", nickname = "findAnswerById", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Answers" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/answers/{answerId}", consumes = { }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<Answer> findById(@ApiParam(value = "answerId", required = true) @PathVariable("answerId") Long answerId);
    
    @ApiOperation(value = "Finds all answers for the given question id.", nickname = "findAnswersByQuestionId", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/questions/{questionId}/answers", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<List<Answer>> findAllByQuestionId(@ApiParam(value = "questionId", required = true) @PathVariable("questionId") Long questionId);
    
    @ApiOperation(value = "Creates a new answer with the given data for the questionId.", nickname = "createAnswerForQuestionId", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/questions/{questionId}/answers", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<Answer> createAnswerForQuestionId(@ApiParam(value = "questionId", required = true) @PathVariable Long questionId,
            @ApiParam(value = "answer", required = true) @RequestBody Answer answer);
    
    @ApiOperation(value = "Updates the answer identified by answerId with the given data.", nickname = "updateAnswer", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")})
    @RequestMapping(value = "/answers/{answerId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<Answer> updateAnswer(@ApiParam(value = "answerId", required = true) @PathVariable Long answerId,
            @ApiParam(value = "answer", required = true) @RequestBody Answer answer);
    
    @ApiOperation(value = "Deletes an answer identified by answerId.", nickname = "deleteAnswer", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")})
    @RequestMapping(value = "/answers/{answerId}", consumes = { }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<Void> deleteAnswer(@ApiParam(value = "answerId", required = true) @PathVariable Long answerId);
}
