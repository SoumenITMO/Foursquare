package com.example.demo.Services;

import com.example.demo.Dto.VenuePictures;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FOURQBService {

    private final String clientId = "1QXTPQ42H0V5IU2OY5NKRLBG3YPEF23EXSO5LXMAIDPGJYOZ";
    private final String clientSecret = "N42BLXDGCUPO14RQHVMTPSEDMNAF41WLXC0UWCD03CHJMCVS";
    private final String venuType = "sushi";
    private final String searchPlace = "tartu";

    public Map<String, String> getVenues() throws IOException, ParseException {

        Map<String, String> venue = new HashMap<>();
        String venuesurl = "https://api.foursquare.com/v2/venues/search?near=" + searchPlace + "&query="+ venuType +
                "&client_id=" + clientId + "&client_secret="+ clientSecret +"&v=20190101";

        JSONObject parseResponse = (JSONObject) getJsonData(venuesurl).get("response");
        JSONArray venuesArray = (JSONArray) parseResponse.get("venues");

        venuesArray.forEach(venueData -> {
            JSONObject parseVenue = (JSONObject) venueData;
            venue.put(parseVenue.get("name").toString(), parseVenue.get("id").toString());
        });
        return venue;
    }

    public VenuePictures getPictures(String venueId) throws IOException, ParseException {

        List<String> pictures = new ArrayList<>();
        String searchVenueImages = "https://api.foursquare.com/v2/venues/" + venueId + "/photos?client_id=" + clientId +
                "&client_secret=" + clientSecret + "&v=20190101&group=venue&limit=10";

        VenuePictures venuePictures = new VenuePictures();
        JSONObject parseResponse = (JSONObject) getJsonData(searchVenueImages).get("response");
        JSONObject getPhotos = (JSONObject) parseResponse.get("photos");
        int count = Integer.parseInt(getPhotos.get("count").toString());
        JSONArray getItems = (JSONArray) getPhotos.get("items");

        getItems.forEach(getItem -> {
            JSONObject parseSource = (JSONObject) getItem;
            pictures.add(parseSource.get("suffix").toString().replace("/", "").trim());
        });
        venuePictures.setCount(count);
        venuePictures.setPics(pictures);
        return venuePictures;
    }

    public JSONObject getJsonData(String apiURL) throws IOException, ParseException {

        URL url = new URL(apiURL);
        URLConnection urlConn = url.openConnection();
        InputStreamReader in = new InputStreamReader(urlConn.getInputStream(),
                Charset.defaultCharset());
        BufferedReader bufferedReader = new BufferedReader(in);

        String data = bufferedReader.readLine();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
        return jsonObject;
    }
}
