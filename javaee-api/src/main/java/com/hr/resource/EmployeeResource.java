package com.hr.resource;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.hr.entity.Employee;
import com.hr.entity.Team;
import com.hr.service.PersistenceService;

@Path("employee")
@Produces("application/json")
@Consumes("application/json")
public class EmployeeResource {
	
	@Inject
	PersistenceService ps;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Path("id")
	public Employee returnHelloWorld(@QueryParam("id") @NotNull Long id) {
		return ps.getEmployeebyId(id);
	}
	
	@GET
	@Path("all")
	public Response getAllEmployees() {
		return Response.ok(ps.getAllEmployees()).status(Response.Status.OK).build();
	}
	
	@POST
	@Path("create")
	public Response createEmployee(@Valid Employee employee) {
		URI uri;
		if(employee.getTeam() != null) {
			if(!ps.isTeamExists(employee.getTeam())) {
				employee.setTeam(null);
				ps.saveEmployee(employee);
				uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(employee.getEmployeeId())).build();
				String res = "Employee Created but team doesn't exist, Kindly assign the correct team name";
				return Response.created(uri).status(Response.Status.CREATED).entity(res).build();
			}
			else {
				Team teamd = ps.getTeambyName(employee.getTeam().getName());
				employee.setTeam(teamd);
			}
		}
		ps.saveEmployee(employee);
		uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(employee.getEmployeeId())).build();
		return Response.created(uri).status(Response.Status.CREATED).build();
	}
	
	@POST
	@Path("assign")
	public Response assignTeamtoEmployee(@QueryParam("id") Long id, Team team) {
		Employee emp = ps.getEmployeebyId(id);
		if(emp != null && ps.isTeamExists(team)) {
			Team tf = ps.getTeambyName(team.getName());
			emp.setTeam(tf);
			ps.assignTeam(emp);
			URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(emp.getEmployeeId())).build();
			return Response.created(uri).status(Response.Status.CREATED).build();
		}
		else
			return Response.status(Response.Status.BAD_REQUEST).entity("Team or Employee not Found").build();
	}
	
	@DELETE
	@Path("{id: \\d+}")
	public Response removeEmployee(@QueryParam("id") @NotNull Long id) {		
		ps.removeEmployee(id);
		return Response.ok().build();
		
	}

}
