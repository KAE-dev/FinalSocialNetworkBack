package ru.rosbank.javaschool.service;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.rosbank.javaschool.dto.PostResponseDto;
import ru.rosbank.javaschool.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.entity.PostEntity;
import ru.rosbank.javaschool.entity.UserEntity;
import ru.rosbank.javaschool.mapper.PostMapper;
import ru.rosbank.javaschool.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    private PostRepository repository;
    private PostMapper mapper;
    private UserEntity user;
    private PostEntity post;
    private PostResponseDto dto;
    private PostService service;

    @BeforeEach
    void setUp() {
        repository = mock(PostRepository.class);
        mapper = mock(PostMapper.class);
        user = new UserEntity(1, "User", "user", "123456",
                "89999999999", List.of(new SimpleGrantedAuthority("ROLE_USER")),
                false, false, false, false);
        post = new PostEntity(1, user,  "content", "", false, 0);
        dto = new PostResponseDto(1, 1, "User", "content", "", 0);
        service = new PostService(repository, mapper);
    }


    @Test
    void getPosts() {
        List<PostEntity> list = new ArrayList<>();
        list.add(post);
        when(repository.findAll()).thenReturn(list);

        List<PostResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);
        when(mapper.entityToPostResponseDto(post)).thenReturn(dto);

        List<PostResponseDto> actual = service.getPosts(0, 1);
        assertIterableEquals(actual, listDto);

    }

    @Test
    void getFirstId() {
        List<PostEntity> list = new ArrayList<>();
        list.add(post);

        when(repository.findAll()).thenReturn(list);

        List<PostResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);
        when(mapper.entityToPostResponseDto(post)).thenReturn(dto);

        val actual = service.getFirstId();
        assertEquals(1, actual);
    }

    @Test
    void getNewPostsNumber() {
        List<PostEntity> list = new ArrayList<>();
        list.add(post);
        when(repository.findById(1)).thenReturn(Optional.of(post));
        when(repository.findAll()).thenReturn(list);

        val actual = service.getNewPostsNumber(1);
        assertEquals(0, actual);
    }

    @Test
    void save() {
        val postSaveRequestDto = new PostSaveRequestDto(1, user, "content", "");

        when(repository.save(post)).thenReturn(post);
        when(mapper.dtoToPostEntity(postSaveRequestDto)).thenReturn(post);
        when(mapper.entityToPostResponseDto(post)).thenReturn(dto);

        PostResponseDto actual = service.save(postSaveRequestDto);
        assertEquals(dto, actual);
    }

    @Test
    void searchByContent() {
        List<PostEntity> list = new ArrayList<>();
        list.add(post);
        String q = "content";

        when(repository.findAllByContentLikeIgnoreCase(q)).thenReturn(list);

        List<PostResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);
        when(mapper.entityToPostResponseDto(post)).thenReturn(dto);

        List<PostResponseDto> actual = service.searchByContent(q);
        assertIterableEquals(actual, listDto);
    }

    @Test
    void likeById() {
        when(repository.findById(1)).thenReturn(java.util.Optional.of(post));
        when(mapper.entityToPostResponseDto(post)).thenReturn(dto);
        PostResponseDto actual = service.likeById(1);
        assertEquals(actual, dto);
    }

    @Test
    void dislikeById() {
        when(repository.findById(1)).thenReturn(java.util.Optional.of(post));
        when(mapper.entityToPostResponseDto(post)).thenReturn(dto);
        PostResponseDto actual = service.dislikeById(1);
        assertEquals(actual, dto);
    }
}
