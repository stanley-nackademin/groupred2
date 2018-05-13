package se.backend.groupred2.service;

import org.springframework.stereotype.Service;

import se.backend.groupred2.model.Team;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TeamRepository;
import se.backend.groupred2.repository.UserRepository;
import se.backend.groupred2.resource.mapper.FullTeamExcepetion;

import java.util.Optional;

@Service
public final class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Optional<Team> deActivate(Team team) {
        Optional<Team> result = teamRepository.findById(team.getId());

        result.ifPresent(t -> {
            t.deActivate();
            teamRepository.save(result.get());
        });

        return result;
    }

    public Optional<Team> update(Team team) {
        Optional<Team> result = teamRepository.findById(team.getId());

        result.ifPresent(t -> {
            t.setName(team.getName());
            teamRepository.save(result.get());
        });

        return result;
    }

    public Iterable<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<User> addUser(Long teamId, User user) {
        Optional<Team> teamResult = teamRepository.findById(teamId);
        Optional<User> userResult = userRepository.findById(user.getId());

        if (teamResult.isPresent() && userResult.isPresent()) {
            user = userResult.get();
            Team team = teamResult.get();

            validate(team);
            user.setTeam(team);

            userRepository.save(user);
        }

        return userResult;
    }

    private void validate(Team team) {
        if (userRepository.countAllByTeam(team) >= team.getMaxUsers()) {
            throw new FullTeamExcepetion("Can't add user. Team is full");
        }
    }
}

