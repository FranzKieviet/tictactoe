module com.tictaccode.tictactoegamecontroller {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens com.tictaccode.tictactoegamecontroller to javafx.fxml;
    exports com.tictaccode.necessities;
    exports com.tictaccode.tictactoegamecontroller;
}