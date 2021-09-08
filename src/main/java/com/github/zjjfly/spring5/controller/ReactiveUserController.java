//package com.github.zjjfly.spring5.controller;
//
//import com.github.zjjfly.spring5.domain.User;
//import com.github.zjjfly.spring5.repo.ReactiveUserRepo;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//
///**
// * @author zjjfly[https://github.com/zjjfly]
// * @date 2021/2/24
// */
//@RestController
//@CrossOrigin(origins = "*")
//@RequestMapping("/user/reactive")
//public class ReactiveUserController {
//
//    private final ReactiveUserRepo reactiveUserRepo;
//
//    public ReactiveUserController(ReactiveUserRepo reactiveUserRepo) {
//        this.reactiveUserRepo = reactiveUserRepo;
//    }
//
//    @GetMapping("/all")
//    public Flux<User> allUsers() {
//        return reactiveUserRepo.findAll().take(12);
//    }
//}
