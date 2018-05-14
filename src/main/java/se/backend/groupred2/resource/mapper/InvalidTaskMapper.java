package se.backend.groupred2.resource.mapper;

import se.backend.groupred2.service.exceptions.InvalidTaskException;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public final class InvalidTaskMapper implements ExceptionMapper<InvalidTaskException>{

    @Override
    public Response toResponse(InvalidTaskException exception) {
        return Response.status(BAD_REQUEST).entity(singletonMap("Error!", exception.getMessage())).build();
    }
}
