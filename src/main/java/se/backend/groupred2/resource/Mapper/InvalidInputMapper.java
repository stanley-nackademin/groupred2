package se.backend.groupred2.resource.Mapper;

import se.backend.groupred2.service.exceptions.InvalidInputException;
import se.backend.groupred2.service.exceptions.InvalidTaskException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public final class InvalidInputMapper implements ExceptionMapper<InvalidInputException> {

    @Override
    public Response toResponse(InvalidInputException exception) {
        return Response.status(BAD_REQUEST).entity(singletonMap("Error!", exception.getMessage())).build();
    }
}
