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
    public ResponseEntity<?> show(@PathVariable int id, @RequestParam String login) {
        return ResponseEntity.ok(photoService.show(id, login));
    }

    @DeleteMapping("/delete_photo")
    public ResponseEntity<?> delete(@RequestBody Photo photo) {
        return ResponseEntity.ok(photoService.delete(photo));
    }

    @PostMapping("/add_photo_tag")
    public ResponseEntity<?> createTag(@RequestBody PhotoTag photoTag) {
        return ResponseEntity.ok(photoService.createTag(photoTag));
    }

    @DeleteMapping("/delete_photo_tag")
    public ResponseEntity<?> deleteTag(@RequestBody PhotoTag photoTag) {
        return ResponseEntity.ok(photoService.deleteTag(photoTag));
    }

    @PostMapping("/add_photo_rating")
    public ResponseEntity<?> createRating(@RequestBody PhotoRating photoRating) {
        return ResponseEntity.ok(photoService.createRating(photoRating));
    }

    @PostMapping("/update_photo_rating")
    public ResponseEntity<?> updateRating(@RequestBody PhotoRating photoRating) {
        return ResponseEntity.ok(photoService.updateRating(photoRating));
    }

    @DeleteMapping("/delete_photo_rating")
    public ResponseEntity<?> deleteRating(@RequestBody PhotoRating photoRating) {
        return ResponseEntity.ok(photoService.deleteRating(photoRating));
    }

    @PostMapping("/add_photo_comment")
    public ResponseEntity<?> createComment(@RequestBody PhotoComment photoComment) {
        return ResponseEntity.ok(photoService.createComment(photoComment));
    }

    @DeleteMapping("/delete_photo_comment")
    public ResponseEntity<?> deleteComment(@RequestBody PhotoComment photoComment) {
        return ResponseEntity.ok(photoService.deleteComment(photoComment));
    }

    @GetMapping("/find_photos")
    public ResponseEntity<?> find(@RequestParam("search_term") String searchTerm, @RequestParam String keyword, @RequestParam String login) {
        return ResponseEntity.ok(photoService.find(searchTerm, keyword, login));
    }

    @GetMapping("/all_photos")
    public ResponseEntity<?> getAll(@RequestParam String login) {
        return ResponseEntity.ok(photoService.getAll(login));
    }

    @GetMapping("/all_photo_tags")
    public ResponseEntity<?> getAllTags(@RequestParam String login) {
        return ResponseEntity.ok(photoService.getAllTags(login));
    }

    @GetMapping("/all_photo_comments")
    public ResponseEntity<?> getAllComments(@RequestParam String login) {
        return ResponseEntity.ok(photoService.getAllComments(login));
    }

}
