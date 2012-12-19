package es.steria.rest.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import es.steria.rest.model.Busines;

@XmlRootElement(name="listado")
public class ListBusines {

    private List<Busines> busines;
    
    public ListBusines() {
	busines = new ArrayList<Busines>();
    }

    public ListBusines(List<Busines> busines) {
	this.busines = busines;
    }

    public List<Busines> getBusines() {
        return busines;
    }

    public void setBusines(List<Busines> busines) {
        this.busines = busines;
    }    
}
