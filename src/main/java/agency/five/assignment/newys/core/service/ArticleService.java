package agency.five.assignment.newys.core.service;

import agency.five.assignment.newys.core.exceptions.InvalidArticleIdException;
import agency.five.assignment.newys.core.model.Article;
import agency.five.assignment.newys.core.model.UserAccount;
import agency.five.assignment.newys.core.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article findById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new InvalidArticleIdException(articleId));
    }

    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public boolean isUsersArticle(UserAccount user, Article article) {
        return articleRepository.existsByArticleIdAndAuthor(article.getArticleId(), user);
    }

    @Transactional
    public Article save(Article article) {
        return articleRepository.save(article);
    }
}
