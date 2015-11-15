package org.theronin.nutcase.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 *
 */
public class HomeView extends VerticalLayout implements View {

    Navigator navigator;

    public HomeView() {
        setMargin(true);

        addComponent(new Button("Users", event -> {
            navigator.navigateTo(NavigatorUI.USERS_LIST_VIEW);
        }));

        addComponent(new Button("Test Cases", event -> {
            navigator.navigateTo(NavigatorUI.TEST_CASE_LIST_VIEW);
        }));

        addComponent(createSignInPanel());

    }

    public Panel createSignInPanel() {
        Panel panel = new Panel("Sign in");
        panel.setSizeUndefined();

        FormLayout fl = new FormLayout();
        fl.setMargin(true);
        fl.setSizeUndefined();

        TextField emailField = new TextField("Email");
        fl.addComponent(emailField);
        emailField.setRequired(true);
        emailField.setRequiredError("You must provide an email address");

        PasswordField passwordField = new PasswordField("Password");
        fl.addComponent(passwordField);
        passwordField.setRequired(true);
        passwordField.setRequiredError("You must provide a password");

        Button submitButton = new Button("Submit", event -> {
            Notification.show("Trying really hard to sign up user " + emailField.getValue() + " with password: " + passwordField.getValue(),
                    Notification.Type.TRAY_NOTIFICATION);
        });
        fl.addComponent(submitButton);

        panel.setContent(fl);

        return panel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
