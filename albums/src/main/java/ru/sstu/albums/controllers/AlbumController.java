package ru.sstu.albums.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.albums.services.AlbumService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/albums")
@AllArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/albums/{userLogin}")
    public ResponseEntity<?> showAll(@PathVariable String userLogin) {
        return ResponseEntity.ok(albumService.showAll(userLogin));
    }

    @PostMapping("/add_album")
    public ResponseEntity<?> create(@RequestParam String name, @RequestBody List<MultipartFile> files) throws IOException {
        return ResponseEntity.ok(albumService.create(name, files));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable int id) {
        return ResponseEntity.ok(albumService.show(id));
    }

    @PostMapping("/create_photos")
    public ResponseEntity<?> createPhotos(List<MultipartFile> files, int albumId, String principal) throws IOException {
        return ResponseEntity.ok(albumService.createPhotos(files, albumId/*, principal*/));
    }

    @DeleteMapping("/delete_album")
    public ResponseEntity<?> deleteAlbum(int id, String principal) {
        return ResponseEntity.ok(albumService.delete(id, principal));
    }

    @GetMapping("/find_albums/{keyword}")
    public ResponseEntity<?> findAlbums(@PathVariable String keyword) {
        return ResponseEntity.ok(albumService.find(keyword));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(albumService.getAll());
    }

}
