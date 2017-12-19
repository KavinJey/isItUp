package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;


public class Connections {

    @FXML private TextField siteLink = new TextField();
    @FXML private Button confirm = new Button("confirm");


    public void handleConfirmButton(ActionEvent event) {
        // TODO: 18/12/17 Get the text and make a ping to the url
        String link = siteLink.getText();
        runPing(link);



    }

    public static void runPing(String command) {
        try {
            String ipAddress = command;
            InetAddress inet = InetAddress.getByName(ipAddress);
            System.out.println("Sending Ping Request to " + ipAddress);
            if (inet.isReachable(5000)){
                System.out.println(ipAddress + " is reachable.");
            } else {
                System.out.println(ipAddress + " NOT reachable.");
            }

        } catch ( Exception e ) {
            System.out.println("Exception:" + e.getMessage());
        }

    }

}
