package org.scrapend.service;

import org.scrapend.model.User;

import java.util.List;

/**
 * Created by pelupotter on 22/05/17.
 */

public interface UserService {
    public User findById(Long id);
    public User findByUsername(String username);
    public List<User> findAll();
}
