package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;


import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData extends AbstractBaseEntity {
    public static final int USER_ID = 100_000;
    public static final int ADMIN_ID = 100_001;
    public static final int NOT_FOUND = 10;
    public static final int USER_M = 1;
    public static final int ADMIN_M = 2;
    public static final LocalDate START_TIME = LocalDate.of(2020, 12, 10);
    public static final LocalDate END_TIME = LocalDate.of(2020, 12, 25);

    public static final Meal USER_MEAL = new Meal(USER_M, LocalDateTime.of(2020, 12, 20, 12, 0, 0), "Обед", 550);
    public static final Meal ADMIN_MEAL = new Meal(ADMIN_M, LocalDateTime.of(2020, 12, 20, 12, 0, 0), "Обед", 550);

//    public static Meal getUpdated() {
//        Meal updated = new Meal(USER_MEAL);
//        updated.setId(USER_M);
//        updated.setDateTime(LocalDateTime.of(2021, 1, 10, 10, 0, 0));
//        updated.setDescription("Завтрак");
//        updated.setCalories(800);
//        return updated;
//    }


}


