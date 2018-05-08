package se.backend.groupred2.resource;

import org.springframework.stereotype.Component;
import se.backend.groupred2.model.Team;
import se.backend.groupred2.service.TeamService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("teams")
public final class TeamResource {

    @Context
    private UriInfo uriInfo;

    private final TeamService service;

    public TeamResource(TeamService service) {
        this.service = service;
    }

    @POST
    public Response createTeam(Team team) {
        Team temp = service.createTeam(team);

        return Response.created(URI.create(uriInfo
                .getAbsolutePathBuilder()
                .path(temp.getId().toString())
                .toString()))
                .build();
    }
}
