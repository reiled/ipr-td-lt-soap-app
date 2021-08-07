package ru.pflb.ipr.Echo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.pflb.ipr.soap.*;

import java.lang.reflect.Field;


@Endpoint
public class EchoEndpoint {

    private static final String NAMESPACE_URI = "http://ipr.pflb.ru/soap";

    @Autowired
    public EchoEndpoint() {

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "echoRequest")
    @ResponsePayload
    public EchoResponse echo(@RequestPayload EchoRequest request) {
        Assert.notNull(request.getEchoParamName(), "[echoParamName] shoudn't be null");

        EchoResponse response = new ObjectFactory().createEchoResponse();

        try {
            Field field = request.getClass().getDeclaredField(request.getEchoParamName());
            field.setAccessible(true);
            Object value = field.get(request);
            field.setAccessible(false);
            response.setParamName(request.getEchoParamName());
            response.setValue(value.toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Assert.isTrue(false, "Couldn't find param [" + request.getEchoParamName() + "]");
        }

        return response;
    }
}
