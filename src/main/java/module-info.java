module com.fitj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires kotlin.stdlib;

    exports com.fitj;
//    exports com.fitj.controllers;
//    exports com.fitj.exceptions;

    opens com.fitj.controllers.users to javafx.fxml;
    opens com.fitj.controllers.clients to javafx.fxml;
    opens com.fitj.controllers.coachs to javafx.fxml;
    opens com.fitj.controllers.admins to javafx.fxml;
}