package application.havenskin.services;

import application.havenskin.models.*;
import application.havenskin.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogHashtagRepository blogHashtagRepository;

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;
    private FirebaseService firebaseService;

    @Autowired
    private BlogImagesRepository blogImagesRepository;
//    @Autowired
//    private Mapper mapper;
//    public List<Blogs>  getAllBlogs(){
//        return blogRepostiory.findAll();
//    }
//    public Blogs getBlogById(String id){
//        return blogRepostiory.findById(id).get();
//    }
//    public Blogs addBlog(Blogs blog){
//        return blogRepostiory.save(blog);
//    }
//    public Blogs updateBlog(String id, BlogDTO blog){
//        Blogs blogToUpdate = blogRepostiory.findById(id).orElseThrow(()-> new RuntimeException("Blog not found"));
//        mapper.updateBlogs(blogToUpdate, blog);
//        return blogRepostiory.save(blogToUpdate);
//    }
//    public void deleteBlog(String id){
//        blogRepostiory.deleteById(id);
//    }
//    public List<Blogs> addListOfBlogs(List<Blogs> blogs){
//        return blogRepostiory.saveAll(blogs);
//    }

    public List<Blogs> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blogs getBlogById(String id) {
        return blogRepository.findById(id).get();
    }

    public Blogs getBlogByTitle(String title) {
        return blogRepository.findByTitle(title);
    }

//    public Blogs createBlog(Blogs blog) {
//        return blogRepository.save(blog);
//    }

    public Blogs createBlog(Blogs blog, List<MultipartFile> images) throws IOException {
        // Tìm User theo email
        Users user = userRepository.findByEmail(blog.getUser().getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + blog.getUser().getEmail()));

        // Tìm BlogCategory theo tên
        BlogCategory category = blogCategoryRepository
                .findByBlogCategoryName(blog.getBlogCategory().getBlogCategoryName())
                 .orElseThrow(() -> new RuntimeException("BlogCategory not found with name: " + blog.getBlogCategory().getBlogCategoryName()));


        // Tìm Hashtags theo tên
        List<BlogHashtag> hashtags = new ArrayList<>();
        if (blog.getHashtags() != null && !blog.getHashtags().isEmpty()) {
            for (BlogHashtag ht : blog.getHashtags()) {
                BlogHashtag existingHashtag = blogHashtagRepository.findHashtagByName(ht.getBlogHashtagName())
                        .orElseThrow(() -> new RuntimeException("Hashtag not found: " + ht.getBlogHashtagName()));
                hashtags.add(existingHashtag);
            }
        }



        // Gán dữ liệu vào blog mới
        blog.setUser(user);
        blog.setBlogCategory(category);
        blog.setHashtags(hashtags);
        Blogs savedBlog = blogRepository.save(blog);

        if(images != null && !images.isEmpty()) {
            List<BlogImages> list = new ArrayList<>();
            for (MultipartFile file : images) {
                String imageUrl = firebaseService.uploadImage(file);

                BlogImages blogImages = new BlogImages();
                blogImages.setImageURL(imageUrl);
                blogImages.setBlogId(blog.getBlogId());
                blogImages.setBlog(savedBlog);

                list.add(blogImages);
            }
            blogImagesRepository.saveAll(list);
            savedBlog.setBlogImages(list);
        }

        return blogRepository.save(blog);
    }


//    public Blogs updateBlogById(String id, Blogs blog) {
//        Blogs existingBlog = blogRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Blog not found"));
//
//        if (blog.getBlogCategory() != null) {
//            existingBlog.setBlogCategory(blog.getBlogCategory());
//        }
//        if (blog.getBlogContent() != null) {
//            existingBlog.setBlogContent(blog.getBlogContent());
//        }
//        if (blog.getBlogImages() != null) {
//            existingBlog.setBlogImages(blog.getBlogImages());
//        }
//        if (blog.getHashtags() != null) {
//            existingBlog.setHashtags(blog.getHashtags());
//        }
//        if (blog.getUserId() != null) {
//            existingBlog.setUserId(blog.getUserId());
//        }
//        if (blog.getPostedTime() != null) {
//            existingBlog.setPostedTime(blog.getPostedTime());
//        }
//        if (blog.getDeletedTime() != null) {
//            existingBlog.setDeletedTime(blog.getDeletedTime());
//        }
//        if (blog.getStatus() != 0) {
//            existingBlog.setStatus(blog.getStatus());
//        }
//
//        return blogRepository.save(existingBlog);
//    }
public Blogs updateBlogByTitle(String blogTitle, Blogs blog) {
    Blogs existingBlog =  blogRepository.findByTitle(blogTitle);
    if (existingBlog == null) {
        throw new RuntimeException("Blog not found with title: " + blogTitle);
    }

//        if (blog.getBlogCategory() != null) {
//            existingBlog.setBlogCategory(blog.getBlogCategory());
//        }

    if (blog.getBlogCategory() != null) {
        BlogCategory incomingCategory = blog.getBlogCategory();

        // Kiểm tra xem BlogCategory đã tồn tại chưa
        BlogCategory existingCategory = blogCategoryRepository
                .findByBlogCategoryName(incomingCategory.getBlogCategoryName())
                .orElseGet(() -> blogCategoryRepository.save(incomingCategory));

        existingBlog.setBlogCategory(existingCategory);
    }
    if (blog.getBlogTitle() != null) {
        existingBlog.setBlogTitle(blog.getBlogTitle());
    }
    if (blog.getBlogContent() != null) {
        existingBlog.setBlogContent(blog.getBlogContent());
    }
    if (blog.getBlogImages() != null) {
        existingBlog.setBlogImages(blog.getBlogImages());
    }
//        if (blog.getHashtags() != null) {
//            existingBlog.setHashtags(blog.getHashtags());
//        }
    if (blog.getHashtags() != null && !blog.getHashtags().isEmpty()) {
        List<BlogHashtag> savedHashtags = new ArrayList<>();

        for (BlogHashtag hashtag : blog.getHashtags()) {
            // Kiểm tra xem hashtag đã tồn tại chưa
            BlogHashtag existingHashtag = blogHashtagRepository
                    .findHashtagByName(hashtag.getBlogHashtagName()) // Đảm bảo tên phương thức trùng khớp
                    .orElseGet(() -> blogHashtagRepository.save(hashtag));

            savedHashtags.add(existingHashtag);
        }

        existingBlog.setHashtags(savedHashtags);
    }
//        if (blog.getUserId() != null) {
//            existingBlog.setUserId(blog.getUserId());
//        }
    if (blog.getPostedTime() != null) {
        existingBlog.setPostedTime(blog.getPostedTime());
    }
    if (blog.getDeletedTime() != null) {
        existingBlog.setDeletedTime(blog.getDeletedTime());
    }
    if (blog.getStatus() != 0) {
        existingBlog.setStatus(blog.getStatus());
    }

    return blogRepository.save(existingBlog);
}


//    public String deleteBlogById(String id) {
//        Blogs blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
//        blog.setStatus((byte) 0); // Xóa mềm
//        blogRepository.save(blog);
//        return "Blog deleted successfully";
//    }

    public String deleteBlogByTitle(String blogTitle) {
        Blogs existingBlog = blogRepository.findByTitle(blogTitle);
        if (existingBlog == null) {
            throw new RuntimeException("Blog not found with title: " + blogTitle);
        }
        existingBlog.setStatus((byte) 0); // Xóa mềm
        blogRepository.save(existingBlog);
        return "Blog deleted successfully";
    }

    public List<Blogs> getBlogByCategory(String categoryName) {
        return blogRepository.findByBlogCategory_BlogCategoryName(categoryName);
    }

    // Lấy blog theo hashtag
    public List<Blogs> getBlogsByHashtagName(String hashtagName) {
        return blogRepository.findByHashtagName(hashtagName);
    }
}

