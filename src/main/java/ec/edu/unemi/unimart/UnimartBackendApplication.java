package ec.edu.unemi.unimart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableWebSecurity(debug = true)
public class UnimartBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnimartBackendApplication.class, args);
    }

}
