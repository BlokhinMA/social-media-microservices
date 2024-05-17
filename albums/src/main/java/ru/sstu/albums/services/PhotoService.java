package ru.sstu.albums.services;

import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.sstu.albums.models.Photo;
import ru.sstu.albums.models.PhotoComment;
import ru.sstu.albums.models.PhotoRating;
import ru.sstu.albums.models.PhotoTag;
import ru.sstu.albums.repositories.*;
import org.springframework.stereotype.Service;
import ru.sstu.albums.repositories.PhotoRepository;
import ru.sstu.albums.repositories.PhotoCommentRepository;
import ru.sstu.albums.repositories.PhotoRatingRepository;
import ru.sstu.albums.repositories.PhotoTagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PhotoService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8765/logging?for_audit=";

    public Photo showEntity(int id) {
        return photoRepository.findEntityById(id);
    }

    public Photo show(int id, String login) {
        Photo photo = photoRepository.findById(id);
        photo.setTags(photoTagRepository.findAllByPhotoId(id));
        photo.setUserRating(photoRatingRepository.findByRatingUserLoginAndPhotoId(login, id));
        photo.setRating(photoRatingRepository.calculateAverageRatingByPhotoId(id));
        photo.setComments(photoCommentRepository.findAllByPhotoId(id));
        photo.setAlbum(albumRepository.findById(photo.getAlbumId()));
        restTemplate.postForObject(url + "false", "Пользователь " + login + " обратился к фото: " + photo, String.class);
        return photo;
    }

    public Photo delete(Photo photo) {
        if (albumRepository.findById(photo.getAlbumId()) == null)
            return null;
        int photoId = photo.getId();
        List<PhotoTag> tags = photoTagRepository.findAllByPhotoId(photoId);
        List<PhotoRating> ratings = photoRatingRepository.findAllByPhotoId(photoId);
        List<PhotoComment> comments = photoCommentRepository.findAllByPhotoId(photoId);
        Photo deletedPhoto = photoRepository.deleteById(photo.getId());
        deletedPhoto.setTags(tags);
        deletedPhoto.setRatings(ratings);
        deletedPhoto.setComments(comments);
        deletedPhoto.setAlbum(albumRepository.findById(deletedPhoto.getAlbumId()));
        restTemplate.postForObject(url + "true", "Пользователь " + deletedPhoto.getAlbum().getUserLogin() + " удалил фото: " + deletedPhoto, String.class);
        return deletedPhoto;
    }

    public PhotoTag createTag(PhotoTag photoTag) {
        if (photoTagRepository.findByTagAndPhotoId(photoTag) != null)
            return null;
        PhotoTag createdPhotoTag = photoTagRepository.save(photoTag);
        restTemplate.postForObject(url + "true", "Пользователь " + albumRepository.findById(photoRepository.findById(createdPhotoTag.getPhotoId()).getAlbumId()).getUserLogin() + " добавил тег: " + createdPhotoTag, String.class);
        return createdPhotoTag;
    }

    public PhotoTag deleteTag(PhotoTag photoTag) {
        if (photoRepository.findById(photoTag.getPhotoId()) == null)
            return null;
        PhotoTag deletedPhotoTag = photoTagRepository.deleteById(photoTag.getId());
        restTemplate.postForObject(url + "true", "Пользователь " + albumRepository.findById(photoRepository.findById(deletedPhotoTag.getPhotoId()).getAlbumId()).getUserLogin() + " удалил тег: " + deletedPhotoTag, String.class);
        return deletedPhotoTag;
    }

    public PhotoRating createRating(PhotoRating photoRating) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return null;
        PhotoRating createdPhotoRating = photoRatingRepository.save(photoRating);
        restTemplate.postForObject(url + "true", "Пользователь " + createdPhotoRating.getRatingUserLogin() + " добавил оценку: " + createdPhotoRating, String.class);
        return createdPhotoRating;
    }

    public PhotoRating updateRating(PhotoRating photoRating) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return null;
        PhotoRating updatedPhotoRating = photoRatingRepository.updateRatingById(photoRating);
        restTemplate.postForObject(url + "true", "Пользователь " + updatedPhotoRating.getRatingUserLogin() + " обновил оценку: " + updatedPhotoRating, String.class);
        return updatedPhotoRating;
    }

    public PhotoRating deleteRating(PhotoRating photoRating) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return null;
        PhotoRating deletedPhotoRating = photoRatingRepository.deleteById(photoRating.getId());
        restTemplate.postForObject(url + "true", "Пользователь " + deletedPhotoRating.getRatingUserLogin() + " удалил оценку: " + deletedPhotoRating, String.class);
        return deletedPhotoRating;
    }

    public PhotoComment createComment(PhotoComment photoComment) {
        if (photoRepository.findById(photoComment.getPhotoId()) == null)
            return null;
        PhotoComment createdPhotoComment = photoCommentRepository.save(photoComment);
        restTemplate.postForObject(url + "true", "Пользователь " + createdPhotoComment.getCommentingUserLogin() + " добавил комментарий: " + createdPhotoComment, String.class);
        return createdPhotoComment;
    }

    public PhotoComment deleteComment(PhotoComment photoComment) {
        if (photoRepository.findById(photoComment.getPhotoId()) == null)
            return null;
        PhotoComment deletedPhotoComment = photoCommentRepository.deleteById(photoComment.getId());
        restTemplate.postForObject(url + "true", "Пользователь " + deletedPhotoComment.getCommentingUserLogin() + " удалил комментарий: " + deletedPhotoComment, String.class);
        return deletedPhotoComment;
    }

    public List<Photo> find(String searchTerm, String word, String login) {
        if (searchTerm != null && !searchTerm.isEmpty() && word != null && !word.isEmpty())
            switch (searchTerm) {
                case "creationTimeStamp" -> {
                    List<Photo> photos = photoRepository.findAllLikeCreationTimeStamp(word);
                    forLogs(login, photos);
                    return photos;
                }
                case "tags" -> {
                    List<Photo> photos = photoRepository.findAllLikeTag(word);
                    forLogs(login, photos);
                    return photos;
                }
                case "comments" -> {
                    List<Photo> photos = photoRepository.findAllLikeComment(word);
                    forLogs(login, photos);
                    return photos;
                }
            }
        return null;
    }

    private void forLogs(String login, List<Photo> photos) {
        List<Photo> photos1 = new ArrayList<>(photos);
        restTemplate.postForObject(url + "false", "Пользователь " + login + " выполнил поиск фото: " + photos1, String.class);
    }

    public List<Photo> getAll(String login) {
        List<Photo> photos = photoRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех фото: " + photos, String.class);
        return photos;
    }

    public List<PhotoTag> getAllTags(String login) {
        List<PhotoTag> photoTags = photoTagRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех постов сообществ: " + photoTags, String.class);
        return photoTags;
    }

    public List<PhotoComment> getAllComments(String login) {
        List<PhotoComment> photoComments = photoCommentRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех постов сообществ: " + photoComments, String.class);
        return photoComments;
    }

}
