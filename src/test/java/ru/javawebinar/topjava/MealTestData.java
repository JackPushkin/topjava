package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final int USER_ID = START_SEQ;
    public static final int MEAL_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final User user =
            new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);

    public static final Meal meal = new Meal(MEAL_ID,
            LocalDateTime.parse("2024-01-08 04:05:06", formatter), "description", 1500);
    public static final Meal meal1 = new Meal(MEAL_ID + 1,
            LocalDateTime.parse("2021-01-08 10:05:06", formatter), "description", 1200);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.parse("2000-01-01 12:00:00", formatter), "meal", 1200);
    }

    public static Meal getUpdated(LocalDateTime dateTime) {
        Meal updated = new Meal(meal);
        updated.setDateTime(dateTime);
        updated.setDescription("UpdatedDesc");
        updated.setCalories(1000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
