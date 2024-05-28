import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        ServerSocket server;
        Logger logger = new Logger();
        try {
            server = new ServerSocket(1);
        } catch (IOException e) {
            server = null;
            logger.logError(e.getMessage());
        }
        logger.logError("test error");
        logger.log("normal log");

    }
}
