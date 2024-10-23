package ua.kpi.ist.springlab1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Order(2)
public class SpringLab1Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringLab1Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Begin of main.");
        System.out.println("Hello from Spring Boot!");
        System.out.println("End of main.");
    }
}


@Component
@Order(1)
class First implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("First");
    }
}
@Component
@Order(3)
class Second implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("Second");
    }
}