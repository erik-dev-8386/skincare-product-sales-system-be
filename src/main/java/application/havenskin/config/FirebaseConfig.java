package application.havenskin.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {
    private FirebaseApp firebaseApp;
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(
                        new ClassPathResource("Skin-care.json").getInputStream()))
                .setStorageBucket("haven-skin-03-2025-d1f5f.firebasestorage.app")
                .build();

        // Nếu chưa khởi tạo FirebaseApp nào, khởi tạo và trả về; nếu đã có, trả về instance hiện có.
        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }
}