package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.Post;
import com.mypet.MyPet.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostRepository postRepository = new PostRepository();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Object insert(@RequestBody Post post){
        return postRepository.insert(post);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        postRepository.delete(id);
    }

    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void inactivate(@PathVariable("id") Long id){
        postRepository.inactivate(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Object update(@RequestBody Post post){
        return postRepository.update(post);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Object> findAll(){
        return postRepository.findAll();
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object find(@PathVariable("id") Long id){
        return postRepository.findById(id);
    }
}
