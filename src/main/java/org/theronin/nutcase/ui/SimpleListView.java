package org.theronin.nutcase.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public class SimpleListView extends VerticalLayout implements View {
    JpaRepository repository;
    Grid grid;
    Class type;

    Navigator navigator;

    @Autowired
    public SimpleListView(JpaRepository repository, Class type, String title) {
        this.repository = repository;
        grid = new Grid();
        this.type = type;

        setSizeFull();
        setMargin(true);

        Label label = new Label(title);
        addComponent(label);

        addComponent(grid);
        grid.setSizeFull();
        listTestCases();

        setExpandRatio(grid, 1);
    }

    private void listTestCases() {
        grid.setContainerDataSource(new BeanItemContainer(type, repository.findAll()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
