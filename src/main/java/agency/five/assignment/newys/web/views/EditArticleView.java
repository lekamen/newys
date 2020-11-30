package agency.five.assignment.newys.web.views;

import agency.five.assignment.newys.core.model.Article;
import agency.five.assignment.newys.core.security.SecurityUtils;
import agency.five.assignment.newys.core.service.ArticleService;
import agency.five.assignment.newys.web.utils.MsgUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;

import static agency.five.assignment.newys.core.model.enums.TypeEnum.NEWS;
import static agency.five.assignment.newys.web.utils.Converters.STRING_TO_BYTE_ARRAY_CONVERTER;

@Route(value = "edit-article", layout = MainLayout.class)
@PageTitle("Newys")
public class EditArticleView extends VerticalLayout implements HasUrlParameter<Long> {

    private TextField titleText;
    private TextArea contentArea;

    private Button publishButton;
    private Button cancelButton;

    private Article article;
    private Binder<Article> binder = new Binder<>(Article.class);

    private ArticleService articleService;

    public EditArticleView(ArticleService articleService) {
        this.articleService = articleService;

        titleText = new TextField(MsgUtil.get("article.edit.title.text"));
        titleText.setWidth("100%");
        binder.forField(titleText).asRequired().bind(Article::getTitle, Article::setTitle);

        contentArea = new TextArea(MsgUtil.get("article.edit.content.area"));
        contentArea.setWidth("100%");
        binder.forField(contentArea).withConverter(STRING_TO_BYTE_ARRAY_CONVERTER).asRequired().bind(Article::getContent, Article::setContent);

        publishButton = new Button(MsgUtil.get("article.edit.publish.button"));
        publishButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        publishButton.addClickListener(event -> onPublish());

        cancelButton = new Button(MsgUtil.get("article.edit.cancel.button"));
        cancelButton.addClickListener(event -> onCancel());

        add(titleText, contentArea);
        add(new HorizontalLayout(publishButton, cancelButton));
    }

    private void onPublish() {
        if (binder.validate().hasErrors()) {
            Notification.show(MsgUtil.get("article.edit.publish.error"));
            return;
        }

        binder.writeBeanIfValid(article);
        article = articleService.save(article);

        UI.getCurrent().navigate(ArticleView.class, article.getArticleId());
    }

    private void onCancel() {
        if (article.getArticleId() != null) {
            UI.getCurrent().navigate(ArticleView.class, article.getArticleId());
        }
        else {
            UI.getCurrent().navigate(HomeView.class);
        }
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long aLong) {
        Article article;
        if (aLong == null) {
            article = new Article();
            article.setAuthor(SecurityUtils.getCurrentUser());
            article.setType(NEWS);
        }
        else {
            article = articleService.findById(aLong);
        }

        setArticle(article);
    }

    private void setArticle(Article article) {
        this.article = article;

        binder.readBean(article);
    }
}
