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

    // teams/1/adduser - body { "id": 1 }

//    @PUT
//    @Path("{id}/adduser")
//    public Response addUser(@PathParam("id") Long teamId, User user) {
//
//        return service.addUser(teamId, user)
//                .map(u -> Response.status(OK))
//                .orElse(Response.status((NOT_FOUND)))
//                .build();
//    }

    /*

    teams/adduser

    body { "teamId": 1, "userId":1 }

     */
    @PUT
    @Path("adduser")
    public Response addUser(TeamUser teamUser) {

        return service.addUser(teamUser.getTeamId(), teamUser.getUserId())
                .map(u -> Response.status(OK))
                .orElse(Response.status((NOT_FOUND)))
                .build();
    }

    // teams/update { "id":1, "name":"new name" }
    @PUT
    @Path("update")
    public Response update(Team team) {

        return service.update(team)
                .map(t -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    // teams/deActivate
    // body { "id":1 }
    @PUT
    @Path("deactivate")
    public Response deActivate(Team team) {

        return service.deActivate(team)
                .map(t -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }
}

