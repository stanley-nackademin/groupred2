package se.backend.groupred2.resource;

import org.springframework.stereotype.Component;
import se.backend.groupred2.model.Issue;
import se.backend.groupred2.service.IssueService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("issues")
public final class IssueResource {
    private final IssueService service;

    public IssueResource(IssueService service) {
        this.service = service;
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, Issue issue) {
        return service.update(id, issue)
                .map(t -> Response.status(OK))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }
}
