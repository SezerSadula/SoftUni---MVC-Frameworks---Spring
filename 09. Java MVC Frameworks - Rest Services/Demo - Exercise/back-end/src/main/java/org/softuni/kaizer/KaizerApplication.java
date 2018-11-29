package org.softuni.kaizer;

import org.softuni.kaizer.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KaizerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaizerApplication.class, args);
    }
}