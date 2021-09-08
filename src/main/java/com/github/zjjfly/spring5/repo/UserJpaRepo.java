package com.github.zjjfly.spring5.repo;

import com.github.zjjfly.spring5.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021/2/19
 */
@Repository
public interface UserJpaRepo extends JpaRepository<User, Long> {
}
