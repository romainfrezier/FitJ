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
    opens com.fitj.controllers.notifications to javafx.fxml;
    opens com.fitj.controllers.commandes to javafx.fxml;
    opens com.fitj.controllers.programmes.programmesNutritions to javafx.fxml;
    opens com.fitj.controllers.programmes.programmesSportifs to javafx.fxml;
    opens com.fitj.controllers.seances to javafx.fxml;
    opens com.fitj.controllers.monCompte to javafx.fxml;
    opens com.fitj.controllers.paiements to javafx.fxml;
    opens com.fitj.controllers.shop to javafx.fxml;
    opens com.fitj.controllers.packs to javafx.fxml;

    opens com.fitj.classes to javafx.base;
    opens com.fitj.dao.tools to javafx.base;
    opens com.fitj.comparators to javafx.fxml;
}