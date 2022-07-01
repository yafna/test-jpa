package some.test.db.services.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import some.test.db.metamodels.Post;
import some.test.db.metamodels.PostComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public class PostCommentService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PostService postService;

    @Transactional
    public PostComment create() {
        PostComment p = new PostComment();
        p.setPost(postService.create());
        p.setReview("Review " + new Date().getTime());
        em.persist(p);
        return p;
    }

    @Transactional
    public PostComment create(Post post) {
        PostComment p = new PostComment();
        p.setPost(post);
        p.setReview("Review " + new Date().getTime());
        em.persist(p);
        return p;
    }

    public List<PostComment> list() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PostComment> q = cb.createQuery(PostComment.class);
        Root<PostComment> c = q.from(PostComment.class);
        return em.createQuery(q.select(c)).getResultList();
    }
}
