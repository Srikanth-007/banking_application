package com.cts.CBLOS.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.CBLOS.model.User;
import com.cts.CBLOS.repository.UserRepository;
 
import java.util.Optional;
 
@Service
public class AuthenticationService {
 
    @Autowired
    private UserRepository userRepository;
 
    public boolean authenticate(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);
 
        if (userOptional.isPresent()) {
            User user = userOptional.get();
         
            System.out.println("Stored Password for " + email + ": " + user.getPassword());
            System.out.println("Entered Password: " + password);
            return user.getPassword().equals(password);
        } else {
            System.out.println("User with email " + email + " not found!");
            return false; 
        }
    }
}