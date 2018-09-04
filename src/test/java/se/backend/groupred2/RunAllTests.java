package se.backend.groupred2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import se.backend.groupred2.service.TaskServiceTest;
import se.backend.groupred2.service.TeamServiceTest;
import se.backend.groupred2.service.UserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({TaskServiceTest.class, TeamServiceTest.class, UserServiceTest.class})
public class RunAllTests {
}
