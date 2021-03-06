package ru.pflb.ipr.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.pflb.ipr.soap.GetRandomLineFromFileRequest;
import ru.pflb.ipr.soap.GetRandomLineFromFileResponse;
import ru.pflb.ipr.soap.ObjectFactory;

@Endpoint
public class FileReaderEndpoint {

    private static final String NAMESPACE_URI = "http://ipr.pflb.ru/soap";

    private final FileReaderUtils fileReaderUtils;

    @Autowired
    public FileReaderEndpoint(FileReaderUtils fileReaderUtils) {
        this.fileReaderUtils = fileReaderUtils;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRandomLineFromFileRequest")
    @ResponsePayload
    public GetRandomLineFromFileResponse getRandomNumber(@RequestPayload GetRandomLineFromFileRequest request) {
        String path = request.getPath().replace("../", "").replace("..\\", "");
        Assert.isTrue(fileReaderUtils.isPathAvailable(path), "Path is not available (" + path + ")");

        GetRandomLineFromFileResponse response = new ObjectFactory().createGetRandomLineFromFileResponse();
        response.setRandomLine(FileReaderUtils.readRandomLineFromFile(path));
        return response;
    }
}

