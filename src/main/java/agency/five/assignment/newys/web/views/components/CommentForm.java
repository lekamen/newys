package agency.five.assignment.newys.web.views.components;

import agency.five.assignment.newys.core.model.Comment;
import agency.five.assignment.newys.core.service.CommentService;
import agency.five.assignment.newys.web.utils.MsgUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;

import java.util.function.Consumer;

import static agency.five.assignment.newys.web.utils.Converters.STRING_TO_BYTE_ARRAY_CONVERTER;

public class CommentForm extends VerticalLayout {

    private TextArea contentArea;

    private Button publishButton;
    private Button cancelButton;

    private Comment comment;
    private Binder<Comment> binder = new Binder<>(Comment.class);
    private CommentService commentService;
    private Consumer<Comment> onCloseHandler;

    public CommentForm(CommentService commentService) {
        this.commentService = commentService;

        contentArea = new TextArea(MsgUtil.get("comment.edit.content.area"));
        contentArea.setWidth("100%");
        binder.forField(contentArea).withConverter(STRING_TO_BYTE_ARRAY_CONVERTER).asRequired().bind(Comment::getContent, Comment::setContent);

        publishButton = new Button(MsgUtil.get("comment.edit.publish.button"));
        publishButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        publishButton.addClickListener(event -> onPublish());

        cancelButton = new Button(MsgUtil.get("comment.edit.cancel.button"));
        cancelButton.addClickListener(event -> onCancel());

        add(contentArea);
        add(new HorizontalLayout(publishButton, cancelButton));

        setVisible(false);
    }

    private void onPublish() {
        if (binder.validate().hasErrors()) {
            Notification.show(MsgUtil.get("comment.edit.publish.error"));
            return;
        }

        binder.writeBeanIfValid(comment);
        comment = commentService.save(comment);

        setVisible(false);
        onCloseHandler.accept(comment);
    }

    private void onCancel() {
        setVisible(false);
    }

    public void showCommentForm(Comment comment) {
        this.comment = comment;
        binder.readBean(comment);

        setVisible(true);
    }

    public void onClose(Consumer<Comment> onCloseHandler) {
        this.onCloseHandler = onCloseHandler;
    }
}
