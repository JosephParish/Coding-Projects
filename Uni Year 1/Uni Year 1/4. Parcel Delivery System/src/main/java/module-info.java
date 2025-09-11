module cw2.cw2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens cw2.cw2 to javafx.fxml;
    exports cw2.cw2;
}