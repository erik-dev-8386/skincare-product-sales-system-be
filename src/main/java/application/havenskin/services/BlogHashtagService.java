package application.havenskin.services;

import application.havenskin.models.BlogHashtag;
import application.havenskin.repositories.BlogHashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogHashtagService {
    @Autowired
    private BlogHashtagRepository blogHashtagRepository;

    public List<BlogHashtag> getAll() {
        return blogHashtagRepository.findAll();
    }

    public BlogHashtag getById(String id) {
        return blogHashtagRepository.findById(id).get();
    }

    public Optional<BlogHashtag> getByHashtagName(String hashtagName) {
        return blogHashtagRepository.findHashtagByName(hashtagName);
    }

    public BlogHashtag addBlogHashtag(BlogHashtag blogHashtag) {
        return blogHashtagRepository.save(blogHashtag);
    }

    //    public BlogHashtag updateBlogHashtag(String id, BlogHashtag blogHashtag) {
//        Optional<BlogHashtag> optionalBlogHashtag = blogHashtagRepository.findById(id);
//        BlogHashtag existingBlogHashtag = optionalBlogHashtag.orElseThrow(() -> new RuntimeException("No answer found with id: " + id));
//
//        if(blogHashtag.getBlogHashtagName() != null) {
//            existingBlogHashtag.setBlogHashtagName(blogHashtag.getBlogHashtagName());
//        }
//
//        if(blogHashtag.getDescription() != null) {
//            existingBlogHashtag.setDescription(blogHashtag.getDescription());
//        }
//
//        if(blogHashtag.getStatus() != 0){
//            existingBlogHashtag.setStatus(blogHashtag.getStatus());
//        }
//
//        return blogHashtagRepository.save(existingBlogHashtag);
//    }
    public BlogHashtag updateBlogHashtagByHashtagName(String blogHashtagName, BlogHashtag blogHashtag) {
        Optional<BlogHashtag> optionalBlogHashtag = blogHashtagRepository.findHashtagByName(blogHashtagName);
        BlogHashtag existingBlogHashtag = optionalBlogHashtag.orElseThrow(() -> new RuntimeException("No hashtag found with name: " + blogHashtagName));

        if (blogHashtag.getBlogHashtagName() != null) {
            existingBlogHashtag.setBlogHashtagName(blogHashtag.getBlogHashtagName());
        }

        if (blogHashtag.getDescription() != null) {
            existingBlogHashtag.setDescription(blogHashtag.getDescription());
        }

        if (blogHashtag.getStatus() != 0) {
            existingBlogHashtag.setStatus(blogHashtag.getStatus());
        }

        return blogHashtagRepository.save(existingBlogHashtag);
    }

    //    public String deleteBlogHashtag(String id) {
//        Optional<BlogHashtag> optionalBlogHashtag = blogHashtagRepository.findById(id);
//        BlogHashtag existingBlogHashtag = optionalBlogHashtag.orElseThrow(() -> new RuntimeException("No answer found with id: " + id));
//        existingBlogHashtag.setStatus((byte) 0);
//        blogHashtagRepository.save(existingBlogHashtag);
//        return "Blog hashtag has been deleted successfully";
//    }
    public String deleteBlogHashtagByHashtagName(String blogHashtagName) {
        Optional<BlogHashtag> optionalBlogHashtag = blogHashtagRepository.findHashtagByName(blogHashtagName);
        BlogHashtag existingBlogHashtag = optionalBlogHashtag.orElseThrow(() -> new RuntimeException("No hashtag found with name: " + blogHashtagName));
        existingBlogHashtag.setStatus((byte) 0);
        blogHashtagRepository.save(existingBlogHashtag);
        return "Blog hashtag has been deleted successfully";
    }


}
