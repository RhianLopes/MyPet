package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.Comment;
import com.mypet.MyPet.dao.CommentDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private CommentDAO commentDAO = new CommentDAO();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment insert(@RequestBody Comment comment){
        return (Comment) commentDAO.insert(comment);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        commentDAO.delete(id);
    }

    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void inactivate(@PathVariable("id") Long id){
        commentDAO.inactivate(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Comment update(@RequestBody Comment comment){
        return (Comment) commentDAO.update(comment);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Comment> findAll(){
        return commentDAO.findAll();
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment find(@PathVariable("id") Long id){
        return (Comment) commentDAO.findById(id);
    }
}
