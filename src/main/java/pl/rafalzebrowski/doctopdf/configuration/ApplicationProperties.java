package pl.rafalzebrowski.doctopdf.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "application.config")
public record ApplicationProperties(String apikey) {}
