package agency.five.assignment.newys.core.repository;

import agency.five.assignment.newys.core.model.Article;
import agency.five.assignment.newys.core.model.Comment;
import agency.five.assignment.newys.core.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

    Page<Comment> findByArticle(Article article, Pageable pageable);

    boolean existsByCommentIdAndAuthor(Long commentId, UserAccount author);
}
