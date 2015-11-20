package org.theronin.nutcase.ui.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

/**
 *
 */
@DesignRoot
public class SignInForm extends VerticalLayout {

    private TextField emailField = new TextField("Email");
    private PasswordField passwordField = new PasswordField("Password");
    private Button submitButton = new Button("Sign in", this::signIn);

    private Panel panel = new Panel("Sign in");

    public SignInForm() {
        buildLayout();
    }

    private void buildLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        formLayout.addComponent(emailField);
        emailField.addValidator(new EmailValidator("Please enter a valid E-mail address"));

        formLayout.addComponent(passwordField);
        passwordField.addValidator(new StringLengthValidator("Please enter a password", 0, null, false));

        formLayout.addComponent(submitButton);

        panel.setContent(formLayout);

        addComponent(panel);
    }

    private void signIn(Button.ClickEvent event) {
        //TODO signin logic here
        if (emailField.isValid() && passwordField.isValid()) {
            Notification.show("Trying really hard to sign in user "
                            + emailField.getValue() + " with password: " + passwordField.getValue(),
                    Notification.Type.TRAY_NOTIFICATION);
        }

    }
}
