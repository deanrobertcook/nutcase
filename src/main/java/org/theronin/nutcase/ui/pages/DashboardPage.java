package org.theronin.nutcase.ui.pages;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.theronin.nutcase.domain.project.Project;
import org.theronin.nutcase.domain.project.ProjectService;
import org.theronin.nutcase.ui.SplashUI;
import org.theronin.nutcase.ui.components.ProjectOverview;

import javax.inject.Inject;
import java.util.List;

/**
 *
 */
@SpringUI(path = DashboardPage.PATH)
@Theme(SplashUI.THEME)
@Widgetset("WidgetSet")
public class DashboardPage extends UI {

    public static final String PATH = SplashUI.ROOT + "/dashboard";

    @Inject
    ProjectService projectService;

    @Override
    protected void init(VaadinRequest request) {
        setContent(new HorizontalLayout() {{
            setSizeFull();

            Panel leftPane = new Panel() {{
                setSizeFull();

                setContent(new VerticalLayout() {{


                }});

            }};
            addComponent(leftPane);
            setExpandRatio(leftPane, 20f);

            Panel rightPane = new Panel() {{
                setSizeFull();

                setContent(new VerticalLayout() {{
//                    setSpacing(true);

                    List<Project> projects = projectService.readAll();
                    for (Project project : projects) {
                        addComponent(new ProjectOverview(project){{
                            setMargin(true);
                            setWidth("100%");
                            setHeightUndefined();

                            addLayoutClickListener(event -> {
                                Notification.show(project.getDescription());
                            });
                        }});
                    }

                }});

            }};
            addComponent(rightPane);
            setExpandRatio(rightPane, 80f);
        }});
    }
}
