package sample.Controllers;

import it.grabz.grabzit.GrabzItClient;
import javafx.concurrent.Task;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class siteOverviewController {

    //Left side of split-pane
    @FXML private TableView<Site> siteTableView;
    @FXML private TableColumn<Site, String> linkColumn;
    @FXML MenuItem addConnection = new MenuItem();
    @FXML MenuItem editConnection = new MenuItem();
    @FXML MenuItem aboutItem = new MenuItem();


    //Right side of the split-pane
    @FXML Label statusText = new Label("text");
    @FXML Circle statusCircle = new Circle();
    @FXML ImageView webShot = new ImageView();



    // TODO: 22/12/17 Have all the handle button methods working (Add, Edit, About)
    //TODO have this pull data from an XML file instead of other shit tyvm


    // Reference to the main application.
    private Main mainApp;


    public siteOverviewController() {
    }


    @FXML
    private void initialize() {


        // Initialize the person table with the two columns.
        linkColumn.setCellValueFactory(cellData -> cellData.getValue().getLink());

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


    @FXML private void handleSiteSelected() throws IOException {

        Site row = siteTableView.getSelectionModel().getSelectedItem();

        Boolean siteIsUp = null;
        try {
            siteIsUp = checkConnection(row.getLink().getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(siteIsUp) {
            statusCircle.setFill(Color.LIGHTGREEN);
            statusText.setText(row.getLink().getValue() + " is up!");
            lookUp(row.getLink().getValue());
        }

        else {
            statusCircle.setFill(Color.RED);
            statusText.setText("Oops! " + row.getLink().getValue() + " is down.");
        }


    }


    public void spillTheBeans() throws IOException {
        File f = new File("./src/sample/Resources/"); // current directory

        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.print("directory:");
            } else {
                System.out.print("     file:");
            }
            System.out.println(file.getCanonicalPath());
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

    private void lookUp(String link) throws IOException {



        String imageName = link.replace("/", "1");
        imageName = imageName.replace(".", "4");
        imageName = imageName.replace(":", "3");

        String filePath = "../Resources/" + imageName + ".jpg";
        String dir = System.getProperty("user.dir") + "/src/sample/Resources/";
        filePath = dir + imageName + ".jpg";

        final String path = filePath;

        File file = new File(path);

        if(file.exists()){

            System.out.println("Okay, now we're trying to make the image");
            System.out.println(imageName);
            spillTheBeans();
            Image image = new Image("sample/Resources/" + imageName + ".jpg");
            webShot.setImage(image);
        }

        else{
            //Task for image, so it doesn't stall the rest of the application
            getSnapShot(link);
            lookUp(link);


        }//End of Else

    }



    private void getSnapShot(String link){

        //TODO make this fucking thing save the image to XML

        String imageName = link.replace("/", "1");
        imageName = imageName.replace(".", "4");
        imageName = imageName.replace(":", "3");

        String dir = System.getProperty("user.dir") + "/src/sample/Resources/";
        String filePath = dir + imageName + ".jpg";

        final String path = filePath;

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                GrabzItClient grabzIt = new GrabzItClient("NWIxOTU0NjlkZWQwNGNiODg0MjkyOTgzYTcxZTU0ODI=", "PD9mRj9APyI/Pz9qKz8/Hj8iDz8/P1JLPz96Pz8/X14=");
                grabzIt.URLToImage(link);
                grabzIt.SaveTo(path);

                return null;
            }
        };

        Thread th = new Thread(task);
        th.start();
        while(th.getState() != Thread.State.TERMINATED) {
            System.out.println(th.getState());

        }


    }

}
