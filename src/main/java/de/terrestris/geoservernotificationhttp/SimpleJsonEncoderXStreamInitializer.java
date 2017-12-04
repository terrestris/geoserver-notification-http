package de.terrestris.geoservernotificationhttp;

import org.geoserver.notification.common.NotificationXStreamInitializer;

import com.thoughtworks.xstream.XStream;

/**
 * Defines the simpleJsonEncoder XML configuration element.
 */
public class SimpleJsonEncoderXStreamInitializer implements NotificationXStreamInitializer{

    @Override
    public void init(XStream xs) {
        xs.alias("simpleJsonEncoder", SimpleJsonEncoder.class);
    }

}
