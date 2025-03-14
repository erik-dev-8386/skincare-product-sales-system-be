package application.havenskin.config;

import lombok.Data;
import org.apache.maven.plugins.annotations.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration // Để Spring Boot quét được
@ConfigurationProperties(prefix = "momo")
public class MomoConfig {
    private String partnerCode;
    private String accessKey;
    private String secretKey;
    private String returnUrl;
    private String ipnUrl;
    private String requestType;
}
