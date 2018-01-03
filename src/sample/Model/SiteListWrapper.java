package sample.Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sites")
public class SiteListWrapper {


    private List<Site> sites;

    @XmlElement(name ="site")
    public List<Site> getSites(){
        return sites;

    }

    public void setSites(List<Site> sites){
        this.sites = sites;
    }
}
