module com.terminkalender {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jdk.javadoc;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.terminkalender to javafx.fxml;
    exports com.terminkalender;
    exports com.terminkalender.controller;
    opens com.terminkalender.controller to javafx.fxml;
    exports com.terminkalender.model;
    opens com.terminkalender.model to javafx.fxml;
}