package se.backend.groupred2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.backend.groupred2.resource.IssueResource;
import se.backend.groupred2.resource.TaskResource;
import se.backend.groupred2.resource.TeamResource;
import se.backend.groupred2.resource.UserResource;
import se.backend.groupred2.resource.mapper.InvalidInputMapper;
import se.backend.groupred2.resource.mapper.InvalidTaskMapper;
import se.backend.groupred2.resource.mapper.InvalidTeamMapper;
import se.backend.groupred2.resource.mapper.InvalidUserMapper;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(IssueResource.class);
        register(TaskResource.class);
        register(TeamResource.class);
        register(UserResource.class);
        register(InvalidInputMapper.class);
        register(InvalidTaskMapper.class);
        register(InvalidTeamMapper.class);
        register(InvalidUserMapper.class);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new ParameterNamesModule());
    }
}
