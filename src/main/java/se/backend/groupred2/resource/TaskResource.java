package se.backend.groupred2.resource;

import org.springframework.stereotype.Component;
import se.backend.groupred2.model.Task;
import se.backend.groupred2.model.TaskStatus;
import se.backend.groupred2.service.TaskService;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("tasks")
public final class TaskResource {

    private final TaskService service;

    public TaskResource(TaskService service) {
        this.service = service;
    }

    @POST
    public Response createTask(Task task) {

        Task result = service.createTask(task);
        return Response.status(CREATED).header("Location", "Tasks/" + result.getId()).build();
    }

//    @GET
//    @Path("{id}")
//    public Response getTask(@PathParam("id") Long id) {
//        return service.getTask(id)
//                .map(Response::ok)
//                .orElse(Response.status(NOT_FOUND))
//                .build();
//    }

//    @GET
//    @Path("{status}")
//    public List<Task> getAllTasksByStatus(@PathParam("status") String status) {
//        return service.getAllTasksByStatus(status);
//    }

    @GET
    @Path("team/{id}")
    public List<Task> getAllTasksByTeam(@PathParam("id") Long teamId){
        return service.getAllTasksByTeamId(teamId);
    }

    @GET
    @Path("user/{id}")
    public List<Task> getAllTasksByUser(@PathParam("id") Long userId){
        return service.getAllTasksByUserId(userId);
    }

    @GET
    @Path("{description}")
    public List<Task> getAllTasksByDescription(@PathParam("description") String description) {
        return service.getAllTasksByDescription(description);
    }

    @DELETE
    @Path("{id}")
    public Response deleteTask(@PathParam("id") Long id) {
        return service.deleteTask(id)
                .map(task -> Response.status(NO_CONTENT))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    @PUT
    @Path("{id}/users/{userId}")
    public Response assignTaskToUser(@PathParam("id") Long id, @PathParam("userId") Long userId) {
        return service.assignTaskToUser(id, userId)
                .map(t -> Response.status(NO_CONTENT))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    /*@PUT
    @Path("{id}")
    public Response updateTask(@PathParam("id") Long id, Task task) {
        return service.updateTask(id, task)
                .map(Response::ok)
                .orElse(Response.status(NOT_FOUND))
                .build();
    }*/

}






