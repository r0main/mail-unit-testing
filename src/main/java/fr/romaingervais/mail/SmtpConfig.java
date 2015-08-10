package fr.romaingervais.mail;

/**
 * Created by Romain on 09/02/15.
 */
public class SmtpConfig {
    private String host;
    private int port;

    private SmtpConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static SmtpConfig of(String host, int port) {
        return new SmtpConfig(host, port);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
