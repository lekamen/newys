package agency.five.assignment.newys.web.exceptionhandlers;

import agency.five.assignment.newys.web.utils.MsgUtil;
import agency.five.assignment.newys.web.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.*;

import javax.servlet.http.HttpServletResponse;

@Tag(Tag.DIV)
@ParentLayout(MainLayout.class)
public class NotFoundExceptionHandler extends Component implements HasErrorParameter<NotFoundException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
        getElement().setText(MsgUtil.get("exception.notFoundException", event.getLocation().getPath()));
        return HttpServletResponse.SC_NOT_FOUND;
    }
}