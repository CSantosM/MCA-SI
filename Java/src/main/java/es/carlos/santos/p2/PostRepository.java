package es.carlos.santos.p2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

	Post getById(long id);

	@Query("SELECT DISTINCT p FROM Post p JOIN p.comments c WHERE c.author.id=?1")
	List<Post> findByCommentsAuthorId(long id);

}