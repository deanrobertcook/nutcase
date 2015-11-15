package org.theronin.nutcase.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.theronin.nutcase.repository.NutcaseUserRepository;
import org.theronin.nutcase.repository.TestCaseRepository;

/**
 *
 */
@SpringUI
@Theme("valo")
public class NavigatorUI extends UI {
    protected static final String USERS_LIST_VIEW = "";
    protected static final String TEST_CASE_LIST_VIEW = "test_cases";

    Navigator navigator;

    NutcaseUserRepository nutcaseUserRepository;
    TestCaseRepository testCaseRepository;

    @Autowired
    public NavigatorUI(NutcaseUserRepository nutcaseUserRepository,
                       TestCaseRepository testCaseRepository) {
        this.nutcaseUserRepository = nutcaseUserRepository;
        this.testCaseRepository = testCaseRepository;
    }

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Navigation Example");

        navigator = new Navigator(this, this);

        navigator.addView(USERS_LIST_VIEW, new UserListView(nutcaseUserRepository));
        navigator.addView(TEST_CASE_LIST_VIEW, new TestCaseView(testCaseRepository));
    }
}
