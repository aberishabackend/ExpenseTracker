package org.university.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.university.model.AppUser;
import org.university.model.Role;
import org.university.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }




    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByUsername("admin").isEmpty()) {
            AppUser adminUser = new AppUser();
            adminUser.setFullName("Admin User");
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder
                    .encode("admin123"));
            adminUser.setRole(Role.ADMIN);
            userRepository.save(adminUser);
            System.out.println("Admin user created: username='admin', password='admin123'");
        }
    }
}
