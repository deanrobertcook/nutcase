package org.theronin.nutcase.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.theronin.nutcase.ui.components.SignInForm;
import org.theronin.nutcase.ui.pages.DashboardPage;

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
    private SignInForm signInLayout = new SignInForm(DashboardPage.PATH);

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Navigation Example");
        buildLayout();
    }

    private void buildLayout() {
        setContent(new VerticalLayout() {{
            setSizeFull();
            setMargin(true);

            Panel centerPanel = new Panel() {{
                setWidth(75f, Unit.PERCENTAGE);
                setHeight(70f, Unit.PERCENTAGE);

                setContent(new VerticalLayout() {{
                    setSizeFull();
                    setMargin(true);

                    addComponent(welcomeLogo);
                    welcomeLogo.setSizeUndefined();
                    setComponentAlignment(welcomeLogo, Alignment.MIDDLE_CENTER);
                    setExpandRatio(welcomeLogo, 1f);

                    addComponent(signInLayout);
                    signInLayout.setWidthUndefined();
                    signInLayout.setHeight(40f, Unit.PERCENTAGE);
                    setComponentAlignment(signInLayout, Alignment.MIDDLE_CENTER);
                    setExpandRatio(signInLayout, 8f);
                }});
            }};

            addComponent(centerPanel);
            setComponentAlignment(centerPanel, Alignment.MIDDLE_CENTER);
        }});
    }
}
