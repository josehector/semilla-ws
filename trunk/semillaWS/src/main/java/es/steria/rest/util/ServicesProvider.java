package es.steria.rest.util;

public class ServicesProvider {

    private static ServicesProvider INSTANCE = new ServicesProvider();
    
    private ServicesProvider() {}
    
    public static ServicesProvider getInstance() {
	return INSTANCE;
    }
    
    public Object getServicio(String nombreServicio) {
	return SpringApplicationContext.getBean(nombreServicio);
    }
}

