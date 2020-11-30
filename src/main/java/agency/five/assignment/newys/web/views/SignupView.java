package agency.five.assignment.newys.web.views;

import agency.five.assignment.newys.core.exceptions.UserExistsException;
import agency.five.assignment.newys.core.exceptions.UsernameTakenException;
import agency.five.assignment.newys.core.model.UserAccount;
import agency.five.assignment.newys.core.service.UserService;
import agency.five.assignment.newys.web.utils.MsgUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

@Route("signup")
@PageTitle("Newys - signup")
public class SignupView extends VerticalLayout {

    private PasswordField passwordField;
    private PasswordField repeatPasswordField;
    private Div errorDiv = new Div();
    private boolean enablePasswordValidation = false;

    private Binder<UserAccount> binder = new Binder<>(UserAccount.class);
    private UserService userService;

    public SignupView(UserService userService) {
        this.userService = userService;

        EmailField emailField = new EmailField(MsgUtil.get("signup.email.text"));
        binder.forField(emailField).asRequired().bind(UserAccount::getEmail, UserAccount::setEmail);

        TextField usernameText = new TextField(MsgUtil.get("signup.username.text"));
        binder.forField(usernameText).asRequired().bind(UserAccount::getUsername, UserAccount::setUsername);

        passwordField = new PasswordField(MsgUtil.get("signup.password.text"));
        binder.forField(passwordField).asRequired().withValidator(this::passwordValidator).bind(UserAccount::getPassword, UserAccount::setPassword);

        repeatPasswordField = new PasswordField(MsgUtil.get("signup.repeatPassword.text"));
        repeatPasswordField.addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        Button submitButton = new Button(MsgUtil.get("signup.submit.button"));
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(e -> submit());

        add(emailField, usernameText, passwordField, repeatPasswordField, submitButton, errorDiv);

    }

    private void submit() {
        errorDiv.removeAll();

        if (binder.validate().hasErrors()) {
            return;
        }

        UserAccount userAccount = new UserAccount();

        binder.writeBeanIfValid(userAccount);

        try {
            userService.registerNewUserAccount(userAccount);
            userService.manuallyLoginUser(userAccount.getUsername(), userAccount.getPassword());
            UI.getCurrent().navigate(HomeView.class);
        } catch (UserExistsException e) {
            errorDiv.add(new Label(MsgUtil.get("signup.registration.error.email", e.getEmail())));
        } catch (UsernameTakenException e) {
            errorDiv.add(new Label(MsgUtil.get("signup.registration.error.username", e.getUsername())));
        }
    }

    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {

        if (StringUtils.isEmpty(pass1)) {
            return ValidationResult.error(MsgUtil.get("signup.password.error.empty"));
        }

        if (!enablePasswordValidation) {
            // user hasn't visited the field yet, so don't validate just yet, but next time.
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = repeatPasswordField.getValue();

        if (pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error(MsgUtil.get("signup.password.error.mismatch"));
    }
}
