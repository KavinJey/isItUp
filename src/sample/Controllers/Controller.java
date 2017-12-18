package sample.Controllers;

import com.sun.javafx.iio.ios.IosDescriptor;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {


    //Declaration of the menus
    @FXML private MenuBar menuBar;
    @FXML final Menu sites = new Menu("Sites...");
    @FXML final MenuItem addSite = new MenuItem("Add Site");
    @FXML final MenuItem editSite = new MenuItem("Edit Site");
    @FXML final Menu help = new Menu("About");



    //About page handlers
    @FXML public void handleAboutButton(ActionEvent event) throws IOException {
        Stage window = new Stage(); // Initializes new window

        Parent aboutWin = FXMLLoader.load(getClass().getResource("../Views/about.fxml")); //loads up scene
        Scene scene = new Scene(aboutWin, 300, 100);

        window.setScene(scene); //This whole block just puts the window into the scene and pow you got ur text thing going off
        window.setResizable(false);
        window.show();

    }


    //Add site handler
    @FXML public void handleAddSiteButton(ActionEvent event) throws IOException{

        // TODO: 18/12/17 Make this function add a vbox element with the site and shit


    }



}
