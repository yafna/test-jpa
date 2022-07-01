package some.test.db.services.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import some.test.db.metamodels.Post;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class PostService {


    @PersistenceContext
    private EntityManager em;
    @Autowired
    private TransactionManager platformTransactionManager;

    @Transactional
    public Post  create() {
        Post p = new Post();
        p.setTitle("Title +" + new Date().getTime());
        em.persist(p);
        return p;
    }

    public List<Post> list() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Post> q = cb.createQuery(Post.class);
        Root<Post> c = q.from(Post.class);
        return em.createQuery(q.select(c)).getResultList();
    }

}
