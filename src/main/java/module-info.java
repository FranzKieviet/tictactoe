module com.tictaccode.tictactoeserver {
    requires javafx.controls;
    requires javafx.fxml;
    
    
    opens com.tictaccode.tictactoeserver to javafx.fxml;
    exports com.tictaccode.tictactoeserver;
    exports com.tictaccode.necessities;
    opens com.tictaccode.necessities to javafx.fxml;
}