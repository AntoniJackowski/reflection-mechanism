module com.example.reflectionmechanism {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.reflectionmechanism to javafx.fxml;
    exports com.example.reflectionmechanism;
}