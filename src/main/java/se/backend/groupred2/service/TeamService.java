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
        return teamRepository.save(new Team(team.getName(), team.isActive(), team.getMaxUsers()));
    }

    public Optional<Team> changeName(Long teamId, String name) {
        Optional<Team> result = teamRepository.findById(teamId);

        result.ifPresent(team -> {
            team.setName(name);
            teamRepository.save(team);
        });

        return result;
    }

    public Optional<Team> updateStatus(Long teamId, boolean active) {
        Optional<Team> result = teamRepository.findById(teamId);

        result.ifPresent(team -> team.setActive(active));

        return result;
    }

    public Iterable<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<User> addUserToTeam(Long userId, Long teamId) {
        Optional<Team> teamResult = teamRepository.findById(teamId);
        Optional<User> userResult = userRepository.findById(userId);

        if (teamResult.isPresent() && userResult.isPresent()) {
            User user = userResult.get();
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

