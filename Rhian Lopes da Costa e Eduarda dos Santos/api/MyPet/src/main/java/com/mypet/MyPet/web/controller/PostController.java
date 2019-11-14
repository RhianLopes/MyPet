package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.Post;
import com.mypet.MyPet.dao.PostDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostDAO postDAO = new PostDAO();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Post insert(@RequestBody Post post){
        return (Post) postDAO.insert(post);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        postDAO.delete(id);
    }

    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void inactivate(@PathVariable("id") Long id){
        postDAO.inactivate(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Post update(@RequestBody Post post){
        return (Post) postDAO.update(post);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Post> findAll(){
        return postDAO.findAll();
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post find(@PathVariable("id") Long id){
        return (Post) postDAO.findById(id);
    }

    @GetMapping("/find-by-pet/{petId}")
    public ArrayList<Post> findAllByPet(@PathVariable("petId") Long petId) {
        return postDAO.findAllByPet(petId);
    }
}
