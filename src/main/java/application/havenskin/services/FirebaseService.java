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
//    public String uploadImage(MultipartFile file) throws IOException {
//        Bucket bucket = StorageClient.getInstance().bucket();
//        // trả về bucket trong config
//        String fileName = file.getOriginalFilename();
//        // tạo tên file
//        Blob blob = bucket.create(fileName,file.getBytes(), file.getContentType());
//        //truyền ảnh vào
//        return blob.getMediaLink();
//        // TRẢ URL ẢNH
//    }

        private final String bucketName = "haven-skin-03-2025-d1f5f.firebasestorage.app"; // Sửa lại đúng Bucket

//        public String uploadImage(MultipartFile file) throws IOException {
//            Storage storage = StorageOptions.getDefaultInstance().getService();
//            Bucket bucket = storage.get(bucketName);
//
//            // Tạo tên file duy nhất để tránh trùng
//            String fileName = "Images/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
//
//            // Upload file lên Firebase Storage
//            Blob blob = bucket.create(fileName, file.getInputStream(), file.getContentType());
//
//            // Lấy URL chứa token
//            String downloadUrl = String.format(
//                    "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media&token=%s",
//                    bucketName, fileName.replace("/", "%2F"), blob.getGeneratedId().split("/")[1]);
//
//            return downloadUrl;
//        }
public String uploadImage(MultipartFile file) throws IOException {
    // Khởi tạo Firebase Storage
    Storage storage = StorageOptions.newBuilder()
            .setCredentials(GoogleCredentials.fromStream(
                    new ClassPathResource("spring-firebase-haven-skin.json").getInputStream()))
            .build()
            .getService();

    // Tạo tên file duy nhất để tránh trùng
    String fileName = "Images/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

    // Upload file lên Firebase Storage
//    Blob blob = storage.create(
//            BlobId.of(bucketName, fileName),
//            file.getInputStream(),
//            BlobInfo.newBuilder(bucketName, fileName).setContentType(file.getContentType()).build()
//    );

//    Blob blob = storage.create(BlobId.of(bucketName, fileName),
//            file.getInputStream(),
//            BlobInfo.newBuilder(bucketName, fileName).setContentType(file.getContentType()).build()
//    );
    Blob blob = storage.create(
            BlobInfo.newBuilder(bucketName, fileName).setContentType(file.getContentType()).build(),
            file.getInputStream()
    );
    // Lấy URL kèm token
    String downloadUrl = String.format(
            "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media&token=%s",
            bucketName,
            fileName.replace("/", "%2F"),
            blob.getGeneratedId() // Sử dụng generatedId làm token
    );

    return downloadUrl;
}
    }


