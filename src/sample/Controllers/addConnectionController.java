package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import sample.Main;
import sample.Model.Site;


public class addConnectionController  {
    @FXML private TextField siteLink = new TextField();
    @FXML private Button confirm = new Button();


    private Main mainApp = new Main();


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

    }


    @FXML
    private void initialize() {

        siteLink.setFocusTraversable(false); //makes the promptText appear

        //Code for if they hit enter, same method runs
        siteLink.setOnKeyPressed((event) -> { if(event.getCode() == KeyCode.ENTER) {
            ActionEvent x = new ActionEvent();
            handleConfirmButton(x);
            siteLink.getScene().getWindow().hide();
        }
        });


    }


    @FXML public void handleConfirmButton(ActionEvent event){

        //Getting text from the textfield
        String siteName = siteLink.getText();

        if(siteName.contains("http")){
            //Do I check connectivity here or somewhere else hmmmm???

            Site newSite = new Site(siteName);
            ObservableList<Site> siteData = mainApp.getSiteData();
            siteData.add(newSite);

            mainApp.setSiteData(siteData);


        }

        else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid Site Link, please try again.");
            alert.setGraphic(null);

            alert.showAndWait();

        }

        //TODO adds siteLink to XML file

    }




}
