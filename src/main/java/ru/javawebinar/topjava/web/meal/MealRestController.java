package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Controller
public class MealRestController {

    private final MealService mealService;

    public MealRestController(MealService mealService) {
        this.mealService = mealService;
    }

    public Meal create(Meal meal) {
        meal.setUserId(SecurityUtil.authUserId());
        return mealService.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        meal.setUserId(SecurityUtil.authUserId());
        mealService.update(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        mealService.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        return mealService.get(id, SecurityUtil.authUserId());
    }

    public Collection<Meal> getAll() {
        return mealService.getAll(SecurityUtil.authUserId());
    }
}