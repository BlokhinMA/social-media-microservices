package ru.sstu.albums.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.sstu.albums.models.Photo;
import ru.sstu.albums.models.PhotoComment;
import ru.sstu.albums.models.PhotoRating;
import ru.sstu.albums.models.PhotoTag;
import ru.sstu.albums.services.PhotoService;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/albums")
@AllArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/photo_entity/{id}")
    public ResponseEntity<?> photoEntity(@PathVariable int id) {
        Photo photo = photoService.showEntity(id);
        return ResponseEntity.ok()
                .header("fileName", photo.getOriginalFileName())
                .contentType(MediaType.valueOf(photo.getContentType()))
                .contentLength(photo.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(photo.getBytes())));
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<?> show(@PathVariable int id, String principal) {
        return ResponseEntity.ok(photoService.show(id, principal));
    }

    @DeleteMapping("/delete_photo")
    public ResponseEntity<?> delete(Photo photo, String principal) {
        return ResponseEntity.ok(photoService.delete(photo, principal));
    }

    @PostMapping("/add_photo_tag")
    public ResponseEntity<?> createTag(PhotoTag photoTag, String principal) {
        return ResponseEntity.ok(photoService.createTag(photoTag, principal));
    }

    @DeleteMapping("/delete_photo_tag")
    public ResponseEntity<?> deleteTag(PhotoTag photoTag, String principal) {
        return ResponseEntity.ok(photoService.deleteTag(photoTag, principal));
    }

    @PostMapping("/add_photo_rating")
    public ResponseEntity<?> createRating(PhotoRating photoRating, String principal) {
        return ResponseEntity.ok(photoService.createRating(photoRating, principal));
    }

    @PostMapping("/update_photo_rating")
    public ResponseEntity<?> updateRating(PhotoRating photoRating, String principal) {
        return ResponseEntity.ok(photoService.updateRating(photoRating, principal));
    }

    @DeleteMapping("/delete_photo_rating")
    public ResponseEntity<?> deleteRating(PhotoRating photoRating, String principal) {
        return ResponseEntity.ok(photoService.deleteRating(photoRating, principal));
    }

    @PostMapping("/add_photo_comment")
    public ResponseEntity<?> createComment(PhotoComment photoComment, String principal) {
        return ResponseEntity.ok(photoService.createComment(photoComment, principal));
    }

    @DeleteMapping("/delete_photo_comment")
    public ResponseEntity<?> deleteComment(PhotoComment photoComment, String principal) {
        return ResponseEntity.ok(photoService.deleteComment(photoComment, principal));
    }

    @GetMapping("/find_photos")
    public ResponseEntity<?> find(String searchTerm, String keyword) {
        return ResponseEntity.ok(photoService.find(searchTerm, keyword));
    }

    @GetMapping("/all_photos")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(photoService.getAll());
    }

    @GetMapping("/all_photo_tags")
    public ResponseEntity<?> getAllTags() {
        return ResponseEntity.ok(photoService.getAllTags());
    }

    @GetMapping("/all_photo_comments")
    public ResponseEntity<?> getAllComments() {
        return ResponseEntity.ok(photoService.getAllComments());
    }

}
