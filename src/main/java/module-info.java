module com.tictaccode.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    
    
    opens com.tictaccode.tictactoe to javafx.fxml;
    exports com.tictaccode.tictactoe;
}