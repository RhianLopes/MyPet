package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.Comment;
import com.mypet.MyPet.repository.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private CommentRepository commentRepository = new CommentRepository();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment insert(@RequestBody Comment comment){
        return (Comment) commentRepository.insert(comment);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        commentRepository.delete(id);
    }

    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void inactivate(@PathVariable("id") Long id){
        commentRepository.inactivate(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Comment update(@RequestBody Comment comment){
        return (Comment) commentRepository.update(comment);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Comment> findAll(){
        return commentRepository.findAll();
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment find(@PathVariable("id") Long id){
        return (Comment) commentRepository.findById(id);
    }
}
