module com.tictaccode.tictactoeclient {
    requires javafx.controls;
    requires javafx.fxml;
    
    
    opens com.tictaccode.tictactoeclient to javafx.fxml;
    exports com.tictaccode.tictactoeclient;
}