package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.dao.FollowerDAO;
import com.mypet.MyPet.dao.PostDAO;
import com.mypet.MyPet.domain.Follower;
import com.mypet.MyPet.domain.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/follower")
public class FollowerController {

    private FollowerDAO followerDAO = new FollowerDAO();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Follower insert(@RequestBody Follower follower){
        return (Follower) followerDAO.insert(follower);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        followerDAO.delete(id);
    }

    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void inactivate(@PathVariable("id") Long id){
        followerDAO.inactivate(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Follower update(@RequestBody Follower follower){
        return (Follower) followerDAO.update(follower);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Follower> findAll(){
        return followerDAO.findAll();
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Follower find(@PathVariable("id") Long id){
        return (Follower) followerDAO.findById(id);
    }

    @GetMapping("/find-all-by-follower/{petId}")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Follower> findAllByFollower(@PathVariable("petId") Long petId){
        return followerDAO.findAllByFollower(petId);
    }

    @GetMapping("/find-all-by-followed/{petId}")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Follower> findAllByFollowed(@PathVariable("petId") Long petId){
        return followerDAO.findAllByFollowed(petId);
    }
}
