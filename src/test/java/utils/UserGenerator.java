package utils;

import models.User;

import java.util.UUID;

public class UserGenerator {

    public static User getRandomUser() {
        String uniqueEmail = "test_" + UUID.randomUUID() + "@yandex.ru";
        return new User(uniqueEmail, "123456", "TestUser");
    }

    public static User getNewUserWithInvalidPassword() {
        String uniqueEmail = "test_" + UUID.randomUUID() + "@yandex.ru";
        return new User(uniqueEmail, "123", "ShortPassUser");
    }
}