package com.germangascon.marvelapirest;
import com.germangascon.marvelapirest.core.MarvelAPIConnect;
import com.germangascon.marvelapirest.core.MarvelAPIEntityType;

public class Main {
    /**
     * API keys
     * You must provide your public and private Api Keys
     * see: https://developer.marvel.com/account for more details
     */
    private final static String MARVEL_PUBLIC_APIKEY = "YOUR_PUBLIC_APIKEY";
    private final static String MARVEL_PRIVATE_APIKEY = "YOUR_PRIVATE_APIKEY";

    public static void main(String[] args) {
        String response;
        MarvelAPIConnect marvelAPIConnect = new MarvelAPIConnect(
                MARVEL_PUBLIC_APIKEY,
                MARVEL_PRIVATE_APIKEY);

        // Test primary entity
        response = marvelAPIConnect.get(MarvelAPIEntityType.CHARACTERS);
        System.out.printf("Response: %s\n", response);

        // Test primary entity with id
        // response = marvelAPIConnect.get(MarvelAPIEntityType.CHARACTERS, 1011334);
        // System.out.printf("Response: %s\n", response);

        // Test primary entity with id and secondary entity
        // response = marvelAPIConnect.get(MarvelAPIEntityType.CHARACTERS, 1011334, MarvelAPIEntityType.COMICS);
        // System.out.printf("Response: %s\n", response);
    }
}
