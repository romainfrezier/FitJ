module com.fitj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires kotlin.stdlib;

    opens com.fitj.controllers to javafx.fxml;
    exports com.fitj;
    exports com.fitj.controllers;
}