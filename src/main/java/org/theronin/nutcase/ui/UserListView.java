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
import org.theronin.nutcase.domain.NutcaseUser;
import org.theronin.nutcase.repository.NutcaseUserRepository;

/**
 *
 */
public class UserListView extends VerticalLayout implements View {

    NutcaseUserRepository nutcaseUserRepository;
    Grid grid;
    Navigator navigator;

    @Autowired
    public UserListView(NutcaseUserRepository nutcaseUserRepository) {
        this.nutcaseUserRepository = nutcaseUserRepository;
        grid = new Grid();

        Button button = new Button("Test Cases", event -> {
            navigator.navigateTo(NavigatorUI.TEST_CASE_LIST_VIEW);
        });

        addComponent(button);

        setSizeFull();
        setMargin(true);

        Label label = new Label("Users");
        addComponent(label);

        addComponent(grid);
        grid.setSizeFull();
        listTestCases();

        setExpandRatio(grid, 1);
    }

    private void listTestCases() {
        grid.setContainerDataSource(new BeanItemContainer(NutcaseUser.class, nutcaseUserRepository.findAll()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
