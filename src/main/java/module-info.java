module com.example.stopper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.stopper to javafx.fxml;
    exports com.example.stopper;
}