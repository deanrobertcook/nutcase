package org.theronin.nutcase.domain.user.service;

import java.time.ZonedDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Optional;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theronin.nutcase.Application;
import org.theronin.nutcase.domain.user.entity.User;
import org.theronin.nutcase.domain.user.repository.UserRepository;
import org.theronin.nutcase.domain.user.util.RandomUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Rule
    public TestName name = new TestName();

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Before
    public void initTest() {
        userRepository.deleteAllInBatch();
    }

    @Test
    public void assertThatUserMustExistToResetPassword() {
        log.info(name.getMethodName());
        Optional<User> maybeUser = userService.requestPasswordReset("john.doe@localhost");
        assertFalse("User should not be present", maybeUser.isPresent());

        User admin = userService.createUserInformation("admin", "admin", "admin", "admin", "admin@localhost", "de");
        userService.activateRegistration(admin.getActivationKey());

        maybeUser = userService.requestPasswordReset("admin@localhost");
        assertTrue("User should be present", maybeUser.isPresent());

        assertEquals("admin@localhost", maybeUser.get().getEmail());
        assertNotNull("Reset date should not be null", maybeUser.get().getResetDate());
        assertNotNull("Reset key should not be null", maybeUser.get().getResetKey());
    }

    @Test
    public void assertThatOnlyActivatedUserCanRequestPasswordReset() {
        log.info(name.getMethodName());
        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");
        Optional<User> maybeUser = userService.requestPasswordReset("john.doe@localhost");
        assertFalse("User should not be present", maybeUser.isPresent());
    }

    @Test
    public void assertThatResetKeyMustNotBeOlderThan24Hours() {
        log.info(name.getMethodName());
        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");

        ZonedDateTime daysAgo = ZonedDateTime.now().minusHours(25);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey(resetKey);

        userRepository.save(user);
        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());
        assertFalse("User should not be present", maybeUser.isPresent());
    }

    @Test
    public void assertThatResetKeyMustBeValid() {
        log.info(name.getMethodName());
        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");

        ZonedDateTime daysAgo = ZonedDateTime.now().minusHours(25);
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey("1234");
        userRepository.save(user);

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());
        assertFalse("User should not be present", maybeUser.isPresent());
    }

    @Test
    public void assertThatUserCanResetPassword() {
        log.info(name.getMethodName());
        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");

        String oldPassword = user.getPassword();
        ZonedDateTime daysAgo = ZonedDateTime.now().minusHours(2);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey(resetKey);
        userRepository.save(user);

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());
        assertTrue("User should be present", maybeUser.isPresent());
        assertNull("Reset date should be null", maybeUser.get().getResetDate());
        assertNull("Reset key should be resetted", maybeUser.get().getResetKey());
        assertNotEquals("Password should not be old password", oldPassword, maybeUser.get().getPassword());
    }

    @Test
    public void testFindNotActivatedUsersByCreationDateBefore() {
        log.info(name.getMethodName());
        userService.removeNotActivatedUsers();
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        assertTrue(users.isEmpty());
    }
}
