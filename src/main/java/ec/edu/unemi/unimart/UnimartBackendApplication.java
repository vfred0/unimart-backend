package ec.edu.unemi.unimart;

import ec.edu.unemi.unimart.configurations.RsaKeys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeys.class)
public class UnimartBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnimartBackendApplication.class, args);
    }

}
