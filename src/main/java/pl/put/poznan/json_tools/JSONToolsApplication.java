package pl.put.poznan.json_tools;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "myapp")
@PWA(name = "JSON-tools", shortName = "JSON-tools", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class JSONToolsApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(JSONToolsApplication.class, args);
    }
}