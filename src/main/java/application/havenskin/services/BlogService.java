package application.havenskin.services;

import application.havenskin.models.Blogs;
import application.havenskin.dataAccess.BlogDTO;
import application.havenskin.mapper.Mapper;
import application.havenskin.repositories.BlogRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepostiory blogRepostiory;
    @Autowired
    private Mapper mapper;
    public List<Blogs>  getAllBlogs(){
        return blogRepostiory.findAll();
    }
    public Blogs getBlogById(String id){
        return blogRepostiory.findById(id).get();
    }
    public Blogs addBlog(Blogs blog){
        return blogRepostiory.save(blog);
    }
    public Blogs updateBlog(String id, BlogDTO blog){
        Blogs blogToUpdate = blogRepostiory.findById(id).orElseThrow(()-> new RuntimeException("Blog not found"));
        mapper.updateBlogs(blogToUpdate, blog);
        return blogRepostiory.save(blogToUpdate);
    }
    public void deleteBlog(String id){
        blogRepostiory.deleteById(id);
    }
    public List<Blogs> addListOfBlogs(List<Blogs> blogs){
        return blogRepostiory.saveAll(blogs);
    }
}

