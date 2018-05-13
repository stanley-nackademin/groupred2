package se.backend.groupred2.resource.Mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;


@Provider
public class FullTeamMapper implements ExceptionMapper<FullTeamExcepetion> {
    @Override
    public Response toResponse(FullTeamExcepetion fullTeamExcepetion) {
        return Response.status(BAD_REQUEST).entity(singletonMap("error", fullTeamExcepetion.getMessage())).build();
    }
}
