package ru.kpfu.itis.aekrylov.itproject.client;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Client extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launchApp(Client.class, MainView.class, args);
    }
}
