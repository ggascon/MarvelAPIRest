package com.germangascon.marvelapirest.core;

import com.germangascon.marvelapirest.util.MD5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * You must add your domain as authorized referrers in order to work properly.
 * By default only developer.marvel.com is allowed.
 * see: https://developer.marvel.com/account for more details
 */
public class MarvelAPIConnect {
    /** We are using clear http connection */
    private final static String MARVEL_API_BASE_ENDPOINT = "http://gateway.marvel.com";
    /** API v1 default path */
    private final static String MARVEL_INITIAL_API_PATH = "/v1/public/";

    // Params required by Marvel API
    private final static String MARVEL_APIKEY_PARAM = "apikey";
    private final static String MARVEL_TIMESTAMP_PARAM = "ts";
    private final static String MARVEL_HASH_PARAM = "hash";

    /** Not important, only to provide user agent information */
    private final static String USER_AGENT = "Mozilla/5.0";

    // Api keys
    private final String publicApiKey;
    private final String privateApiKey;

    public MarvelAPIConnect(String publicApiKey, String privateApiKey) {
        this.publicApiKey = publicApiKey;
        this.privateApiKey = privateApiKey;
    }

    public String get(MarvelAPIEntityType primaryEntity, int id, MarvelAPIEntityType secondaryEntity) {
        StringBuilder builder = new StringBuilder();
        Integer idEntity = id >= 0 ? id : null;
        URL url = null;
        try {
            url = getUrl(primaryEntity, idEntity, secondaryEntity);
            HttpURLConnection con  = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                while ((line = in.readLine()) != null) {
                    builder.append(line);
                }
                in.close();
            }
        } catch (MalformedURLException mue){
            System.out.println("Invalid URL");
        } catch (IOException ioe){
            System.out.println("Cannot open " + url);
        }
        return builder.toString();
    }

    public String get(MarvelAPIEntityType primaryEntity, int id) {
        return get(primaryEntity, id, null);
    }

    public String get(MarvelAPIEntityType primaryEntity) {
        return get(primaryEntity, -1, null);
    }

    private URL getUrl(MarvelAPIEntityType primaryEntity, Integer id,
                       MarvelAPIEntityType secondaryEntity) throws MalformedURLException {
        String spec = MARVEL_API_BASE_ENDPOINT + MARVEL_INITIAL_API_PATH + primaryEntity;
        if(id != null)
            spec += "/" + id;
        if(secondaryEntity != null)
            spec += "/" + secondaryEntity;
        spec += "?" + MARVEL_APIKEY_PARAM + "=" + publicApiKey;
        long ts = System.currentTimeMillis();
        spec += "&" + MARVEL_TIMESTAMP_PARAM + "=" + ts;
        /*
         * The order of the md5 digest input value must be:
         * ts + private key + public key
         * See: https://developer.marvel.com/documentation/authorization for more details
         */
        String hash = MD5.getMd5(ts + privateApiKey + publicApiKey);
        spec += "&" + MARVEL_HASH_PARAM + "=" + hash;
        return new URL(spec);
    }
}
