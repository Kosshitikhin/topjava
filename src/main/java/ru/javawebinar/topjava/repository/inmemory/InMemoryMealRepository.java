package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meals : MealsUtil.meals) {
            save(meals, meals.getUserId());
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        } else if (meal.getUserId() == userId) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(id) != null && repository.get(id).getUserId() == userId) {
            return repository.remove(id) != null;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getAllFiltered(userId, meal -> true);
    }

    public List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        Collection<Meal> meals = repository.values();
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());

    }
    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startTime, LocalDateTime endTime, int userId) {
        return getAllFiltered(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startTime, endTime));
    }
}

