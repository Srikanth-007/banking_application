package com.cts.CBLOS.service;
 
import com.cts.CBLOS.model.User;
 
public interface UserService {
    void saveUser(User user);
    boolean isAdmin(String email);
    User findByEmail(String email);
}