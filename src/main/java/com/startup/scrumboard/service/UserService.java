package com.startup.scrumboard.service;

import com.startup.scrumboard.model.entity.User;
import com.startup.scrumboard.repository.UserRepository;
import com.startup.scrumboard.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User create(User user) {
        Assert.notNull(user, "Не указан пользователь!");
        Assert.notNull(user.getPassword(), "Пароль не указан!");
        Assert.isTrue(user.getPassword().length()>=6, "Пароль должен быть более 6 символов!");
        Assert.isNull(userRepository.findByLogin(user.getLogin()), "Пользователь с таким логином уже существует!");
        user.setPassword(SecurityUtils.getPwd(user.getPassword()));
        return userRepository.save(user);

    }

    public User update(User user) {
        Assert.notNull(user, "Не указан пользователь!");
        Assert.notNull(user.getLogin(), "Не указан пользователь!");
        User entity = userRepository.findOne(user.getLogin());
        Assert.notNull(entity, "Пользователь не найден!");

        if (user.getEmail() != null) {
            entity.setEmail(user.getEmail());
        }

        if (user.getFirstName() != null) {
            entity.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null) {
            entity.setLastName(user.getLastName());
        }
        if (user.getMiddleName() != null) {
            entity.setMiddleName(user.getMiddleName());
        }

        if (user.getPassword() != null) {
            Assert.isTrue(user.getPassword().length()>=6, "Пароль должен быть более 6 символов!");
            entity.setPassword(SecurityUtils.getPwd(user.getPassword()));
        }

        return userRepository.save(entity);
    }

    public Page<User> list(Pageable pageable, String login) {
        Page<User> users;

        if (!StringUtils.isEmpty(login)) {
            users = userRepository.findByLoginLike(login, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return users;
    }

    public void delete(String id) {
        Assert.notNull(id, "Не указан id пользователь!");
        User entity = userRepository.findOne(id);
        Assert.notNull(entity, "Пользователь не найден!");
        userRepository.delete(id);

    }

    public User get(String id) {
        return userRepository.findOne(id);
    }

    public String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "Anonymous";
        } else {
            return auth.getName();
        }
    }
}
