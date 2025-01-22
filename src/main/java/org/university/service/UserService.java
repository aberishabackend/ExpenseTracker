package org.university.service;

import org.university.model.AppUser;

public interface UserService {
    AppUser saveUser(AppUser user);
    AppUser findByUsername(String username);
}
