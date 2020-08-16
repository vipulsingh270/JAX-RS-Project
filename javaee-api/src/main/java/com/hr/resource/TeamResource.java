package com.hr.resource;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.hr.entity.Team;
import com.hr.service.PersistenceService;

@Path("team")
@Produces("application/json")
@Consumes("application/json")

public class TeamResource {
	@Inject
	PersistenceService ps;
	
	@Context
	UriInfo uriInfo;
	
	@POST
	@Path("create")
	public Response createTeam(Team team) {
		if(ps.isTeamExists(team))
			return Response.status(Response.Status.BAD_REQUEST).entity("Team already Exists").build();
		ps.saveTeam(team);
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(team.getId())).build();
		return Response.created(uri).status(Response.Status.CREATED).build();
	}
	
	@GET
	@Path("all")
	public Response getAllTeams() {
		return Response.ok(ps.getAllTeams()).status(Response.Status.OK).build();
	}
	
	@GET
	@Path("id")
	public Response getTeambyId(@QueryParam("id") @NotNull Long id) {
		if(ps.getTeambyId(id) != null)
			return Response.ok(ps.getTeambyId(id)).status(Response.Status.OK).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity("Team not Found").build();
	} 

}
