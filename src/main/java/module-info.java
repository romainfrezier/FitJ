module com.fitj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires kotlin.stdlib;

    opens com.fitj.controllers to javafx.fxml;
    exports com.fitj;
    exports com.fitj.controllers;
    exports com.fitj.controllers.factory;
    exports com.fitj.classes;
    exports com.fitj.models;
    exports com.fitj.models.connexions;
    exports com.fitj.models.factory;
    exports com.fitj.models.methodesBD;
    exports com.fitj.models.postgresql;
    exports com.fitj.facades;
    exports com.fitj.facades.factory;
    exports com.fitj.facades.postgresql;
}