package org.theronin.nutcase.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.theronin.nutcase.ui.components.SignInForm;

import java.io.File;

/**
 *
 */
@SpringUI(path = SplashUI.ROOT)
@Theme(SplashUI.THEME)
public class SplashUI extends UI {

    public static final String ROOT = "/nutcase";
    public static final String THEME = "valo";

    public static final String IMAGES_ROOT = "src/main/resources/images/";

    private Image welcomeLogo = new Image(null, new FileResource(new File(IMAGES_ROOT + "logo-large.png")));
    private SignInForm signInLayout = new SignInForm();

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Navigation Example");
        buildLayout();
    }

    private void buildLayout() {
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        content.setSizeFull();
        content.setMargin(true);

        Panel centerPanel = new Panel();
        content.addComponent(centerPanel);
        content.setComponentAlignment(centerPanel, Alignment.MIDDLE_CENTER);
        centerPanel.setWidth(75f, Unit.PERCENTAGE);
        centerPanel.setHeight(70f, Unit.PERCENTAGE);

        VerticalLayout panelContent = new VerticalLayout();
        centerPanel.setContent(panelContent);
        panelContent.setSizeFull();
        panelContent.setMargin(true);

        panelContent.addComponent(welcomeLogo);
        welcomeLogo.setSizeUndefined();
        panelContent.setComponentAlignment(welcomeLogo, Alignment.MIDDLE_CENTER);
        panelContent.setExpandRatio(welcomeLogo, 1f);


        panelContent.addComponent(signInLayout);
        signInLayout.setWidthUndefined();
        signInLayout.setHeight(40f, Unit.PERCENTAGE);
        panelContent.setComponentAlignment(signInLayout, Alignment.MIDDLE_CENTER);
        panelContent.setExpandRatio(signInLayout, 8f);
    }
}
