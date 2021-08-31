package de.terrestris.geoservernotificationhttp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
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

    private HttpClient getClient() throws MalformedURLException {
        HttpClient client = new HttpClient();
        if (username == null) {
            return client;
        }

        List<String> authPrefs = new ArrayList<String>();
        authPrefs.add(AuthPolicy.BASIC);
        client.getParams().setParameter(AuthPolicy.AUTH_SCHEME_PRIORITY, authPrefs);
        client.getParams().setAuthenticationPreemptive(true);
        URL url = new URL(this.url);

        Credentials defaultcreds = new UsernamePasswordCredentials(username, password);
        client.getState()
                .setCredentials(
                        new AuthScope(url.getHost(), url.getPort(), AuthScope.ANY_REALM),
                        defaultcreds);
        return client;
    }

    @Override
    public void send(byte[] payload) throws Exception {
        HttpClient client = getClient();

        PostMethod post = new PostMethod(url);
        post.setRequestEntity(new ByteArrayRequestEntity(payload));
        int responseCode = client.executeMethod(post);
        if (responseCode != 200) {
            String response = new String(post.getResponseBody(), "UTF-8");
            LOGGER.warning("Could not send notification message: " + response);
            LOGGER.warning("Status code was " + responseCode);
        } else {
            LOGGER.info("Notified web client of updated feature type.");
        }
    }
}
