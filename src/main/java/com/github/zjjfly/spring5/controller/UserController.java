package com.github.zjjfly.spring5.controller;

import com.github.zjjfly.spring5.domain.User;
import com.github.zjjfly.spring5.repo.UserJpaRepo;
import com.github.zjjfly.spring5.repo.UserRepo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

/**
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021/2/19
 */
@RestController
@RequestMapping("/test")
public class UserController {

    private final UserRepo userRepo;

    private final UserJpaRepo userJpaRepo;

    private final TransactionTemplate transactionTemplate;

    public UserController(UserRepo userRepo, UserJpaRepo userJpaRepo, TransactionTemplate transactionTemplate) {
        this.userRepo = userRepo;
        this.userJpaRepo = userJpaRepo;
        this.transactionTemplate = transactionTemplate;
    }

    @PostMapping("/saveUser")
    public long saveUser(@Validated @RequestBody User user) {
        return userRepo.save(user);
    }

    @PostMapping("/saveUserWithJpa")
    public long saveUserWithJpa(@RequestBody User user) {
        return userJpaRepo.save(user).getId();
    }

    @GetMapping("/{name}")
    public List<User> searchByName(@PathVariable String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("name", contains().caseSensitive())
                                               .withMatcher("company", startsWith().ignoreCase());
        User user = new User();
        user.setName(name);
        user.setSex(User.Gender.MALE);
        Example<User> example = Example.of(user, matcher);
        return userJpaRepo.findAll(example);
    }


}
