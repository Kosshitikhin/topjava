package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

public class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Collection<Meal> getAll(int userId) {
        log.info("getAll");

        return service.getAll(userId);
    }

    public Meal get(int id, int userId) {
        log.info("get {}", id);

        return service.get(id, userId);
    }

    public Meal create(Meal meal, int userId) {
        log.info("Create {}", meal);

        return service.create(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("Delete {}", id);

        service.delete(id, userId);
    }

    public void update(Meal meal, int userId, int id) {
        log.info("Update meal {} with userId {}", meal, meal.getId());
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

}
