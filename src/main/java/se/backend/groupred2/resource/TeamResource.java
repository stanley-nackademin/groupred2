package se.backend.groupred2.resource;

import org.springframework.stereotype.Component;
import se.backend.groupred2.model.Team;
import se.backend.groupred2.model.User;
import se.backend.groupred2.service.TeamService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("teams")
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

    // post teams/{id}/user { "id":1 }

    @PUT
    @Path("{id}/users")
    public Response addUser(@PathParam("id") Long teamId, User user) {

        return service.addUser(teamId, user)
                .map(u -> Response.status(OK))
                .orElse(Response.status((NOT_FOUND)))
                .build();
    }

    // teams/1/update { "name":"new name" }
    @PUT
    @Path("{id}/update")
    public Response update(@PathParam("id") Long teamId, Team team) {

        return service.update(teamId, team)
                .map(t -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    // teams/{id}/deactivate
    @PUT
    @Path("{id}/deactivate")
    public Response deActivate(@PathParam("id") Long teamId, Team team) {

        return service.deActivate(teamId)
                .map(t -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }
}

