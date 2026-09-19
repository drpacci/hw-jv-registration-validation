package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_SYMBOLS_LOGIN = 6;
    private static final int MIN_SYMBOLS_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("Login is null");
        }
        if (user.getLogin().length() < MIN_SYMBOLS_LOGIN) {
            throw new RegistrationException(
                    "Login '" + user.getLogin() + "' is too short. Minimum length is " + MIN_SYMBOLS_LOGIN
            );
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password is null");
        }
        if (user.getPassword().length() < MIN_SYMBOLS_PASSWORD) {
            throw new RegistrationException(
                    "Password '" + user.getPassword() + "' is too short. Minimum length is " + MIN_SYMBOLS_PASSWORD
            );
        }

        if (user.getAge() == null) {
            throw new RegistrationException("Age is null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    "Age " + user.getAge() + " is too young. Minimum age is " + MIN_AGE
            );
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(
                    "User with login '" + user.getLogin() + "' already exists"
            );
        }
        return storageDao.add(user);
    }
}
