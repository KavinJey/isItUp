package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Controllers.siteOverviewController;
import sample.Model.Site;
import sample.Model.SiteListWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Main extends Application {

    private BorderPane rootLayout;
    private Stage primaryStage;

    //Where our sites are held
    private ObservableList<Site> siteData = FXCollections.observableArrayList();

    public ObservableList<Site> getSiteData() {
        return siteData;
    }

    public void setSiteData(ObservableList<Site> x) {
        siteData = x;
    }


    public Main() {
        // Add some sample data
        siteData.add(new Site("https://www.riotgames.com/en"));
        System.out.println(siteData.get(0).getLink());


    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IsItUp?");

        initRootLayout();
        showSiteOverview();
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


    public void saveSiteXML(File file) {
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
            setSitesFilePath(file);

        }catch(Exception e){
            System.out.println("Lol wtf");

        }
    }

    public File getSiteFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setSitesFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
