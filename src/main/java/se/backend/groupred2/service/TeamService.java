package se.backend.groupred2.service;

import org.springframework.stereotype.Service;
import se.backend.groupred2.model.Team;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TeamRepository;
import se.backend.groupred2.repository.UserRepository;
import se.backend.groupred2.service.mapper.FullTeamExcepetion;

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
        //validate(user);
        return teamRepository.save(new Team(team.getName(), team.isActive(), team.getMaxUsers()));
    }

    public Team setStatus(Team team, boolean active) {

        team.setActive(active);

        return teamRepository.save(team);
    }

    private Optional<Team> getTeam(Long id) {
        return teamRepository.findById(id);
    }

    private Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Iterable<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<User> addUserToTeam(Long userId, Long teamId) {
        Optional<Team> teamResult = getTeam(teamId);
        Optional<User> userResult = getUser(userId);

        if (teamResult.isPresent() && userResult.isPresent()) {
            validate(teamResult.get());

            userResult.get().setTeam(teamResult.get());

            userRepository.save(userResult.get());
        }

        return userResult;
    }

    private void validate(Team team) {
        if (userRepository.countAllByTeam(team) >= team.getMaxUsers()) {
            throw new FullTeamExcepetion("Can't add user. Team is full");
        }
    }
}

