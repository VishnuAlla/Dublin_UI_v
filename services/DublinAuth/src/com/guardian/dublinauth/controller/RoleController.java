/*Copyright (c) 2016-2017 imaginea.com All Rights Reserved.
 This software is the confidential and proprietary information of imaginea.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with imaginea.com*/
package com.guardian.dublinauth.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.guardian.dublinauth.Role;
import com.guardian.dublinauth.UserRoleMapping;
import com.guardian.dublinauth.service.RoleService;


/**
 * Controller object for domain model class Role.
 * @see Role
 */
@RestController("DublinAuth.RoleController")
@Api(value = "RoleController", description = "Exposes APIs to work with Role resource.")
@RequestMapping("/DublinAuth/Role")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
	@Qualifier("DublinAuth.RoleService")
	private RoleService roleService;

	@ApiOperation(value = "Creates a new Role instance.")
	@RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Role createRole(@RequestBody Role role) {
		LOGGER.debug("Create Role with information: {}" , role);

		role = roleService.create(role);
		LOGGER.debug("Created Role with information: {}" , role);

	    return role;
	}


    @ApiOperation(value = "Returns the Role instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Role getRole(@PathVariable("id") String id) throws EntityNotFoundException {
        LOGGER.debug("Getting Role with id: {}" , id);

        Role foundRole = roleService.getById(id);
        LOGGER.debug("Role details with id: {}" , foundRole);

        return foundRole;
    }

    @ApiOperation(value = "Updates the Role instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Role editRole(@PathVariable("id") String id, @RequestBody Role role) throws EntityNotFoundException {
        LOGGER.debug("Editing Role with id: {}" , role.getRoleId());

        role.setRoleId(id);
        role = roleService.update(role);
        LOGGER.debug("Role details with id: {}" , role);

        return role;
    }

    @ApiOperation(value = "Deletes the Role instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteRole(@PathVariable("id") String id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Role with id: {}" , id);

        Role deletedRole = roleService.delete(id);

        return deletedRole != null;
    }

    /**
     * @deprecated Use {@link #findRoles(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Role instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Role> searchRolesByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Roles list");
        return roleService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Role instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Role> findRoles(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Roles list");
        return roleService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Role instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Role> filterRoles(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Roles list");
        return roleService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportRoles(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return roleService.export(exportType, query, pageable);
    }

	@ApiOperation(value = "Returns the total count of Role instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countRoles( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Roles");
		return roleService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getRoleAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return roleService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{id:.+}/userRoleMappings", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the userRoleMappings instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<UserRoleMapping> findAssociatedUserRoleMappings(@PathVariable("id") String id, Pageable pageable) {

        LOGGER.debug("Fetching all associated userRoleMappings");
        return roleService.findAssociatedUserRoleMappings(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service RoleService instance
	 */
	protected void setRoleService(RoleService service) {
		this.roleService = service;
	}

}

