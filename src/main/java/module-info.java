module test.movienight {
    requires javafx.controls;
    requires javafx.fxml;


    opens test.movienight to javafx.fxml;
    exports test.movienight;
}