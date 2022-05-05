module com.tictaccode.tictactoeserver {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens com.tictaccode.tictactoeserver to javafx.fxml;
    exports com.tictaccode.necessities;
    exports com.tictaccode.tictactoeserver;
}