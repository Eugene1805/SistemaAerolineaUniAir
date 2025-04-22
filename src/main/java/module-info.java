module com.mycompany.sistemaaerolineauniair {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.sistemaaerolineauniair to javafx.fxml;
    exports com.mycompany.sistemaaerolineauniair;
}
