package ua.kpi.ist.springlab1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@OpenAPIDefinition(
        info = @Info(

                title = "User Service Demo",
                contact = @Contact(

                        name = "Victoria Sabadan",
                        email = "victoriasabadan1@gmail.com"
                ),

                description = "Product API",
                version = "1.0",
                license = @License(name = "Apache 2.0", url =

                        "https://www.apache.org/licenses/LICENSE-2.0.html")

        ),
        servers = {

                @Server(url = "http://localhost:8080", description = "test server"),
                @Server(url = "http://example.com", description = "production server")
        }

)
@SpringBootApplication
@EnableJpaRepositories(basePackages = "ua.kpi.ist.springlab1.repository")
@EntityScan(basePackages = "ua.kpi.ist.springlab1.model")
public class SpringLab2Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringLab2Application.class, args);
    }

}

