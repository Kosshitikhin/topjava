package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql",config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {


    @Autowired
    private MealService service;
    @Autowired
    MealRepository mealRepository;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL1_ID, USER_ID);

    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        Assert.assertNull(mealRepository.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void notFoundDelete() throws Exception {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(1, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotOwn() throws Exception {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(MEAL1_ID, USER_ID));
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1);
        service.update(updated, USER_ID);
        assertMatch(MEAL1, updated);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
    }


//    @Test
//    public void getBetweenInclusive() throws Exception {
//        assertMatch(service.getBetweenInclusive(START_TIME, END_TIME, USER_ID), MEAL3, MEAL2, MEAL1);
//    }
//
//    @Test
//    public void getBetweenWithNullDates() throws Exception {
//        assertMatch(service.getBetweenInclusive(null, null, USER_ID), MEALS);
//    }

}