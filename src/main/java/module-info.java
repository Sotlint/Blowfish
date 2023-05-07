module com.kurs.blowfish {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.kurs.blowfish to javafx.fxml;
    exports com.kurs.blowfish;
    exports com.kurs.blowfish.Controllers;
    opens com.kurs.blowfish.Controllers to javafx.fxml;
}