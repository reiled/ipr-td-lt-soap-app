package ru.pflb.ipr.RandomNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ru.pflb.ipr.soap.GetRandomNumberRequest;
import ru.pflb.ipr.soap.GetRandomNumberResponse;
import ru.pflb.ipr.soap.ObjectFactory;

@Endpoint
public class RandomNumberEndpoint {

    private static final String NAMESPACE_URI = "http://ipr.pflb.ru/soap";

    @Autowired
    public RandomNumberEndpoint() {

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRandomNumberRequest")
    @ResponsePayload
    public GetRandomNumberResponse getRandomNumber(@RequestPayload GetRandomNumberRequest request) {
        GetRandomNumberResponse response = new ObjectFactory().createGetRandomNumberResponse();
        response.setRandomNumber(RandomNumberUtils.generateNextDouble(request.getLeftBound(), request.getRightBound(), request.getDecimalPlaces()));
        return response;
    }
}
