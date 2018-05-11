package se.backend.groupred2.resource;

import org.springframework.stereotype.Component;
import se.backend.groupred2.model.Team;
import se.backend.groupred2.service.TeamService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;

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
        return Response.ok(service.getAllTeams()).build();
    }

    @POST
    public Response createTeam(Team team) {
        Team result = service.createTeam(team);

        return Response.status(CREATED).header("Location", "Team/" + result.getId()).build();
    }

    @PUT
    public Response addUser(@QueryParam("id") Long teamId,
                            @QueryParam("userId") Long userId) {
        return service.addUserToTeam(userId, teamId)
                .map(u -> Response.status(OK))
                .orElse(Response.status((NOT_FOUND)))
                .build();
    }

    @PUT
    public Response updateName(@QueryParam("id") Long teamId,
                               @QueryParam("updateName") String name) {
        return service.changeName(teamId, name)
                .map(team -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    @PUT
    public Response updateStatus(@QueryParam("id") Long teamId,
                                 @QueryParam("isActive") boolean isActive) {
        return service.updateStatus(teamId, isActive)
                .map(team -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }
}

