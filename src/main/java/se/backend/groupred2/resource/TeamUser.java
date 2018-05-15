package se.backend.groupred2.resource;

public final class TeamUser {
    private long teamId;
    private long userId;

    protected TeamUser() {}

    public long getTeamId() {
        return teamId;
    }

    public long getUserId() {
        return userId;
    }
}
