package ru.sstu.albums.services;

import lombok.AllArgsConstructor;
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

import java.util.List;

@Service
@AllArgsConstructor
public class PhotoService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;

    public Photo showEntity(int id) {
        return photoRepository.findById(id);
    }

    public Photo show(int id, String principal) {
        Photo photo = photoRepository.findById(id);
        photo.setBytes(null);
        photo.setTags(photoTagRepository.findAllByPhotoId(id));
        photo.setUserRating(photoRatingRepository.findByRatingUserLoginAndPhotoId(principal, id));
        photo.setRating(photoRatingRepository.calculateAverageRatingByPhotoId(id));
        photo.setComments(photoCommentRepository.findAllByPhotoId(id));
        photo.setAlbum(albumRepository.findById(photo.getAlbumId()));
        return photo;
    }

    public Photo delete(Photo photo, String principal) {
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
        return deletedPhoto;
    }

    public PhotoTag createTag(PhotoTag photoTag, String principal) {
        if (photoTagRepository.findByTagAndPhotoId(photoTag) != null)
            return null;
        PhotoTag createdPhotoTag = photoTagRepository.save(photoTag);
        return createdPhotoTag;
    }

    public PhotoTag deleteTag(PhotoTag photoTag, String principal) {
        if (photoRepository.findById(photoTag.getPhotoId()) == null)
            return null;
        PhotoTag deletedPhotoTag = photoTagRepository.deleteById(photoTag.getId());
        return deletedPhotoTag;
    }

    public PhotoRating createRating(PhotoRating photoRating, String principal) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return null;
        photoRating.setRatingUserLogin(principal);
        PhotoRating createdPhotoRating = photoRatingRepository.save(photoRating);
        return createdPhotoRating;
    }

    public PhotoRating updateRating(PhotoRating photoRating, String principal) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return null;
        PhotoRating updatedPhotoRating = photoRatingRepository.updateRatingById(photoRating);
        return updatedPhotoRating;
    }

    public PhotoRating deleteRating(PhotoRating photoRating, String principal) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return null;
        PhotoRating deletedPhotoRating = photoRatingRepository.deleteById(photoRating.getId());
        return deletedPhotoRating;
    }

    public PhotoComment createComment(PhotoComment photoComment, String principal) {
        photoComment.setComment(photoComment.getComment());
        photoComment.setCommentingUserLogin(principal);
        photoComment.setPhotoId(photoComment.getPhotoId());
        PhotoComment createdPhotoComment = photoCommentRepository.save(photoComment);
        return createdPhotoComment;
    }

    public PhotoComment deleteComment(PhotoComment photoComment, String principal) {
        if (photoRepository.findById(photoComment.getPhotoId()) == null)
            return null;
        PhotoComment deletedPhotoComment = photoCommentRepository.deleteById(photoComment.getId());
        return deletedPhotoComment;
    }

    public List<Photo> find(String searchTerm, String word) {
        if (searchTerm != null && !searchTerm.isEmpty() && word != null && !word.isEmpty())
            switch (searchTerm) {
                case "creationTimeStamp" -> {
                    return photoRepository.findAllLikeCreationTimeStamp(word);
                }
                case "tags" -> {
                    return photoRepository.findAllLikeTags(word);
                }
                case "comments" -> {
                    return photoRepository.findAllLikeComments(word);
                }
            }
        return null;
    }

    public List<Photo> getAll() {
        return photoRepository.findAll();
    }

    public List<PhotoTag> getAllTags() {
        return photoTagRepository.findAll();
    }

    public List<PhotoComment> getAllComments() {
        return photoCommentRepository.findAll();
    }

}
