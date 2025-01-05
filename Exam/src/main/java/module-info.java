module com.example.exam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.exam to javafx.fxml;
    opens com.example.exam.Model to javafx.base;
    exports com.example.exam;
}