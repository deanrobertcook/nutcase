package org.theronin.nutcase.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.theronin.nutcase.domain.TestCase;
import org.theronin.nutcase.repository.TestCaseRepository;

/**
 *
 */
public class TestCaseView extends VerticalLayout implements View {

    TestCaseRepository testCaseRepository;
    Grid grid;

    Navigator navigator;

    @Autowired
    public TestCaseView(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
        grid = new Grid();

        Button button = new Button("Users", event -> {
            navigator.navigateTo(NavigatorUI.USERS_LIST_VIEW);
        });

        addComponent(button);

        setSizeFull();
        setMargin(true);

        Label label = new Label("Test Cases");
        addComponent(label);

        addComponent(grid);
        grid.setSizeFull();
        listTestCases();

        setExpandRatio(grid, 1);
    }

    private void listTestCases() {
        grid.setContainerDataSource(new BeanItemContainer(TestCase.class, testCaseRepository.findAll()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
