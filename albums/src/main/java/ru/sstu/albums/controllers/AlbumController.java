package ru.sstu.albums.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.albums.models.Album;
import ru.sstu.albums.services.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/my_albums")
    public List<Album> showAll(Principal principal) {
        return albumService.showAll(principal);
    }

    @GetMapping("/albums/{userLogin}")
    public List<Album> showAll(@PathVariable String userLogin) {
        return albumService.showAll(userLogin);
    }

    @PostMapping("/add_album")
    public ResponseEntity<?> create(@RequestParam String name, @RequestBody List<MultipartFile> files) throws IOException {
        return ResponseEntity.ok(albumService.create(name, files));
    }

    @GetMapping("/album/{id}")
    public Album show(@PathVariable int id) {
        return albumService.show(id);
    }

    @PostMapping("/create_photos")
    public void createPhotos(List<MultipartFile> files, int albumId, Principal principal) throws IOException {
        albumService.createPhotos(files, albumId/*, principal*/);
    }

    @DeleteMapping("/delete_album")
    public void deleteAlbum(int id, Principal principal) {
        albumService.delete(id, principal);
    }

    @GetMapping("/find_albums")
    public List<Album> findAlbums(String keyword) {
        return albumService.find(keyword);
    }

}
