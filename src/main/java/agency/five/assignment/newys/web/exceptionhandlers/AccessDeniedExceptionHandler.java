package agency.five.assignment.newys.web.exceptionhandlers;

import agency.five.assignment.newys.web.utils.MsgUtil;
import agency.five.assignment.newys.web.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletResponse;

@Tag(Tag.DIV)
@ParentLayout(MainLayout.class)
public class AccessDeniedExceptionHandler extends Component implements HasErrorParameter<AccessDeniedException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<AccessDeniedException> parameter) {
        getElement().setText(MsgUtil.get("exception.accessDeniedException"));
        return HttpServletResponse.SC_FORBIDDEN;
    }
}