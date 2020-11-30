package agency.five.assignment.newys.core.repository;

import agency.five.assignment.newys.core.model.Article;
import agency.five.assignment.newys.core.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    boolean existsByArticleIdAndAuthor(Long articleId, UserAccount author);
}
