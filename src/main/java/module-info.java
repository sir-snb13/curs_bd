module org.example.curs_bd {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.example.curs_bd to javafx.fxml;
    exports org.example.curs_bd;
}