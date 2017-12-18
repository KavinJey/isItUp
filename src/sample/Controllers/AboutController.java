package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AboutController {

    @FXML final Button closeButton = new Button("Got It Close!");
    @FXML final Text text = new Text("");

    @FXML public void handleCloseButton(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }



}
