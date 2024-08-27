package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.NOT_FOUND;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.getNew;
import static ru.javawebinar.topjava.MealTestData.getUpdated;
import static ru.javawebinar.topjava.MealTestData.user;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static {
        SLF4JBridgeHandler.install();
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), user.getId());
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, user.getId()), newMeal);
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.parse(
                        "2024-01-08 04:05:06", formatter), "duplicate", 1300), user.getId()));
    }

    @Test
    public void update() {
        Meal updated = getUpdated(LocalDateTime.parse("2000-01-01 12:00:00", formatter));
        service.update(updated, user.getId());
        assertMatch(service.get(updated.getId(), user.getId()), updated);
    }

    @Test
    public void updateWithDuplicateDateTime() {
        Meal updated = getUpdated(LocalDateTime.parse("2021-01-08 10:05:06", formatter));
        assertThrows(DataAccessException.class, () -> service.update(updated, user.getId()));
    }

    @Test
    public void updateByAnotherUser() {
        Meal updated = getUpdated(LocalDateTime.parse("2000-01-01 12:00:00", formatter));
        assertThrows(NotFoundException.class, () -> service.update(updated, user.getId() + 1));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, user.getId());
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, user.getId()));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, user.getId()));
    }

    @Test
    public void deletedByAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, user.getId() + 1));
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, user.getId());
        assertMatch(meal, MealTestData.meal);
    }

    @Test
    public void getByAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, user.getId() + 1));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(user.getId());
        MealTestData.assertMatch(all, MealTestData.meal, MealTestData.meal1);
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(
                LocalDate.of(2020, 1, 1), LocalDate.of(2022, 1, 1), user.getId());
        MealTestData.assertMatch(all, MealTestData.meal1);
    }
}
