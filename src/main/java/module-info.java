module com.estetica_automotiva {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens com.estetica_automotiva to javafx.fxml;
    opens com.estetica_automotiva.controller to javafx.fxml;
    opens com.estetica_automotiva.model to javafx.base;

    exports com.estetica_automotiva;
    exports com.estetica_automotiva.controller;
}
