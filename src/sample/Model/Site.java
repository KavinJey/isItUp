package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Site {

    private final StringProperty link;
    private StringProperty imagePath;


    public Site(){
        this(null);

    }

    public Site(String link){
        this.link = new SimpleStringProperty(link);
        this.imagePath = new SimpleStringProperty(null);
    }

    public StringProperty linkProperty() {
        return link;
    }
    public void setLink(String link){
        this.link.set(link);
    }
    public String getLink(){return link.getValue(); }




    public StringProperty imagePathProperty() {return imagePath;}
    public void setImagePath(String imagePath){ this.imagePath.set(imagePath);}
    public String getImagePath() {return imagePath.getValue(); }


}
