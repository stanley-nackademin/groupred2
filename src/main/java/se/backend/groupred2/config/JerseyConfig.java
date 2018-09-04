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

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(IssueResource.class);
        register(TaskResource.class);
        register(TeamResource.class);
        register(UserResource.class);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new ParameterNamesModule());
    }
}
