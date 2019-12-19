package ru.rosbank.javaschool.service;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {

    @Test
    void saveMultipartJpeg() {

        val multipart = mock(MultipartFile.class);
        when(multipart.getContentType()).thenReturn("image/jpeg");
        String uploadPath = "";

        val service = new FileService(uploadPath);
        val dto = service.save(multipart);
        assertTrue(dto.getName().endsWith(".jpg"));

    }


    @Test
    void saveMultipartPng() {

        val multipart = mock(MultipartFile.class);
        when(multipart.getContentType()).thenReturn("image/png");
        String uploadPath = "";

        val service = new FileService(uploadPath);
        val dto = service.save(multipart);
        assertTrue(dto.getName().endsWith(".png"));

    }

    @Test
    void saveMultipartGif() {

        val multipart = mock(MultipartFile.class);
        when(multipart.getContentType()).thenReturn("image/gif");
        String uploadPath = "";

        val service = new FileService(uploadPath);
        val dto = service.save(multipart);
        assertTrue(dto.getName().endsWith(".gif"));

    }

    @Test
    void saveMultipartWebm() {

        val multipart = mock(MultipartFile.class);
        when(multipart.getContentType()).thenReturn("video/webm");
        String uploadPath = "";

        val service = new FileService(uploadPath);
        val dto = service.save(multipart);
        assertTrue(dto.getName().endsWith(".webm"));

    }

    @Test
    void saveMultipartMp4() {

        val multipart = mock(MultipartFile.class);
        when(multipart.getContentType()).thenReturn("video/mp4");
        String uploadPath = "";

        val service = new FileService(uploadPath);
        val dto = service.save(multipart);
        assertTrue(dto.getName().endsWith(".mp4"));

    }


    @Test
    void saveMultipartMp3() {

        val multipart = mock(MultipartFile.class);
        when(multipart.getContentType()).thenReturn("audio/mp3");
        String uploadPath = "";

        val service = new FileService(uploadPath);
        val dto = service.save(multipart);
        assertTrue(dto.getName().endsWith(".mp3"));

    }

}
