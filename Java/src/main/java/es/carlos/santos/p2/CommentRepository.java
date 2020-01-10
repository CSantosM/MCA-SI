package es.carlos.santos.p2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByAuthorId(long id);

    Comment getById(long id);

}