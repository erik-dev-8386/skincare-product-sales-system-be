package application.havenskin.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseService {
    private final String bucketName = "swp391-2004.appspot.com";

    public String uploadImage(MultipartFile file) throws IOException {
        // Khởi tạo Firebase Storage
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(
                        new ClassPathResource("Skin-care.json").getInputStream()))
                .build()
                .getService();

        // Tạo tên file duy nhất để tránh trùng
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = "Images/" + UUID.randomUUID() + fileExtension;

        // Upload file lên Firebase Storage
        Blob blob = storage.create(
                BlobInfo.newBuilder(bucketName, fileName).setContentType(file.getContentType()).build(),
                file.getBytes()
        );
        // Lấy URL kèm token
        String downloadUrl = String.format(
                "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                bucketName,
                fileName.replace("/", "%2F"),
                blob.getGeneratedId()
        );

        return downloadUrl;
    }
}


