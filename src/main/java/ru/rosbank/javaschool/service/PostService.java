package ru.rosbank.javaschool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rosbank.javaschool.dto.PostResponseDto;
import ru.rosbank.javaschool.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.entity.PostEntity;
import ru.rosbank.javaschool.exception.NotFoundException;
import ru.rosbank.javaschool.mapper.PostMapper;
import ru.rosbank.javaschool.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final PostMapper mapper;

    public int getFirstId() {
        List<PostResponseDto> collect = repository.findAll().stream()
                .filter(o -> !o.isRemoved())
                .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
                .limit(1)
                .map(mapper::entityToPostResponseDto)
                .collect(Collectors.toList());
        return collect.get(0).getId();
    }

    public List<PostResponseDto> getPosts(int lastPost, int i) {
        return repository.findAll().stream()
                .filter(o -> !o.isRemoved())
                .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
                .skip(lastPost)
                .limit(i)
                .map(mapper::entityToPostResponseDto)
                .collect(Collectors.toList());
    }

    public int getNewPostsNumber(int firstPostId) {
        Optional<PostEntity> firstPost = repository.findById(firstPostId);
        List<Optional<PostEntity>> collect = repository.findAll().stream()
                .filter(o -> !o.isRemoved())
                .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
                .map(Optional::of)
                .collect(Collectors.toList());
        return collect.indexOf(firstPost);
    }


    public PostResponseDto save(PostSaveRequestDto dto) {
        return mapper.entityToPostResponseDto(repository.save(mapper.dtoToPostEntity(dto)));
    }


    public PostResponseDto getPostById(int id) {
        return repository.findById(id)
                .map(mapper::entityToPostResponseDto)
                .orElseThrow(NotFoundException::new);
    }

    public void removeById(int id) {
        repository.setRemovedById(id);
    }

    public List<PostResponseDto> searchByContent(String q) {
        return repository.findAllByContentLikeIgnoreCase(q).stream()
                .filter(o -> !o.isRemoved())
                .map(mapper::entityToPostResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDto likeById(int id) {
        repository.increaseLikesById(id, 1);
        final PostEntity entity = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapper.entityToPostResponseDto(entity);
    }

    public PostResponseDto dislikeById(int id) {
        repository.increaseLikesById(id, -1);
        final PostEntity entity = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapper.entityToPostResponseDto(entity);
    }
}
