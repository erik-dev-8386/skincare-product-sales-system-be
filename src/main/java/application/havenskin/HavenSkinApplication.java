package application.havenskin;

import application.havenskin.config.MomoConfig;
import com.google.api.client.util.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableConfigurationProperties(MomoConfig.class)
public class HavenSkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(HavenSkinApplication.class, args);
    }

}
