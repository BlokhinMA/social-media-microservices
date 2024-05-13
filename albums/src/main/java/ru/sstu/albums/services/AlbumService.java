package ru.sstu.albums.services;

import lombok.AllArgsConstructor;
import ru.sstu.albums.models.Album;
import ru.sstu.albums.models.Photo;
import ru.sstu.albums.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;

    public Album create(String name, List<MultipartFile> files/*, Principal principal*/) throws IOException {
        Album album = new Album();
        album.setName(name);
        album.setUserLogin(/*principal.getName()*/"user");
        Album createdAlbum = albumRepository.save(album);
        createPhotos(files, createdAlbum.getId()/*, principal*/);
        return createdAlbum;
    }

    private Photo toPhotoEntity(MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setName(file.getName());
        photo.setOriginalFileName(file.getOriginalFilename());
        photo.setSize(file.getSize());
        photo.setContentType(file.getContentType());
        photo.setBytes(file.getBytes());
        return photo;
    }

    public List<Album> showAll(String userLogin) {
        return albumRepository.findAllByUserLogin(userLogin);
    }

    public Album show(int id) {
        Album album = albumRepository.findById(id);
        album.setPhotos(photoRepository.findAllByAlbumId(id));
        return album;
    }

    public Album delete(int id, String principal) {
        List<Photo> photos = photoRepository.findAllByAlbumId(id);
        for (Photo photo : photos) {
            int photoId = photo.getId();
            photo.setTags(photoTagRepository.findAllByPhotoId(photoId));
            photo.setRatings(photoRatingRepository.findAllByPhotoId(photoId));
            photo.setComments(photoCommentRepository.findAllByPhotoId(photoId));
        }
        Album deletedAlbum = albumRepository.deleteById(id);
        deletedAlbum.setPhotos(photos);
        return deletedAlbum;
    }

    public List<Photo> createPhotos(List<MultipartFile> files, int albumId/*, Principal principal*/) throws IOException {
        if (Objects.requireNonNull(files.getFirst().getOriginalFilename()).isEmpty())
            return null;
        List<Photo> photos = new ArrayList<>();
        List<Photo> createdPhotos = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            photos.add(toPhotoEntity(files.get(i)));
            photos.get(i).setAlbumId(albumId);
            Photo createdPhoto = photoRepository.save(photos.get(i));
            createdPhoto.setAlbum(albumRepository.findById(createdPhoto.getAlbumId()));
            createdPhotos.add(createdPhoto);
        }
        return createdPhotos;
    }

    public List<Album> find(String word) {
        if (word != null && !word.isEmpty())
            return albumRepository.findAllLikeName(word);
        return null;
    }

    public List<Album> getAll() {
        return albumRepository.findAll();
    }

}
