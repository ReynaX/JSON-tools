package pl.put.poznan.json_tools.views.filter;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.io.IOUtils;
import pl.put.poznan.json_tools.logic.JSONToolsService;
import pl.put.poznan.json_tools.views.MainLayout;
import pl.put.poznan.json_tools.views.UploadExamplesI18N;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@PageTitle("Filter")
@Route(value = "filter", layout = MainLayout.class)
public class FilterView extends VerticalLayout {

    private MemoryBuffer buffer;
    private JSONToolsService service;
    public FilterView() {
        buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        service = new JSONToolsService();
        TextArea textArea = new TextArea();

        upload.setAcceptedFileTypes(".csv");

        UploadExamplesI18N i18n = new UploadExamplesI18N();
        i18n.getAddFiles().setOne("Upload CSV...");
        i18n.getDropFiles().setOne("Drop CSV here");
        i18n.getError().setIncorrectFileType(
                "The provided file does not have the correct format (CSV document).");
        upload.setI18n(i18n);

        upload.addSucceededListener(event -> {
            String result;
            InputStream inputStream = buffer.getInputStream();
            try {
                String textJson = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                result = service.filter(textJson);
                textArea.setValue(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        add(upload, textArea);

    }
}
