package com.cts.CBLOS.config;
 
import com.cts.CBLOS.model.User;
import com.cts.CBLOS.model.User.UserRole;
import com.cts.CBLOS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
 
@Component
public class AdminUserInitilizer implements CommandLineRunner {
 
    @Autowired
    private UserRepository userRepository;
 
    @Value("${app.admin.email:admin@CTS.com}")
    private String adminEmail;
 
    @Value("${app.admin.password:admin123}")
    private String adminPassword;
 
    @Override
    public void run(String... args) {
        if (!userRepository.findByEmail(adminEmail).isPresent()) {
            User adminUser = new User();
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(adminPassword);
            adminUser.setRole(UserRole.ADMIN);
            userRepository.save(adminUser);
            System.out.println(">>> Default Admin User Created <<<");
        }
    }
}