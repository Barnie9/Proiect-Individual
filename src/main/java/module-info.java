module movienight {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.net.http;


    exports controllers;
    opens controllers to javafx.fxml;
    exports classes;
    opens classes to javafx.fxml;
    exports api;
    opens api to javafx.fxml;
}