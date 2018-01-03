package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Controllers.siteOverviewController;
import sample.Model.Site;
import sample.Model.SiteListWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Main extends Application {

    private BorderPane rootLayout;
    private Stage primaryStage;

    //Where our sites are held
    private ObservableList<Site> siteData = FXCollections.observableArrayList();

    //Saved data from session
    private File saveData = new File("/home/kavinjey/Desktop/IsItUp2/src/sample/Resources/data.xml");

    public ObservableList<Site> getSiteData() {
        return siteData;
    }

    public void setSiteData(ObservableList<Site> x) {
        siteData = x;

    }



    public void setSiteFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("isItUp " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("isItUp");
        }
    }

    public void saveSiteDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SiteListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            SiteListWrapper wrapper = new SiteListWrapper();
            wrapper.setSites(siteData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setSiteFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }



    public Main() {
        // Add some sample data




    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IsItUp?");

        loadSiteDataFromFile(saveData);

        initRootLayout();
        showSiteOverview();
    }

    @Override
    public void stop(){

        saveSiteDataToFile(saveData);

    }


    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/RootLayout.fxml"));
            rootLayout =  loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showSiteOverview(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/siteOverview.fxml"));
            AnchorPane siteOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(siteOverview);

            // Give the controller access to the main app.
            siteOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSiteDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SiteListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            SiteListWrapper wrapper = (SiteListWrapper) um.unmarshal(file);

            siteData.clear();
            siteData.addAll(wrapper.getSites());

            // Save the file path to the registry.
            setSiteFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("The sites from your previous session were not saved correctly.");
            alert.setGraphic(null);

            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
