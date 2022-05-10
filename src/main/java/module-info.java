module com.tictaccode.tictactoegamecontroller {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens com.tictaccode.tictactoeai to javafx.fxml;
    exports com.tictaccode.necessities;
    exports com.tictaccode.tictactoeai;
}