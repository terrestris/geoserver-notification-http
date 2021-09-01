package de.terrestris.geoservernotificationhttp;

import com.thoughtworks.xstream.XStream;
import org.geoserver.notification.common.NotificationXStreamInitializer;

/** Defines the simpleJsonEncoder XML configuration element. */
public class SimpleJsonEncoderXStreamInitializer implements NotificationXStreamInitializer {

    @Override
    public void init(XStream xs) {
        xs.alias("simpleJsonEncoder", SimpleJsonEncoder.class);
    }
}
