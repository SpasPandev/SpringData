package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    @Query("SELECT DISTINCT u FROM User AS u JOIN FETCH u.posts AS p ORDER BY SIZE(p) DESC , u.id")
    List<User> findAllByOrderByPostsCountDescThenByUserId();
}
