package se.backend.groupred2.resource;

import org.springframework.stereotype.Component;
import se.backend.groupred2.model.User;
import se.backend.groupred2.service.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URI;


import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;

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
//    public Response getAll() {
//        return Response.ok( service.getAllUsers()).build();
//    }
//
//    @GET
//    @Path("{id}")
//    public Response getUser(@PathParam("id") Long id) {
//        return service.getUser(id)
//                .map(Response::ok)
//                .orElse(Response.status(NOT_FOUND))
//                .build();
//    }
//
//    @DELETE
//    @Path("{id}")
//    public Response deleteUser(@PathParam("id") Long id) {
//        return service.deleteUser(id)
//                .map(c -> Response.status(NO_CONTENT))
//                .orElse(Response.status(NOT_FOUND))
//                .build();
//    }


  
}
