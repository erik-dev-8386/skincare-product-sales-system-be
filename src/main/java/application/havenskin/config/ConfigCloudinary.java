package application.havenskin.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConfigCloudinary {

    @Value("${CLOUDINARY_CLOUD_NAME}")
    private String CLOUDINARY_CLOUD_NAME;
    @Value("${CLOUDINARY_API_KEY}")
    private String CLOUDINARY_API_KEY;
    @Value("${CLOUDINARY_API_SECRET}")
    private String CLOUDINARY_API_SECRET;
    @Bean
    public Cloudinary configKey() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUDINARY_CLOUD_NAME);
        config.put("api_key", CLOUDINARY_API_KEY);
        config.put("api_secret", CLOUDINARY_API_SECRET);
        return new Cloudinary(config);
    }
}
