package com.loyaltypartner.elok.quiz.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loyaltypartner.elok.quiz.model.Domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api
public interface IDomainController {

    @ApiOperation(value = "Finds all domains.", nickname = "findAllDomains", authorizations = { @Authorization(value = "bearer") }, tags = { "Domains" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/domains", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
    ResponseEntity<List<Domain>> findAll();

    @ApiOperation(value = "Finds a specific domain by the primary id.", nickname = "findDomainById", authorizations = {
            @Authorization(value = "bearer") }, tags = { "Domains" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/domains/{domainId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<Domain> findById(@ApiParam(value = "domainId", required = true) @PathVariable("domainId") Long domainId);

    @ApiOperation(value = "Creates a new domain with the given data.", nickname = "createDomain", authorizations = { @Authorization(value = "bearer") }, tags = {
            "Domains" })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/domains", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<Domain> createDomain(@ApiParam(value = "user", required = true) @RequestBody(required = true) Domain domain);

    @ApiOperation(value = "Updates an existing domain with the given data.", nickname = "updateDomain", authorizations = { @Authorization(value = "bearer") }, tags = {
            "Domains" })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/domains/{domainId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT, headers = {})
    @ApiImplicitParams({ @ApiImplicitParam(name = "Content-Type", value = MediaType.APPLICATION_JSON_VALUE, paramType = "header") })
    ResponseEntity<Domain> updateDomain(@ApiParam(value = "domainId", required = true) @PathVariable("domainId") Long domainId,
            @ApiParam(value = "domain", required = true) @RequestBody(required = true) Domain domain);

}
