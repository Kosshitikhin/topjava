package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);

        if (repository.get(id) != null) {
            return repository.remove(id) != null;
        } else {
            return false;
        }
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);

        if(repository.get(id) != null) {
            return repository.get(id);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> allUsers = (ArrayList<User>) repository.values();
        allUsers.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getName().compareTo(o2.getName()) == 0) {
                    return o1.getEmail().compareTo(o2.getEmail());
                } else
                return o1.getName().compareTo(o2.getName());
            }
        });

        return allUsers;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);

        User userByEmail = null;
        List<User> users = (ArrayList<User>) repository.values();
        for (User u : users) {
            if (email.equalsIgnoreCase(u.getEmail())) {
                userByEmail = u;
            }
        }

        return userByEmail;
    }

}
