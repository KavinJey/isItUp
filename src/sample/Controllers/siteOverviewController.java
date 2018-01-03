package sample.Controllers;

import it.grabz.grabzit.GrabzItClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.Site;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.concurrent.CancellationException;

public class siteOverviewController {

    //Left side of split-pane
    @FXML private TableView<Site> siteTableView;
    @FXML private TableColumn<Site, String> linkColumn;
    @FXML MenuItem addConnection = new MenuItem();
    @FXML MenuItem aboutItem = new MenuItem();


    @FXML Button refreshButton = new Button();

    //Right side of the split-pane
    @FXML Label statusText = new Label("text");
    @FXML Circle statusCircle = new Circle();
    @FXML ImageView webShot = new ImageView();
    @FXML ProgressBar progressToScreenShot = new ProgressBar();

    //TODO have this pull data from an XML file instead of other shit tyvm


    // Reference to the main application.
    private Main mainApp;


    @FXML
    private void initialize() {


        progressToScreenShot.setOpacity(0);

        // Initialize the site column with some sicc data from our xml
        linkColumn.setCellValueFactory(cellData -> cellData.getValue().linkProperty());

        //Anon function to make addConnection function work
        addConnection.setOnAction(x -> {
            try {
                Stage newWind = new Stage();
                FXMLLoader newLoad = new FXMLLoader();
                newLoad.setLocation(Main.class.getResource("View/AddLayout.fxml"));
                Scene addScreen = new Scene(newLoad.load());

                //Reference to mainApp to use methods and stuff, suuupppper useful geez
                addConnectionController controller = newLoad.getController();
                controller.setMainApp(mainApp);


                newWind.setScene(addScreen);
                newWind.showAndWait();


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Anon function to make aboutItem work
        aboutItem.setOnAction(x -> {
            //Alert pop-ups
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("IsItUp");
            alert.setHeaderText("About");
            alert.setContentText("Author: Jeyakavin Jeyaranjan\nWebsite: https://kavinjey.tech");
            alert.showAndWait();

            });

        //Anon function to make editConnection function work



    }


    @FXML private void handleRefreshButton(){
        mainApp.initRootLayout();
        mainApp.showSiteOverview();

    }

    @FXML private void handleSiteSelected() {

        Site row = siteTableView.getSelectionModel().getSelectedItem();
        webShot.setImage(null);
        progressToScreenShot.setOpacity(0);

        Boolean siteIsUp = null;
        try {
            siteIsUp = checkConnection(row.getLink());
        } catch (Exception e) {
            e.printStackTrace();
            statusCircle.setFill(Color.RED);
            statusText.setText("Oops! " + row.getLink() + " is down.");
        }

        if(siteIsUp) {
            statusCircle.setFill(Color.LIGHTGREEN);
            statusText.setText(row.getLink() + " is up!");
            lookUp(row);
        }

        else {
            statusCircle.setFill(Color.RED);
            statusText.setText("Oops! " + row.getLink() + " is down.");
        }


    }


    //Connection Check Method
    public static boolean checkConnection(String targetUrl) throws IOException {

        System.out.println(targetUrl);

        HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(targetUrl).openConnection();

        httpUrlConnection.setRequestMethod("HEAD");

        try {
            int responseCode = httpUrlConnection.getResponseCode();
            return responseCode == 403 || responseCode == 200;

        } catch (UnknownHostException noInternetConnection) {
            return false;
        }
    }

    //This method lets us use things from our main
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        siteTableView.setItems(mainApp.getSiteData());
    }

    private void lookUp(Site site) {

        String link = site.getLink();

        String imageName = link.replace("/", "1");
        imageName = imageName.replace(".", "4");
        imageName = imageName.replace(":", "3");


        if(site.getImagePath() != null){
            //what to do if this shit does have an image already
            System.out.println("sample/Resources/images/" + site.getImagePath() + ".jpg");
            String imagePath = "/sample/Resources/images/" + site.getImagePath() + ".jpg";
            imagePath.trim();



            System.out.println(site.getImagePath());
            System.out.println(System.getProperty("user.dir"));
            Image image = new Image(imagePath);

            webShot.setImage(image);

        }

        else{
            //What to do if the image doesn't exist
             Snapshot service = new Snapshot();
             service.setLink(site.getLink());

             service.setOnSucceeded(t -> {
                 System.out.println("done:" + t.getSource().getValue());
                 site.setImagePath(t.getSource().getValue().toString());
                 progressToScreenShot.setProgress(100);
                 progressToScreenShot.setOpacity(0);

                 Image image = new Image("src/sample/Resources/images/" + site.getImagePath() + ".jpg");
                 webShot.setImage(image);

             });

             service.setOnRunning(t -> {
                 progressToScreenShot.setOpacity(1.0);
                 progressToScreenShot.setProgress(service.getProgress());
             });

             service.start();



        }//End of Else

    }


    //This is gonna be the service that runs the screenshot shit after I wake up
    private static class Snapshot extends Service<String> {
        private StringProperty link = new SimpleStringProperty();

        public final void setLink(String value) {
            link.set(value);
        }

        public final String getLink() {
            return link.getValue();
        }

        public final StringProperty linkProperty() {
            return link;
        }

        protected Task<String> createTask() {
            final String link = getLink();
            return new Task<String>() {
                protected String call() {


                    String imageName = link.replace("/", "1");
                    imageName = imageName.replace(".", "4");
                    imageName = imageName.replace(":", "3");

                    String dir = System.getProperty("user.dir") + "/src/sample/Resources/images/";
                    String filePath = dir + imageName + ".jpg";

                    GrabzItClient grabzIt = new GrabzItClient("NWIxOTU0NjlkZWQwNGNiODg0MjkyOTgzYTcxZTU0ODI=", "PD9mRj9APyI/Pz9qKz8/Hj8iDz8/P1JLPz96Pz8/X14=");

                    try {
                        grabzIt.URLToImage(link);
                        grabzIt.SaveTo(filePath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return imageName;
                }
            };
        }
    }


}




