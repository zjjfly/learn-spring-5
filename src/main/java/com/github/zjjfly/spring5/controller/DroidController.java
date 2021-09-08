package com.github.zjjfly.spring5.controller;

import com.github.zjjfly.spring5.config.Droid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zjjfly[[https://github.com/zjjfly]]
 * @date 2021/3/12
 */
@RestController
@RequestMapping("/droid")
public class DroidController {

    private final Droid droid;

    public DroidController(Droid droid) {
        this.droid = droid;
    }

    @GetMapping
    Droid getDroid() {
        return droid;
    }
}
