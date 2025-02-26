package application.havenskin.services;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FirebaseService {
    public String uploadImage(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        // trả về bucket trong config
        String fileName = file.getOriginalFilename();
        // tạo tên file
        Blob blob = bucket.create(fileName,file.getBytes(), file.getContentType());
        //truyền ảnh vào
        return blob.getMediaLink();
        // TRẢ URL ẢNH
    }
}
