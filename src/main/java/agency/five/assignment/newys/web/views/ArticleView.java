package agency.five.assignment.newys.web.views;

import agency.five.assignment.newys.core.model.Article;
import agency.five.assignment.newys.core.model.Comment;
import agency.five.assignment.newys.core.security.SecurityUtils;
import agency.five.assignment.newys.core.service.ArticleService;
import agency.five.assignment.newys.core.service.CommentService;
import agency.five.assignment.newys.web.utils.ArticleUtil;
import agency.five.assignment.newys.web.utils.DateUtil;
import agency.five.assignment.newys.web.utils.MsgUtil;
import agency.five.assignment.newys.web.views.components.CommentForm;
import agency.five.assignment.newys.web.views.components.Pager;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Route(value = "article", layout = MainLayout.class)
@PageTitle("Newys")
public class ArticleView extends VerticalLayout implements HasUrlParameter<Long> {

    private ArticleService articleService;
    private CommentService commentService;

    private Article article;
    private Div commentSection;
    private Button newCommentButton;
    private Pager pager;
    private Integer pageCommentSize;

    public ArticleView(ArticleService articleService, CommentService commentService,
                       @Value("${page.comment.size}") Integer pageCommentSize) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.pageCommentSize = pageCommentSize;

        newCommentButton = new Button(MsgUtil.get("article.comments.newComment"));
        newCommentButton.addClickListener(event -> onNewComment());
    }

    private void onNewComment() {
        CommentForm editForm = new CommentForm(commentService);

        commentSection.add(editForm);

        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setAuthor(SecurityUtils.getCurrentUser());

        editForm.onClose(c -> fetchPage(1));
        editForm.showCommentForm(comment);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        article = articleService.findById(aLong);
        setArticle(article);
    }

    private void setArticle(Article article) {
        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.add(new H2(article.getTitle()));

        if (SecurityUtils.currentUserCanEditArticle() && articleService.isUsersArticle(SecurityUtils.getCurrentUser(), article)) {
            Button editButton = new Button(MsgUtil.get("article.editArticle.button"));
            editButton.addClickListener(event -> UI.getCurrent().navigate(EditArticleView.class, article.getArticleId()));
            titleLayout.add(editButton);
        }

        add(titleLayout);
        Div datesDiv = new Div();
        datesDiv.add(MsgUtil.get("article.dates.label", DateUtil.formatDateTime(article.getLastModified()), DateUtil.formatDateTime(article.getCreated())));
        add(new Div(new Label(ArticleUtil.getReadableContent(article))));
        add(createCommentSection());

        fetchPage(1);
    }


    private Div createCommentSection() {
        Div commentLayout = new Div();
        commentSection = new Div();
        commentLayout.add(new H3(MsgUtil.get("article.comments.label")));

        if (SecurityUtils.currentUserCanComment()) {
            commentLayout.add(newCommentButton);
        }

        pager = new Pager(pageCommentSize);
        pager.onPageChange(this::fetchPage);

        commentLayout.add(commentSection, pager);
        return commentLayout;
    }

    private void fetchPage(int page) {
        Page<Comment> comments = commentService.findByArticle(article, PageRequest.of(page - 1, pageCommentSize, Sort.Direction.DESC, "lastModified"));
        setCommentSection(comments);
        pager.setCurrentPage(page);
        pager.setTotalResults(comments.getTotalElements());
    }

    private void setCommentSection(Page<Comment> comments) {
        commentSection.removeAll();
        comments.getContent().forEach(comment -> commentSection.add(createComment(comment)));
    }

    private Div createComment(Comment comment) {
        Div commentDiv = new Div();

        Div readOnlyDiv = new Div();
        CommentForm editForm = new CommentForm(commentService);

        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.add(new H5(MsgUtil.get("article.comment.title.label", comment.getAuthor().getUsername())));

        Label dateLabel = new Label(MsgUtil.get("article.comment.comment.label", DateUtil.formatDateTime(comment.getLastModified())));
        Paragraph paragraphContent = new Paragraph(ArticleUtil.getReadableContent(comment));

        Button editButton = new Button(MsgUtil.get("article.comments.editComment"));
        editButton.addClickListener(event -> {
            editForm.showCommentForm(comment);
            readOnlyDiv.setVisible(false);

            editForm.onClose(c -> fetchPage(1));
        });

        if (SecurityUtils.currentUserCanComment() && commentService.isUsersComment(SecurityUtils.getCurrentUser(), comment)) {
            titleLayout.add(editButton);
        }

        readOnlyDiv.add(titleLayout);
        readOnlyDiv.add(dateLabel);
        readOnlyDiv.add(paragraphContent);

        commentDiv.add(readOnlyDiv);
        commentDiv.add(editForm);

        return commentDiv;
    }
}
