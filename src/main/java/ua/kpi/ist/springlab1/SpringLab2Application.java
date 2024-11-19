package ua.kpi.ist.springlab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ua.kpi.ist.springlab1.model.Product;
import ua.kpi.ist.springlab1.repository.CatalogRepository;

@SpringBootApplication
public class SpringLab2Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringLab2Application.class, args);
    }

}

@Configuration
class AppConfig {

    // Singleton
    @Bean
    public CatalogRepository catalogRepository() {return new CatalogRepository();}

    // Prototype
    @Bean
    @Scope("prototype")
    public Product product() {
        return new Product();
    }
}
