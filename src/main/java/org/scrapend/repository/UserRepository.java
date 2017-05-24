package org.scrapend.repository;

import org.scrapend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pelupotter on 22/05/17.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

