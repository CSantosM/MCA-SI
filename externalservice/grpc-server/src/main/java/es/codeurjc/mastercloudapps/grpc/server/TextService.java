package es.codeurjc.mastercloudapps.grpc.server;

import es.codeurjc.mastercloudapps.grpc.Request;
import es.codeurjc.mastercloudapps.grpc.Response;
import es.codeurjc.mastercloudapps.grpc.ServiceGrpc;
import io.grpc.stub.StreamObserver;

import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class TextService extends ServiceGrpc.ServiceImplBase {

    @Override
    public void toUpperCase(final Request request, final StreamObserver<Response> responseObserver) {

        System.out.println("I am the SERVER:\n");
        System.out.println("Request received from client:\n" + request);

        String textReceived = request.getText();
        String upperText = textReceived.toUpperCase();

        final Response response = Response.newBuilder().setResult(upperText).build();

        System.out.println("Sending text to client: " + upperText);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
