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
    @Path("addUser")
    public Response addUser(Team team, User user) {

        return service.addUser(user, team)
                .map(u -> Response.status(OK))
                .orElse(Response.status((NOT_FOUND)))
                .build();
    }

    // team/update
    @PUT
    @Path("update")
    public Response update(Team team) {

        return service.update(team)
                .map(t -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    // team/deActivate?id=1
    @PUT
    @Path("deActivate")
    public Response updateStatus(@QueryParam("id") Long teamId) {

        return service.deActivate(teamId)
                .map(team -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }
}

