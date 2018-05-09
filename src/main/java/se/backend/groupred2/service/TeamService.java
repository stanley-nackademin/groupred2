package se.backend.groupred2.service;

import org.springframework.stereotype.Service;
import se.backend.groupred2.model.Team;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TeamRepository;
import se.backend.groupred2.repository.UserRepository;

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
        return teamRepository.save(new Team(team.getName(), team.isActive()));
    }

    public Team setStatus(Team team, boolean active) {

        team.setActive(active);

        return teamRepository.save(team);
    }

    public Iterable<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public User addUserToTeam(User user, Team team) {
        validate(team);
        user.setTeam(team);
        return userRepository.save(user);
    }

    private void validate(Team team) {
        if (userRepository.countAllByTeam(team) >= 10) {
            //can't add to team
        }
    }
}

