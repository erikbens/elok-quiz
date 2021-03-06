package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.loyaltypartner.elok.quiz.model.Question;
import com.loyaltypartner.elok.quiz.model.dto.QuestionDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api
public interface IQuestionController {

    @ApiOperation(value = "Finds all questions.", nickname = "findAllQuestions", authorizations = { @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/questions", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
    ResponseEntity<List<Question>> findAll();

    @ApiOperation(value = "Finds a specific question by the primary id.", nickname = "findQuestionById", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/questions/{questionId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<Question> findById(@ApiParam(value = "questionId", required = true) @PathVariable("questionId") Long questionId,
            @ApiParam(value = "withDomain", required = false) @RequestParam(name = "withDomain", required = false) Boolean withDomain);

    @ApiOperation(value = "Finds all questions for the given domain id.", nickname = "findQuestionsByDomainId", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/domains/{domainId}/questions", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<List<Question>> findByDomainId(@ApiParam(value = "domainId", required = true) @PathVariable("domainId") Long domainId);

    @ApiOperation(value = "Finds all questions by title or text for the given query.", nickname = "findQuestionsByTitleTextQuery", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/questions/search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<List<Question>> findByTitleOrText(@ApiParam(value = "query", required = true) @RequestParam String query);

    @ApiOperation(value = "Creates a new question with the given data.", nickname = "createQuestion", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/questions", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.MULTIPART_FORM_DATA_VALUE, paramType = "header") })
    ResponseEntity<Question> createQuestion(@ApiParam(value = "question", required = true) @RequestPart("question") QuestionDTO question,
            @ApiParam(value = "image", required = false) @RequestPart(name = "image", required = false) MultipartFile image);

    @ApiOperation(value = "Updates the question identified by questionId with the given data.", nickname = "updateQuestion", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/questions/{questionId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.MULTIPART_FORM_DATA_VALUE, paramType = "header") })
    ResponseEntity<Question> updateQuestion(@ApiParam(value = "questionId", required = true) @PathVariable Long questionId,
            @ApiParam(value = "question", required = true) @RequestPart("question") QuestionDTO question,
            @ApiParam(value = "image", required = false) @RequestPart(name = "image", required = false) MultipartFile image);

    @ApiOperation(value = "Deletes a question identified by questionId.", nickname = "deleteQuestion", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/questions/{questionId}", consumes = {}, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<Void> deleteQuestion(@ApiParam(value = "questionId", required = true) @PathVariable Long questionId);

    @ApiOperation(value = "Removes the link between questionId and domainId.", nickname = "removeQuestionFromDomain", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/domains/{domainId}/questions/{questionId}", consumes = {}, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<Void> removeQuestionFromDomain(@ApiParam(value = "questionId", required = true) @PathVariable Long questionId,
            @ApiParam(value = "domainId", required = true) @PathVariable Long domainId);

    @ApiOperation(value = "Checks the answers for the given questionId.", nickname = "checkAnswersForQuestionId", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/questions/{questionId}/check", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    public ResponseEntity<Boolean> checkAnswersForQuestionId(@ApiParam(value = "questionId", required = true) @PathVariable Long questionId,
            @ApiParam(value = "answerIds", required = true) @RequestBody List<Long> answerIds);

    @ApiOperation(value = "Finds the image for the given questionId.", nickname = "findImageByQuestionId", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Questions" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/questions/{questionId}/image", consumes = {}, produces = { MediaType.IMAGE_JPEG_VALUE }, method = RequestMethod.GET, headers = {})
    ResponseEntity<Resource> findImageByQuestionId(@ApiParam(value = "questionId", required = true) @PathVariable("questionId") Long questionId);
}
