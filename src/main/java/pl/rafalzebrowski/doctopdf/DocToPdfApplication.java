package pl.rafalzebrowski.doctopdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DocToPdfApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocToPdfApplication.class, args);
    }
}