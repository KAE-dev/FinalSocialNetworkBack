package ru.rosbank.javaschool.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.dto.PostResponseDto;
import ru.rosbank.javaschool.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.entity.UserEntity;
import ru.rosbank.javaschool.service.PermissionService;
import ru.rosbank.javaschool.service.PostService;
import ru.rosbank.javaschool.service.UserService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class RestPostController {
  private final PostService service;
  private final UserService userService;
  private final PermissionService permissionService;

  @GetMapping
  public int getIdFirstPost() {
    return service.getFirstId();
  }


  @GetMapping(params = {"firstPostId"})
  public int getNewPostsNumber(@RequestParam int firstPostId) {
    return service.getNewPostsNumber(firstPostId);
  }

  @GetMapping(params = {"lastPost", "i"})
  public List<PostResponseDto> getSomePosts(@RequestParam int lastPost, @RequestParam int i) {
    return service.getPosts(lastPost, i);
  }


  @GetMapping(params = "q")
  public List<PostResponseDto> searchByContent(@RequestParam String q) {
    return service.searchByContent(q);
  }


  @PostMapping
  public PostResponseDto save(@RequestBody PostSaveRequestDto dto) {
    UserEntity user = getAuthorizedUser();
    if (dto.getId() != 0) {
      permissionService.isOperationAvailable(dto.getId(), user);
    }
    dto.setAuthor(user);
    return service.save(dto);
  }


  @DeleteMapping("/{id}")
  public void removeById(@PathVariable int id) {
    UserEntity user = getAuthorizedUser();
    permissionService.isOperationAvailable(id, user);
    service.removeById(id);
  }

  private UserEntity getAuthorizedUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails user = userService.loadUserByUsername(auth.getName());
    return (UserEntity) user;
  }

  @PostMapping("/{id}/likes")
  public PostResponseDto likeById(@PathVariable int id) {
    return service.likeById(id);
  }

  @DeleteMapping("/{id}/likes")
  public PostResponseDto dislikeById(@PathVariable int id) {
    return service.dislikeById(id);
  }
}
