package application.havenskin.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloundinaryServiceImpl implements UploadImageFile{

    private final Cloudinary cloudinary;

    @Override
    public String uploadImageFile(MultipartFile file) throws IOException {
        // upload ảnh lên Cloudinary
        // truyền theo nhị phân
        Map<? , ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        // lấy url
        return uploadResult.get("url").toString();
    }
}
