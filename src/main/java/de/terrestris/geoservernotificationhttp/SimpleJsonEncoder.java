package de.terrestris.geoservernotificationhttp;

import org.geoserver.catalog.impl.FeatureTypeInfoImpl;
import org.geoserver.notification.common.Notification;
import org.geoserver.notification.common.NotificationEncoder;
import org.geotools.api.feature.type.FeatureType;
import org.geotools.api.feature.type.Name;

/**
 * A simple {@link NotificationEncoder} that encodes an updated feature type's namespace and
 * localname as JSON: {"namespace": "http://namespace.org/namespace", "localname": "myfeaturetype"}
 */
public class SimpleJsonEncoder implements NotificationEncoder {

    @Override
    public byte[] encode(Notification notification) throws Exception {
        FeatureTypeInfoImpl featureType = (FeatureTypeInfoImpl) notification.getObject();
        FeatureType ft = featureType.getFeatureType();
        Name name = ft.getName();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"namespace\":\"").append(name.getNamespaceURI());
        sb.append("\",\"localname\":\"").append(name.getLocalPart()).append("\"}");
        return sb.toString().getBytes("UTF-8");
    }
}
