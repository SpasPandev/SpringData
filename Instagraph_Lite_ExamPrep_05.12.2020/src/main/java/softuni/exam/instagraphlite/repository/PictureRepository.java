package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    boolean existsByPath(String path);

    Picture findPictureByPath(String path);
}
