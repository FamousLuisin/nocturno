package com.nocturno.api.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nocturno.api.models.like.LikePostModel;
import com.nocturno.api.models.like.dto.LikePostDTO;
import com.nocturno.api.models.post.PostModel;
import com.nocturno.api.models.post.dto.PostRequestDTO;
import com.nocturno.api.models.post.dto.PostDTO;
import com.nocturno.api.models.user.UserModel;
import com.nocturno.api.repository.LikePostRepository;
import com.nocturno.api.repository.PostRepository;
import com.nocturno.api.repository.UserRepository;

@Service
public class PostService {
    
    private UserRepository userRepository;
    private PostRepository postRepository;
    private LikePostRepository likePostRepository;

    public PostService(UserRepository userR, PostRepository postR, LikePostRepository likePostR){
        this.postRepository = postR;
        this.userRepository = userR;
        this.likePostRepository = likePostR;
    }

    public PostDTO newPost(PostRequestDTO dto, String userId){
        
        UserModel user = userRepository.findById(UUID.fromString(userId)).orElse(null);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }

        PostModel post = new PostModel(dto.getContent(), user);

        post = postRepository.save(post);
        
        return new PostDTO(post.getId(), post.getContent(), post.getCreatedAt(), user.getUsername(), post.getNumberLikes());
    }

    public List<PostDTO> getAllPosts(){
        List<PostModel> posts = postRepository.findAll();

        List<PostDTO> dto = new ArrayList<>();

        posts.forEach(post -> {
            dto.add(new PostDTO(post.getId(), post.getContent(), post.getCreatedAt(), post.getCreator().getUsername(), post.getNumberLikes()));
        });

        return dto;
    }

    public List<PostDTO> getPostsByUser(String username){
        UserModel user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }

        List<PostModel> posts = postRepository.findByCreator(user);

        return posts
            .stream()
            .map(post -> new PostDTO(post.getId(), post.getContent(), post.getCreatedAt(), post.getCreator().getUsername(), post.getNumberLikes()))
            .toList();
    }

    public void deletePost(String id, String userId){
        UUID postId;

        try {
            postId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid UUID format");
        }

        PostModel post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post not found");
        }

        if (!post.getCreator().getId().equals(UUID.fromString(userId))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
        }

        post.setDeletedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    public PostDTO updatePost(String id, PostRequestDTO dto, String userId){
        UUID postId;

        try {
            postId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid UUID format");
        }

        PostModel post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post not found");
        }

        if (!post.getCreator().getId().equals(UUID.fromString(userId))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
        }

        post.setContent(dto.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        return new PostDTO(post.getId(), post.getContent(), post.getCreatedAt(), post.getCreator().getUsername(), post.getNumberLikes());
    }

    public void likePost(String post, String user){

        UUID postId;
        UUID userId;

        try {
            postId = UUID.fromString(post);
            userId = UUID.fromString(user);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid UUID format");
        }
        
        UserModel userRef = userRepository.getReferenceById(userId);
        PostModel postRef = postRepository.findById(postId).orElse(null);

        if (postRef == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post not found");
        }

        LikePostModel like = likePostRepository.findByPostAndUser(postRef, userRef);

        if (like != null) {
            likePostRepository.delete(like);
            postRepository.removeLikes(postId);
            return;
        }

        like = new LikePostModel();
        like.setPost(postRef);
        like.setUser(userRef);
        likePostRepository.save(like);
        postRepository.addLikes(postId);
    }

    public List<LikePostDTO> getLikePost(String id){
        UUID postId;

        try {
            postId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid UUID format");
        }

        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post not found");
        }

        List<LikePostDTO> lp = likePostRepository.findLikesByPost(postId);
        
        return lp;
    }
}
