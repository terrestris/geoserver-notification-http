package de.terrestris.geoservernotificationhttp;

import com.thoughtworks.xstream.XStream;
import org.geoserver.notification.common.NotificationXStreamInitializer;

/** Defines the httpSender XML configuration element. */
public class HttpSenderXStreamInitializer implements NotificationXStreamInitializer {

    @Override
    public void init(XStream xs) {
        xs.alias("httpSender", HttpSender.class);
    }
}
