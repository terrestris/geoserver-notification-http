package de.terrestris.geoservernotificationhttp;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import org.geoserver.notification.common.sender.NotificationSender;
import org.geotools.util.logging.Logging;

/**
 * A simple {@link NotificationSender} that sends out notifications about which feature types have
 * changed data over HTTP.
 */
public class HttpSender implements NotificationSender {

    private static final Logger LOGGER = Logging.getLogger(HttpSender.class);

    protected String url;

    protected String username;

    protected String password;

    private HttpClient getClient() {
        HttpClient.Builder builder = HttpClient.newBuilder();
        if (username == null) {
            return builder.build();
        }

        builder.authenticator(
                new Authenticator() {
                    @Override
                    public PasswordAuthentication requestPasswordAuthenticationInstance(
                            String host,
                            InetAddress addr,
                            int port,
                            String protocol,
                            String prompt,
                            String scheme,
                            URL url,
                            RequestorType reqType) {
                        return new PasswordAuthentication(username, password.toCharArray());
                    }
                });
        return builder.build();
    }

    @Override
    public void send(byte[] payload) throws Exception {
        HttpClient client = getClient();

        HttpRequest request =
                HttpRequest.newBuilder(new URI(url))
                        .POST(HttpRequest.BodyPublishers.ofByteArray(payload))
                        .build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() != 200) {
            LOGGER.warning("Could not send notification message: " + response.body());
            LOGGER.warning("Status code was " + response.statusCode());
        } else {
            LOGGER.info("Notified web client of updated feature type.");
        }
    }
}
