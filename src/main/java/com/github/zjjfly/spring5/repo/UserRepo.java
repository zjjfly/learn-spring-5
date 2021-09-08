package com.github.zjjfly.spring5.repo;

import com.github.zjjfly.spring5.domain.User;

/**
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021/2/19
 */
public interface UserRepo {
    long save(User user);
}
