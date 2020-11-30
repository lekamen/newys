package agency.five.assignment.newys.web.views;

import agency.five.assignment.newys.core.service.UserService;
import agency.five.assignment.newys.web.utils.MsgUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route("login")
@PageTitle("Newys - login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginForm();

    public LoginView(UserService userService) {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);

        add(new H1(MsgUtil.get("application.title")), login);
        add(new RouterLink(MsgUtil.get("login.createNewAccount"), SignupView.class));

        Button continueAsGuestButton = new Button(MsgUtil.get("login.continueAsGuest"));
        continueAsGuestButton.addClickListener(e -> {
            userService.loginAsGuest();
            UI.getCurrent().navigate(HomeView.class);
        });

        add(continueAsGuestButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
