package se.backend.groupred2.resource;

import org.springframework.stereotype.Component;
import se.backend.groupred2.model.Team;
import se.backend.groupred2.service.TeamService;

import javax.ws.rs.*;

import javax.ws.rs.core.Response;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("team")
public final class TeamResource {

    private final TeamService service;

    public TeamResource(TeamService service) {
        this.service = service;
    }

    @GET
    public Response getAllTeams() {
        return  Response.ok(service.getAllTeams()).build();
    }
    @POST
    public Response createTeam(Team team) {

        Team result = service.createTeam(team);
        return Response.status(CREATED).header("Location", "Teams/" + result.getId()).build();
    }

    @PUT
    @Path("{id}")
    public Response addUserToTeam(@QueryParam("teamId")) {
        return null;
    }


}

