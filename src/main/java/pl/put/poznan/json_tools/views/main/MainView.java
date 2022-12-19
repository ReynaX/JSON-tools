package pl.put.poznan.json_tools.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.put.poznan.json_tools.views.MainLayout;

@PageTitle("JSON-tools")
@Route(value = "", layout = MainLayout.class)

public class MainView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public MainView() {

    }

}
