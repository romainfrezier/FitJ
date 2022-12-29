module com.fitj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires kotlin.stdlib;
    requires com.zaxxer.hikari;
    requires org.slf4j;

    exports com.fitj;

    opens com.fitj.controllers.users to javafx.fxml;
    opens com.fitj.controllers.clients to javafx.fxml;
    opens com.fitj.controllers.coachs to javafx.fxml;
    opens com.fitj.controllers.admins to javafx.fxml;
    opens com.fitj.controllers.sports to javafx.fxml;
    opens com.fitj.controllers.headers to javafx.fxml;
    opens com.fitj.controllers.materiels to javafx.fxml;
    opens com.fitj.controllers.aliments to javafx.fxml;
    opens com.fitj.controllers.recettes to javafx.fxml;
    opens com.fitj.controllers.exercices to javafx.fxml;

    opens com.fitj.classes to javafx.base;
}