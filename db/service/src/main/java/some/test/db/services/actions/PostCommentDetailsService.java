package some.test.db.services.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import some.test.db.metamodels.PostComment;
import some.test.db.metamodels.PostCommentDetails;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PostCommentDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private PostCommentService postCommentService;


    @Transactional
    public PostCommentDetails create(PostComment comment) {
        PostComment pcMerged = em.merge(comment);
        PostCommentDetails p = new PostCommentDetails();
        p.setComment(pcMerged);
        p.setVotes(3);
        em.persist(p);
        return p;
    }

    public List<PostCommentDetails> jpqlToTest() {
        List<PostCommentDetails> commentDetailsList = em.createQuery("""
                                select pcd
                                from PostCommentDetails pcd
                                order by pcd.id
                                """,
                        PostCommentDetails.class)
                .getResultList();
        return commentDetailsList;
    }

    public List<PostCommentDetails> list() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PostCommentDetails> q = cb.createQuery(PostCommentDetails.class);
        Root<PostCommentDetails> c = q.from(PostCommentDetails.class);
        return em.createQuery(q.select(c)).getResultList();
    }
}
