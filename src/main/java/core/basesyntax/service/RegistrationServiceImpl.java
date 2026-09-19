package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_SYMBOLS_LOGIN = 6;
    private static final int MIN_SYMBOLS_PASSWORD = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }

        if (user.getLogin() == null || user.getLogin().length() < MIN_SYMBOLS_LOGIN) {
            throw new RegistrationException("Login is invalid");
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_SYMBOLS_PASSWORD) {
            throw new RegistrationException("Password is invalid");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User is too young");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }

        return storageDao.add(user);
    }
}
