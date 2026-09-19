package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("test");
        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setPassword("test");
        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_underAge_notOk() {
        User user = new User();
        user.setAge(15);
        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("test123");
        user.setPassword("qwerty");
        user.setAge(20);

        registrationService.register(user);

        User user2 = new User();
        user2.setLogin("test123");
        user2.setPassword("abcdef");
        user2.setAge(30);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user2)
        );
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("loginIsGood");
        user.setAge(23);
        user.setPassword("password");

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
        assertEquals(user, Storage.people.get(0));
    }
}
