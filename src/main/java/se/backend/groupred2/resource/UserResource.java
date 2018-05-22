package se.backend.groupred2.resource;

import org.springframework.stereotype.Component;
import se.backend.groupred2.model.User;
import se.backend.groupred2.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("users")
public final class UserResource {


    private final UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @POST
    public Response createUser(User user) {
        User result = service.createUser(user);
        return Response.status(CREATED).header("Location", "Users/" + result.getId()).build();
    }


    // users/update     vi mÃ¥ste skicka id och user
    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, User user) {

        return service.update(id, user)
                .map(t -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }


    // users/deactivate         vi mÃ¥ste skicka id bara
    @PUT
    @Path("deactivate")
    public Response deActivate(User user) {

        return service.deActivate(user)
                .map(t -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }


    @GET
    public Response getUserByUserNamefirstNameLastName(
            @QueryParam("usernumber") @DefaultValue("0") long usernumber,
            @QueryParam("userName") @DefaultValue("0") String userName,
            @QueryParam("firstName") @DefaultValue("0") String firstName,
            @QueryParam("lastName") @DefaultValue("0") String lastName) {
        return Response.ok(service.getUserByUserNamefirstNameLastName(usernumber, userName, firstName, lastName)).build();
    }


    @GET
    @Path("getByTeamId/{id}")
    public List<User> getAllUserByTeamId(@PathParam("id") Long teamId) {
        return service.getAllUserByteamId(teamId);
    }


}