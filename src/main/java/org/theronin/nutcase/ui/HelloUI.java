package org.theronin.nutcase.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.theronin.nutcase.domain.NutcaseUser;
import org.theronin.nutcase.domain.TestCase;
import org.theronin.nutcase.repository.NutcaseUserRepository;
import org.theronin.nutcase.repository.TestCaseRepository;

/**
 *
 */
@SpringUI
@Theme("valo")
public class HelloUI extends UI {

    TestCaseRepository testCaseRepository;
    NutcaseUserRepository nutcaseUserRepository;
    Grid grid1;
    Grid grid2;

    @Autowired
    public HelloUI(TestCaseRepository testCaseRepository, NutcaseUserRepository nutcaseUserRepository) {
        this.testCaseRepository = testCaseRepository;
        this.nutcaseUserRepository = nutcaseUserRepository;
        grid1 = new Grid();
        grid2 = new Grid();
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        content.setMargin(true);
        content.setSizeFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        content.addComponent(horizontalLayout);


        Label label = new Label("Test Cases");
        horizontalLayout.addComponent(label);

        Button button = new Button("Users", event -> {
            grid2.setContainerDataSource(new BeanItemContainer(NutcaseUser.class, nutcaseUserRepository.findAll()));
            grid2.setSizeFull();
            content.removeComponent(grid1);
            content.addComponent(grid2);
            content.setExpandRatio(grid2, 1);
        });

        horizontalLayout.addComponent(button);

        content.addComponent(grid1);
        grid1.setSizeFull();
        listCustomers();

        content.setExpandRatio(grid1, 1);
    }

    private void listCustomers() {
        grid1.setContainerDataSource(new BeanItemContainer(TestCase.class, testCaseRepository.findAll()));
    }
}
