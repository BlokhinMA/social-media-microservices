package ru.sstu.albums.services;

import ru.sstu.albums.models.Photo;
import ru.sstu.albums.models.PhotoComment;
import ru.sstu.albums.models.PhotoRating;
import ru.sstu.albums.models.PhotoTag;
import ru.sstu.albums.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sstu.albums.repositories.PhotoRepository;
import ru.sstu.albums.repositories.PhotoCommentRepository;
import ru.sstu.albums.repositories.PhotoRatingRepository;
import ru.sstu.albums.repositories.PhotoTagRepository;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;

    public Photo showEntity(int id) {
        return photoRepository.findById(id);
    }

    public Photo show(int id, Principal principal) {
        Photo photo = this.photoRepository.findById(id);
        photo.setTags(photoTagRepository.findAllByPhotoId(id));
        photo.setUserRating(photoRatingRepository.findByRatingUserLoginAndPhotoId(principal.getName(), id));
        photo.setRating(photoRatingRepository.calculateAverageRatingByPhotoId(id));
        photo.setComments(photoCommentRepository.findAllByPhotoId(id));
        photo.setAlbum(albumRepository.findById(photo.getAlbumId()));
        return photo;
    }

    public void delete(Photo photo, Principal principal) {
        if (albumRepository.findById(photo.getAlbumId()) == null)
            return;
        int photoId = photo.getId();
        List<PhotoTag> tags = photoTagRepository.findAllByPhotoId(photoId);
        List<PhotoRating> ratings = photoRatingRepository.findAllByPhotoId(photoId);
        List<PhotoComment> comments = photoCommentRepository.findAllByPhotoId(photoId);
        Photo deletedPhoto = this.photoRepository.deleteById(photo.getId());
        deletedPhoto.setTags(tags);
        deletedPhoto.setRatings(ratings);
        deletedPhoto.setComments(comments);
    }

    public void createTag(PhotoTag photoTag, Principal principal) {
        if (this.photoTagRepository.findByTagAndPhotoId(photoTag) != null)
            return;
        PhotoTag createdPhotoTag = this.photoTagRepository.save(photoTag);
    }

    public void deleteTag(PhotoTag photoTag, Principal principal) {
        if (photoRepository.findById(photoTag.getPhotoId()) == null)
            return;
        PhotoTag deletedPhotoTag = this.photoTagRepository.deleteById(photoTag.getId());
    }

    public void createRating(PhotoRating photoRating, Principal principal) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return;
        photoRating.setRatingUserLogin(principal.getName());
        PhotoRating createdPhotoRating = this.photoRatingRepository.save(photoRating);
    }

    public void updateRating(PhotoRating photoRating, Principal principal) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return;
        PhotoRating updatedPhotoRating = this.photoRatingRepository.updateRatingById(photoRating);
    }

    public void deleteRating(PhotoRating photoRating, Principal principal) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return;
        PhotoRating deletedPhotoRating = this.photoRatingRepository.deleteById(photoRating.getId());
    }

    public void createComment(PhotoComment photoComment, Principal principal) {
        photoComment.setComment(photoComment.getComment());
        photoComment.setCommentingUserLogin(principal.getName());
        photoComment.setPhotoId(photoComment.getPhotoId());
        PhotoComment createdPhotoComment = this.photoCommentRepository.save(photoComment);
    }

    public void deleteComment(PhotoComment photoComment, Principal principal) {
        if (photoRepository.findById(photoComment.getPhotoId()) == null)
            return;
        PhotoComment deletedPhotoComment = this.photoCommentRepository.deleteById(photoComment.getId());
    }

    public void find(String searchTerm, String word) {
        if (searchTerm != null && !searchTerm.isEmpty() && word != null && !word.isEmpty())
            switch (searchTerm) {
                case "creationTimeStamp" -> photoRepository.findAllLikeCreationTimeStamp(word);
                case "tags" -> photoRepository.findAllLikeTags(word);
                case "comments" -> photoRepository.findAllLikeComments(word);
            }
    }

}
