package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql",config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_M, USER_ID);
        meal.equals(USER_MEAL);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_M, USER_ID);
        service.getAll(USER_ID).equals(null);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(USER_M, NOT_FOUND);
    }
//
    @Test
    public void getAll() throws Exception{
        List<Meal> meals = service.getAll(USER_ID);
        meals.equals(Arrays.asList(USER_MEAL));
    }
//
    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_MEAL);
        updated.setCalories(900);
        updated.setDescription("Dinner");
        updated.setDateTime(LocalDateTime.of(2020, 11, 30, 10, 0, 0));
        service.update(updated, USER_ID);
        service.get(USER_M, USER_ID).equals(updated);
    }
//
    @Test
    public void create() throws Exception {
        Meal created = service.create(USER_MEAL, USER_ID);
        created.equals(USER_MEAL);
    }
//
//    @Test(expected = DataAccessException.class)
//    public void dublicateDateCreate() throws Exception {
//        service.create(new Meal(USER_MEAL), USER_ID);
//    }

    @Test
    public void getBetweenHalfOpen() throws Exception {
        service.getBetweenInclusive(START_TIME, END_TIME, USER_ID);
    }
}