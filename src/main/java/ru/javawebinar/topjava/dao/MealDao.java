package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    Meal addMeal(Meal meal);

    Meal getMealById(Long id);

    List<Meal> getMeals();

    Meal updateMeal(Meal meal, Long id);

    Meal deleteMealById(Long id);
}
