package agency.five.assignment.newys.web.views.admin;

import agency.five.assignment.newys.core.model.UserAccount;
import agency.five.assignment.newys.core.service.UserService;
import agency.five.assignment.newys.web.utils.MsgUtil;
import agency.five.assignment.newys.web.views.MainLayout;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "review-author-requests", layout = MainLayout.class)
@PageTitle("Review author requests")
public class ReviewAuthorRequestsView extends VerticalLayout {

    private UserService userService;

    public ReviewAuthorRequestsView(UserService userService) {
        this.userService = userService;
        List<UserAccount> users = userService.findUsersWithRequestedAuthorStatus();

        users.forEach(this::createSection);
    }

    private void createSection(UserAccount user) {
        add(new Label(user.getUsername()));

        Button accept = new Button(MsgUtil.get("reviewAuthorRequests.accept.button"));
        accept.addClickListener(e -> {
            userService.userAcceptsAuthor(user);
            UI.getCurrent().getPage().reload();
        });

        Button decline = new Button(MsgUtil.get("reviewAuthorRequests.decline.button"));
        decline.addClickListener(e -> {
            userService.userDeclinesAuthor(user);
            UI.getCurrent().getPage().reload();
        });
        HorizontalLayout buttonsLayout = new HorizontalLayout(accept, decline);
        add(buttonsLayout);
        add(new HtmlComponent("br"));
    }
}
