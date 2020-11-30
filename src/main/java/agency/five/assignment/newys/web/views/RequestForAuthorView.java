package agency.five.assignment.newys.web.views;

import agency.five.assignment.newys.core.model.UserAccount;
import agency.five.assignment.newys.core.security.SecurityUtils;
import agency.five.assignment.newys.core.service.UserService;
import agency.five.assignment.newys.web.utils.MsgUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "request-for-author", layout = MainLayout.class)
@PageTitle("Newys - request for author")
public class RequestForAuthorView extends VerticalLayout {

    public RequestForAuthorView(UserService userService) {
        UserAccount user = SecurityUtils.getCurrentUser();

        Button requestButton = new Button(MsgUtil.get("requestForAuthor.request.button"));
        requestButton.addClickListener(e -> {
            userService.userRequestsAuthor(user);
            UI.getCurrent().getPage().reload();
        });
        switch (user.getAuthorStatus()) {
            case NONE:
                add(requestButton);
                break;
            case ACCEPTED:
                add(new Label(MsgUtil.get("requestForAuthor.status.accepted")));
                break;
            case REQUESTED:
                add(new Label(MsgUtil.get("requestForAuthor.status.requested")));
                break;
            case DECLINED:
                add(new Label(MsgUtil.get("requestForAuthor.status.declined")));
                break;
        }
    }

}
