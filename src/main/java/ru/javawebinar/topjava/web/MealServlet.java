package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMealDaoImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private final MealDao mealDao = InMemoryMealDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        action = action == null ? "" : action;
        switch (action) {
            case "update": {
                log.debug("redirect to add_meal.jsp");
                long mealId = Long.parseLong(request.getParameter("mealId"));
                Meal updatedMeal = mealDao.getMealById(mealId);
                request.setAttribute("updatedMeal", updatedMeal);
                request.getRequestDispatcher("/update_meal.jsp").forward(request, response);
                break;
            }
            case "delete": {
                long mealId = Long.parseLong(request.getParameter("mealId"));
                log.debug("delete meal with id={}", mealId);
                mealDao.deleteMealById(mealId);
                request.setAttribute("mealsList", getMealsTo());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            }
            default: {
                log.debug("redirect to meals.jsp");
                request.setAttribute("mealsList", getMealsTo());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            Meal meal = new Meal(dateTime, description, calories);
            if ("update".equals(request.getParameter("action"))) {
                long mealId = Integer.parseInt(request.getParameter("id"));
                log.debug("update meal with id={}", mealId);
                mealDao.updateMeal(meal, mealId);
            } else {
                log.debug("create meal={}", meal);
                mealDao.addMeal(meal);
            }
            request.setAttribute("mealsList", getMealsTo());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } catch (RuntimeException exception) {
            log.debug("processing request error. Invalid request parameters: dateTime={}, description={}, calories={}",
                    request.getParameter("dateTime"),
                    request.getParameter("description"),
                    request.getParameter("calories"));
            response.sendError(400, "invalid data");
        }
    }

    private List<MealTo> getMealsTo() {
        return MealsUtil.filteredByStreams(mealDao.getMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
    }
}
