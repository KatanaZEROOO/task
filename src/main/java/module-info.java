module com.example.task {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires java.sql;

    opens com.example.task to javafx.fxml;
    exports com.example.task;
}