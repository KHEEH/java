import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String CONFIG_FILE = "server_info.dat";
    private static final String DEFAULT_SERVER_IP = "localhost";
    private static final int DEFAULT_SERVER_PORT = 1234;

    private String serverIp;
    private int serverPort;

    public Config() {
        loadConfig();
    }

    private void loadConfig() {
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            Properties properties = new Properties();
            properties.load(input);

            serverIp = properties.getProperty("server_ip", DEFAULT_SERVER_IP);
            serverPort = Integer.parseInt(properties.getProperty("server_port", String.valueOf(DEFAULT_SERVER_PORT)));
        } catch (IOException e) {
            // Use default values if the configuration file is not found
            serverIp = DEFAULT_SERVER_IP;
            serverPort = DEFAULT_SERVER_PORT;
        }
    }

    public String getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }
}
