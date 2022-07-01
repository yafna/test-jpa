package some.test.db.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import some.test.db.metamodels.Post;
import some.test.db.metamodels.PostComment;
import some.test.db.metamodels.PostCommentDetails;
import some.test.db.services.actions.PostCommentDetailsService;
import some.test.db.services.actions.PostCommentService;
import some.test.db.services.actions.PostService;
import some.test.db.services.config.DbTestConfig;

import java.util.List;

@EnableTransactionManagement(proxyTargetClass = true)
@ContextConfiguration(classes = DbTestConfig.class)
public class IfItWorks extends BaseTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private PostService postService;
    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private PostCommentDetailsService postCommentDetailsService;
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Test
    public void testList() {
        Post post = postService.create();
        Assumptions.assumeTrue(null != post.getId());
        PostComment postComment = postCommentService.create(post);
        Assumptions.assumeTrue(null != postComment);
        PostCommentDetails postCommentDetails = postCommentDetailsService.create(postComment);
        Assumptions.assumeTrue(null != postCommentDetails.getId());
        List<PostCommentDetails> list = postCommentDetailsService.list();
        Assumptions.assumeTrue(1 == list.size());
        Assumptions.assumeTrue(postCommentDetails.getId() == list.get(0).getId());
    }
}
