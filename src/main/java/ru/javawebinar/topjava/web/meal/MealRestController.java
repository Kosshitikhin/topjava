package ru.javawebinar.topjava.web.meal;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealController {

    public Meal get(int id) {
        return super.get(id, authUserId());
    }

    public void delete(int id) {
        super.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        super.update(meal, authUserId(), id);
    }

    public Meal create(Meal meal) {
        return super.create(meal, authUserId());
    }

    public List<MealTo> getAll() {      //конвертация в MealTo без фильтрации
        return MealsUtil.getTos(super.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);

    }



}