package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loyaltypartner.elok.quiz.model.User;
import com.loyaltypartner.elok.quiz.model.dto.UserLoginDTO;
import com.loyaltypartner.elok.quiz.model.dto.UserLoginResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api
public interface IUserController {

    @ApiOperation(value = "Finds all users.", nickname = "findAllUsers", authorizations = { @Authorization(value = "bearer") }, tags = { "Users" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
    ResponseEntity<List<User>> findAll();

    @ApiOperation(value = "Finds a specific user by the primary id.", nickname = "findUserById", authorizations = { @Authorization(value = "bearer") }, tags = {
            "Users" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/users/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<User> findById(@ApiParam(value = "userId", required = true) @PathVariable("userId") Long userId);

    @ApiOperation(value = "Creates a new user with the given data.", nickname = "createUser", authorizations = { @Authorization(value = "bearer") }, tags = {
            "Users" })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<User> createUser(@ApiParam(value = "user", required = true) @RequestBody(required = true) User user);

    @ApiOperation(value = "Updates an existing user with the given data.", nickname = "updateUser", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Users" })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/users/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<User> updateUser(@ApiParam(value = "userId", required = true) @PathVariable("userId") Long userId,
            @ApiParam(value = "user", required = true) @RequestBody(required = true) User user);

    @ApiOperation(value = "Logs in a user with the given name and password.", nickname = "loginUser", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Users" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/users/login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<UserLoginResponseDTO> loginUser(@ApiParam(value = "loginData", required = true) @RequestBody(required = true) UserLoginDTO loginData);

}
