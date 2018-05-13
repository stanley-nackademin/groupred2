package se.backend.groupred2.resource;

import org.springframework.stereotype.Component;
import se.backend.groupred2.model.User;
import se.backend.groupred2.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

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

//
//    @GET
//    public Response getUserByUserNumber(@QueryParam("usernumber") @DefaultValue("0") long usernumber) {
//        return Response.ok(service.getUser(usernumber)).build();
//    }


    @GET
    public Response getUserByUserNamefirstNameLastName(
            @QueryParam("usernumber") @DefaultValue("0") long usernumber,
            @QueryParam("userName") @DefaultValue("0") String userName,
            @QueryParam("firstName") @DefaultValue("0") String firstName,
            @QueryParam("lastName") @DefaultValue("0") String lastName) {
        return Response.ok(service.getUserByUserNamefirstNameLastName(usernumber, userName, firstName, lastName)).build();
    }


    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") long id, User user) {
        return service.updatUser(id, user)
                .map(Response::ok).orElse(Response.status(NOT_FOUND)).build();
    }


    @GET
    @Path("{id}")
    public Response getAllUserByteamId(@PathParam("id") Long teamId) {
        return service.getALLUserByteamId(teamId)
                .map(Response::ok)
                .orElse(Response.status(NOT_FOUND))
                .build();


    }
}