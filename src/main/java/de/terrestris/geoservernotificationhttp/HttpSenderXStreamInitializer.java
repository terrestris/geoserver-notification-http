package de.terrestris.geoservernotificationhttp;

import org.geoserver.notification.common.NotificationXStreamInitializer;

import com.thoughtworks.xstream.XStream;

/**
 * Defines the httpSender XML configuration element.
 */
public class HttpSenderXStreamInitializer implements NotificationXStreamInitializer {

    @Override
    public void init(XStream xs) {
        xs.alias("httpSender", HttpSender.class);
    }

}
