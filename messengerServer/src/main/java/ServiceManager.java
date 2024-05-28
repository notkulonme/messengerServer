import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceManager {
    private static ServiceManager instance;
    private ArrayList<ServerSerrvice> services;

    private ServiceManager() {
        services = new ArrayList<ServerSerrvice>();
    }

    /***
     * this class is singleton
     * @return only instance of the class
     */
    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    public void addService(ServerSerrvice service) {
        services.add(service);
    }

    /***
     * closes all runing service
     */
    public void closeAllService() {
        for (ServerSerrvice service : services) {
            service.close();
        }
        services = new ArrayList<ServerSerrvice>();
    }

    /***
     * closes a service with the given name
     * @param name service name
     */
    public void closeService(String name) {
        services.stream()
                .filter(serrvice -> {
                    if (serrvice.getName().equals(name)) {
                        serrvice.close();
                        return false;
                    } else {
                        return true;
                    }
                })
                .collect(Collectors.<ServerSerrvice>toList());
    }
    public int getRuningServiceCount(){
        return services.size();
    }
    public List<ServerSerrvice> getRunningServices(){
        return (List<ServerSerrvice>) services.clone();
    }


    public void close() {
        instance = null;
    }


    public String getName() {
        return "Service Manager";
    }

}
