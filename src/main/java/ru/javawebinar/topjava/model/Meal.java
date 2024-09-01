package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.SELECT_ALL, query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = Meal.SELECT, query = "SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = Meal.SELECT_BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id=:userId " +
                "AND m.dateTime >= :start AND m.dateTime < :end ORDER BY m.dateTime DESC")
})
@Entity
@Table(name = "meal")
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String SELECT = "Meal.select";
    public static final String SELECT_ALL = "Meal.select_all";
    public static final String SELECT_BETWEEN = "Meal.select_between";

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @PositiveOrZero
    @Column(name = "calories", nullable = false)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
