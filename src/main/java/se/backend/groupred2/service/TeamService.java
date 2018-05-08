package se.backend.groupred2.service;

import org.springframework.stereotype.Service;
import se.backend.groupred2.model.Team;
import se.backend.groupred2.repository.TeamRepository;

@Service
public final class TeamService {

    private final TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public Team createTeam(Team team){
        return repository.save(new Team(team.getName(), team.isActive()));
    }
}
