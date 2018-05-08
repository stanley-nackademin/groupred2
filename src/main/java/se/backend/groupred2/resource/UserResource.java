package se.backend.groupred2.resource;

import org.springframework.stereotype.Component;
import se.backend.groupred2.model.User;
import se.backend.groupred2.service.UserService;

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
@Path("users")
public final class UserResource {

    @Context
    private UriInfo uriInfo;

    private final UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @POST
    public Response createUser(User user) {
        User temp = service.createUser(user);

        return Response.created(URI.create(uriInfo
                .getAbsolutePathBuilder()
                .path(temp.getId().toString())
                .toString()))
                .build();
    }
}
