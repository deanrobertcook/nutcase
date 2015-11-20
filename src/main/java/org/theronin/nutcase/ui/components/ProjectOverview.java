package org.theronin.nutcase.ui.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.renderers.LabelRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.AxesDefaults;
import org.dussan.vaadin.dcharts.options.Options;
import org.theronin.nutcase.domain.project.Project;

/**
 *
 */
public class ProjectOverview extends HorizontalLayout {

    private final Project project;

    private Label lastExecutedLabel;
    private Label durationLabel;
    private Label statusLabel;

    public ProjectOverview(Project project) {
        this.project = project;
        setLabelData();
        buildLayout();
    }

    public void setLabelData() {
        lastExecutedLabel = new Label(String.format("Last RC executed: %s", "12/12/12 16:43:21"));
        durationLabel = new Label(String.format("Last RC duration: %s", "2:45:21"));
        statusLabel = new Label(String.format("Last RC status: %s", "executing"));
    }

    public void buildLayout() {
        addComponent(new Panel(project.getName()) {{
            setSizeFull();

            setContent(new HorizontalLayout() {{
                setSizeFull();
                setMargin(true);

                AxesDefaults axesDefaults = new AxesDefaults()
                        .setLabelRenderer(LabelRenderers.CANVAS);

                Axes axes = new Axes()
                        .addAxis(
                                new XYaxis()
                                        .setLabel("Month")
                                        .setPad(0))
                        .addAxis(
                                new XYaxis(XYaxes.Y)
                                        .setLabel("% Coverage"));

                Options options = new Options()
                        .setTitle("Automated Test Coverage")
                        .setAxesDefaults(axesDefaults)
                        .setAxes(axes);

                DataSeries dataSeries = new DataSeries()
                        .add(1, 2, 3, 4, 5, 6, 7, 8, 9);

                DCharts chart = new DCharts()
                        .setDataSeries(dataSeries)
                        .setOptions(options)
                        .show();

                chart.setSizeFull();
                addComponent(chart);
                setExpandRatio(chart, 1f);

                VerticalLayout rightContent = new VerticalLayout() {{
                    setSizeFull();
                    setMargin(true);
                    setSpacing(true);

                    addComponent(lastExecutedLabel);
                    addComponent(durationLabel);
                    addComponent(statusLabel);
                }};
                addComponent(rightContent);
                setExpandRatio(rightContent, 1f);
            }});
        }});
    }

}
