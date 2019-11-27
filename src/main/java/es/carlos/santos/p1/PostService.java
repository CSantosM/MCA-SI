package es.carlos.santos.p1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class PostService {

    private Map<Long, Post> postList = new ConcurrentHashMap<Long, Post>();
    private AtomicLong lastId = new AtomicLong();

    PostService() {
        Post post = new Post("Master Cloud Apps", "Tecnologias de Servicios de Internet");
        post.addComment(new Comment("Carlos", "Comentario de prueba"));
        this.add(post);
    }

    public void add(Post post) {
        post.setId(this.lastId.get());
        this.postList.put(this.lastId.getAndIncrement(), post);
    }

    public Post getPost(Long id) {
        return this.postList.get(id);
    }

    public Map<Long, Post> getPosts() {
        return this.postList;
    }

    public Post deletePost(Long id) {
        return this.postList.remove(id);
    }

}