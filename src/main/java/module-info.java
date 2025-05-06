module com.mycompany.sistemaaerolineauniair {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.sistemaaerolineauniair to javafx.fxml;
    exports com.mycompany.sistemaaerolineauniair;
}
