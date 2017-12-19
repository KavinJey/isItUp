package sample.Controllers;

import com.sun.javafx.iio.ios.IosDescriptor;
import com.sun.org.apache.xml.internal.security.Init;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {


    //Declaration of the menus
    @FXML private MenuBar menuBar;
    @FXML private Menu sites = new Menu("Sites...");
    @FXML private MenuItem addSite = new MenuItem("Add Site");
    @FXML private MenuItem editSite = new MenuItem("Edit Site");
    @FXML private Menu help = new Menu("About");


    @FXML private VBox verticalView;
    private HBox hbox;
    private Button buttonCurrent;
    private Button buttonProjected;




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
    @FXML public void handleAddSiteButton(ActionEvent event) throws IOException {

        Stage current = (Stage) menuBar.getScene().getWindow();

        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../Views/connections.fxml")), 435   , 85    );
        stage.setScene(scene);
        stage.showAndWait();



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        hbox = new HBox();


        buttonCurrent = new Button("ey");
        buttonProjected = new Button("oi");
        buttonCurrent.setPrefSize(100, 20);
        buttonProjected.setPrefSize(100, 20);

        // TODO: 18/12/17 gotta make the hbox generic for all sites
        hbox.getChildren().addAll(buttonCurrent, buttonProjected);

        verticalView.getChildren().addAll(hbox);

    }
}
