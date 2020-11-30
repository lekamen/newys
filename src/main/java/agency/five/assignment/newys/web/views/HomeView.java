package agency.five.assignment.newys.web.views;

import agency.five.assignment.newys.core.model.Article;
import agency.five.assignment.newys.core.service.ArticleService;
import agency.five.assignment.newys.web.utils.ArticleUtil;
import agency.five.assignment.newys.web.views.components.Pager;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Newys")
public class HomeView extends VerticalLayout {

    private ArticleService articleService;
    private VerticalLayout articlesLayout = new VerticalLayout();
    private Pager pager;
    private Integer pageArticleSize;

    public HomeView(ArticleService articleService,
                    @Value("${page.article.size}") Integer pageArticleSize) {
        this.articleService = articleService;
        this.pageArticleSize = pageArticleSize;

        pager = new Pager(pageArticleSize);
        pager.onPageChange(this::fetchPage);

        add(articlesLayout, pager);

        fetchPage(1);
    }

    private void fetchPage(int page) {
        Page<Article> articles = articleService.findAll(PageRequest.of(page - 1, pageArticleSize, Sort.Direction.DESC, "lastModified"));
        setLayout(articles);
        pager.setCurrentPage(page);
        pager.setTotalResults(articles.getTotalElements());
    }

    private void setLayout(Page<Article> articles) {
        articlesLayout.removeAll();
        articles.getContent().forEach(this::addArticle);
    }

    private void addArticle(Article article) {
        articlesLayout.add(new RouterLink(article.getTitle(), ArticleView.class, article.getArticleId()));
        articlesLayout.add(new Div(new Label(ArticleUtil.getReadableContent(article))));
    }
}
