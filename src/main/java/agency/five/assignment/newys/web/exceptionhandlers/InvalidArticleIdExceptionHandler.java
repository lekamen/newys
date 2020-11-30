package agency.five.assignment.newys.web.exceptionhandlers;

import agency.five.assignment.newys.core.exceptions.InvalidArticleIdException;
import agency.five.assignment.newys.web.utils.MsgUtil;
import agency.five.assignment.newys.web.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;

import javax.servlet.http.HttpServletResponse;

@Tag(Tag.DIV)
@ParentLayout(MainLayout.class)
public class InvalidArticleIdExceptionHandler extends Component implements HasErrorParameter<InvalidArticleIdException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<InvalidArticleIdException> parameter) {
        getElement().setText(MsgUtil.get("exception.invalidArticleIdException", parameter.getException().getArticleId()));
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}