package bsu.comp152.windowwebdata;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import com.google.gson.Gson;

public class DataHandler {
    private HttpClient dataGrabber;
    private String webLocation;

    public DataHandler(String siteToSearch){
        dataGrabber = HttpClient.newHttpClient();
        webLocation = siteToSearch;
    }

    public UniversityDataType[] getData(){
        var requestBuilder = HttpRequest.newBuilder();
        var ourURI = URI.create(webLocation);
        var dataRequest = requestBuilder.uri(ourURI).build();
        HttpResponse<String> response = null;
        try {
            response = dataGrabber.send(dataRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("error connecting to server");;
        } catch (InterruptedException exception) {
            System.out.println("lost connection to server");
        }
        if(response == null){
            System.out.println("unable to get data from network... giving up");
            System.exit(-1);
        }
        var responseBody = response.body();
        var jsonParser = new Gson();
        var UniData = jsonParser.fromJson(responseBody, UniversityDataType[].class);
        return UniData;
    }

    class UniversityDataType{
        String alpha_two_code;
        ArrayList<String>web_pages;
        String name;
        String country;
        ArrayList<String>domains;

        @Override
        public String toString(){
            return name;
        }
    }
}
