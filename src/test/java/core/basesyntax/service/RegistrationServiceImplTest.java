package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.*;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }
    
    @Test
    void Should_ThrowException_When_LoginsEquals() {
        User unexpected = new User("alex213", "first431", 18);
        storageDao.add(unexpected);
        User actual = new User("alex213", "second", 21);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException if logins are equal");
    }

    @Test
    void Should_ThrowException_When_LoginLess6Char() {
        User actual = new User("olya2", "665433", 19);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when login less than 6 characters");
    }

    @Test
    void Should_ThrowException_When_PasswordLess6Char() {
        User actual = new User("SergiiLee4", "apple", 29);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when password less than 6 characters");
    }

    @Test
    void Should_ThrowException_When_AgeLessThan18() {
        User actual = new User("angelina566", "sOCIABLE3444", 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when age less than 18");
    }

    @Test
    void Should_ThrowException_When_NullLogin() {
        User actual = new User(null, "private321", 41);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when login equal null");
    }

    @Test
    void Should_ThrowException_When_NullPassword() {
        User actual = new User("angela", null, 29);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when password equal null");
    }

    @Test
    void Should_ThrowException_When_NullAge() {
        User actual = new User("geraSike", "givemoney", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when age equal null");
    }

    @Test
    void Should_ThrowException_When_NullUser() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "Expected RegistrationException when User equal null");
    }

    @Test
    void Should_Equals_When_ValidUser() {
        User expected = new User("michael32", "pedro33", 23);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(expected, storageDao.get("michael32"));
    }

    @Test
    void Should_ThrowException_When_LoginContainsSomeSymbols() {
        User firstActual = new User("oleksii#2!1", "5331256", 32);
        User secondActual = new User("s@nchous^%55", "3458898sa", 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(firstActual),
                "Expected RegistrationException if login contain's !, @, #, %, ^.");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(secondActual),
                "Expected RegistrationException if login contain's !, @, #, %, ^.");
    }
}
