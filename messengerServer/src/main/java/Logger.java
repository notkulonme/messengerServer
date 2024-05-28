import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Logger implements ServerSerrvice {
    static private long counter;
    private final File file;
    private final BufferedWriter writer;
    String name;

    /***
     * this class helps proper loging in server
     * @param file file to write the logs
     */
    public Logger(File file){
        System.out.println("test");
        this.file = file;
        BufferedWriter writerBuffer;
        try {
            writerBuffer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            writerBuffer = null;
            System.err.println(e.getMessage());
        }
        writer = writerBuffer;
        ServiceManager.getInstance().addService(this);
        counter++;
        this.name = "Logger"+counter;
    }
    public Logger(){
        this.file = null;
        this.writer = null;
        ServiceManager.getInstance().addService(this);
        counter++;
        this.name = "Logger"+counter;
    }

    public synchronized void log(String message){
        String finalMessage = LocalDate.now()+" "+LocalTime.now()+": "+ message;
        System.out.println(finalMessage);
        writeToFile(finalMessage);
    }

    public synchronized void logError(String message) {
        String finalMessage = LocalDate.now() + " " + LocalTime.now() + ": " + message;
        System.err.println(finalMessage);
        writeToFile(finalMessage);
    }

    private void writeToFile(String message){
        if (writer != null) {
            try {
                writer.write(message);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /***
     * releases all resources and removes its self from the running services
     */
    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        ServiceManager.getInstance().closeService(getName());
    }
    @Override
    public String getName(){
        return name;
    }


}
