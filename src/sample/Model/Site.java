package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Site {

    private final StringProperty link;
    private final StringProperty imagePath;


    public Site(){
        this(null);


    }

    public Site(String link){
        this.link = new SimpleStringProperty(link);
        this.imagePath = new SimpleStringProperty(null);
    }

    public StringProperty getLink() {
        return link;
    }

    public void setLink(String link){
        this.link.set(link);
    }
    public void setImagePath(String imagePath){ this.imagePath.set(imagePath);}


}
