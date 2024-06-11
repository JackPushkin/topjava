package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo1 = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(15, 0), 2000);
        mealsTo1.forEach(System.out::println);

        System.out.println("-------------------------------------------------------------------------------------");

        List<UserMealWithExcess> mealsTo2 = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(15, 0), 2000);
        mealsTo2.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        Map<Integer, List<UserMeal>> mealsTable = new HashMap<>();
        for (UserMeal meal : meals) {
            int day = meal.getDateTime().getDayOfMonth();
            if (!mealsTable.containsKey(day)) {
                mealsTable.put(day, new ArrayList<>());
            }
            mealsTable.get(day).add(meal);
        }
        for (List<UserMeal> value : mealsTable.values()) {
            int calories = 0;
            boolean excess = false;
            for (UserMeal userMeal : value) {
                calories += userMeal.getCalories();
            }
            if (calories > caloriesPerDay) {
                excess = true;
            }
            for (UserMeal userMeal : value) {
                if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                    mealsWithExcess.add(toUserMealWithExcess(userMeal, excess));
                }
            }
        }
        return mealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        final List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        meals.stream()
                .collect(Collectors.groupingBy(o -> o.getDateTime().getDayOfMonth()))
                .forEach((integer, userMeals) -> {
                    Integer calories = userMeals.stream().reduce(0, (num, meal) -> num + meal.getCalories(), Integer::sum);
                    boolean excess = calories > caloriesPerDay;
                    userMeals.stream()
                            .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(meal -> toUserMealWithExcess(meal, excess)).forEach(mealsWithExcess::add);
                });
        return mealsWithExcess;
    }

    private static UserMealWithExcess toUserMealWithExcess(UserMeal meal, boolean excess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
