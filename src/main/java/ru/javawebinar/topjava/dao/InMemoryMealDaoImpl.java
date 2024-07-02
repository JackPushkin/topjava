package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMealDaoImpl implements MealDao {

    private static volatile InMemoryMealDaoImpl INSTANCE;
    private final ConcurrentHashMap<Long, Meal> mealsTable;
    private final AtomicLong id;

    private InMemoryMealDaoImpl() {
        mealsTable = new ConcurrentHashMap<>();
        id = new AtomicLong(1);
    }

    public static InMemoryMealDaoImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (InMemoryMealDaoImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new InMemoryMealDaoImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Meal addMeal(Meal meal) {
        meal.setId(increaseId());
        return mealsTable.put(meal.getId(), meal);
    }

    @Override
    public Meal getMealById(Long id) {
        return mealsTable.get(id);
    }

    @Override
    public List<Meal> getMeals() {
        return new ArrayList<>(mealsTable.values());
    }

    @Override
    public Meal updateMeal(Meal meal, Long id) {
        Meal currentMeal = mealsTable.get(id);
        if (currentMeal != null) {
            if (meal.getDateTime() != null) currentMeal.setDateTime(meal.getDateTime());
            if (meal.getCalories() != null) currentMeal.setCalories(meal.getCalories());
            if (meal.getDescription() != null) currentMeal.setDescription(meal.getDescription());
        } else {
            throw new RuntimeException(String.format("Meal with id=%d not found", id));
        }
        return mealsTable.put(id, currentMeal);
    }

    @Override
    public Meal deleteMealById(Long id) {
        return mealsTable.remove(id);
    }

    private Long increaseId() {
        return id.getAndIncrement();
    }
}
