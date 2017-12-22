package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class RootLayoutController implements Initializable {

    @FXML private MenuBar menuBar;
    @FXML private Menu sites = new Menu("Sites...");
    @FXML private MenuItem addSite = new MenuItem("Add Site");
    @FXML private MenuItem editSite = new MenuItem("Edit Site");
    @FXML private Menu help = new Menu("About");




    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }


}
