package pl.put.poznan.json_tools.views.Minify;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.io.IOUtils;
import pl.put.poznan.json_tools.logic.JSONToolsService;
import pl.put.poznan.json_tools.views.MainLayout;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@PageTitle("Minify")
@Route(value = "minify", layout = MainLayout.class)
public class MinifyView extends VerticalLayout {

    private MemoryBuffer buffer;
    private JSONToolsService service;
    public MinifyView() {
        buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        service = new JSONToolsService();
        TextArea textArea = new TextArea();

        upload.addSucceededListener(event -> {
            String result;
            InputStream inputStream = buffer.getInputStream();
            try {
                String textJson = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                result = service.minify(textJson);
                textArea.setValue(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        add(upload, textArea);

    }
}
