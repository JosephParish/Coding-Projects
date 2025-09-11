package com.example.mountainclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Sends requests to the Mountain Server API to perform operations such as retrieving, adding,
 * deleting, and clearing mountain data. Uses Java's HttpClient for making HTTP requests.
 *
 * @author Joseph Parish
 * @date 02/04/25
 * @version 2.5.8
 */
public class MountainClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String baseUri;

    /**
     * Constructor to initialize the MountainClient with a base URI for the server.
     *
     * @param baseUri The base URI for the server.
     */
    public MountainClient(String baseUri) {
        this.baseUri = baseUri;
    }

    /**
     * Retrieves mountains from the server based on the provided search criteria.
     *
     * @param name      The name of the mountain to search for (optional).
     * @param continent The continent the mountain is located in (optional).
     * @param country   The country the mountain is located in (optional).
     * @param height    The minimum height of the mountain (optional).
     * @param id        The ID of the mountain (optional).
     * @param type      The type of request to make (e.g., search, get all).
     * @return Optional containing a Response object with the retrieved mountains, or empty if the request fails.
     */
    public Optional<Response> get(final String name,
                                  final String continent,
                                  final String country,
                                  final Integer height,
                                  final Integer id,
                                  final int type) {
        try {
            if (type < 0 || type > 3) {
                System.out.println("Invalid type parameter");
                return Optional.empty();
            }

            if (height != null && height < 0) {
                System.out.println("Invalid height value");
                return Optional.empty();
            }

            if (id != null && id <= 0) {
                System.out.println("Invalid ID value");
                return Optional.empty();
            }

            String param = buildParam(name, continent, country, height, id, type);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(param))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return Optional.of(new Response(parseMountains(response.body()), response));
            } else if (response.statusCode() == 204
                    || response.statusCode() == 404
                    || response.statusCode() == 400) {
                return Optional.of(new Response(parseMountains(response.body()), response));
            } else {
                return Optional.empty();
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Sends a POST request to add a new mountain.
     *
     * @param mountain The mountain to be added.
     * @return Optional containing a Response object with the added mountain, or empty if the request fails.
     */
    public Optional<Response> post(Mountain mountain) {
        try {
            String param = buildParam(null, null, null, null, null, 1);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(param))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(mountain)))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                return Optional.of(new Response(parseMountains(response.body()), response));
            } else if (response.statusCode() == 400
                    || response.statusCode() == 409) {
                return Optional.of(new Response(parseMountains(response.body()), response));
            } else {
                return Optional.empty();
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Sends a DELETE request to remove a mountain by its ID.
     *
     * @param id The ID of the mountain to delete.
     * @return Optional containing a Response object indicating the result of the operation, or empty if the request fails.
     */
    public Optional<Response> delete(int id) {
        try {
            if (id <= 0) {
                System.out.println("Invalid ID value");
                return Optional.empty();
            }

            String param = buildParam(null, null, null, null, id, 2);


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(param))
                    .DELETE()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204) {
                return Optional.of(new Response(parseMountains(response.body()), response));
            } else if (response.statusCode() == 400
                    || response.statusCode() == 404) {
                return Optional.of(new Response(parseMountains(response.body()), response));
            } else {
                return Optional.empty();
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Sends a POST request to add a mountain (same as the `post` method).
     *
     * @param mountain The mountain to be added.
     * @return Optional containing a Response object with the added mountain, or empty if the request fails.
     */
    public Optional<Response> addMountain(Mountain mountain){
        return post(mountain);
    }

    /**
     * Retrieves all mountains from the server.
     *
     * @return Optional containing a Response object with all mountains, or empty if the request fails.
     */
    public Optional<Response> getAll() {
        return get(null, null, null, null, null, 3);
    }

    /**
     * Retrieves mountains by continent.
     *
     * @param continent The continent to filter mountains by.
     * @return Optional containing a Response object with the filtered mountains, or empty if the request fails.
     */
    public Optional<Response> getByContinent(String continent) {
        return get(null, continent, null, null, null, 0);
    }

    /**
     * Retrieves mountains by continent and country.
     *
     * @param continent The continent to filter mountains by.
     * @param country   The country to filter mountains by.
     * @return Optional containing a Response object with the filtered mountains, or empty if the request fails.
     */
    public Optional<Response> getByContinentAndCountry(String continent, String country) {
        return get(null, continent, country, null, null, 0);
    }

    /**
     * Retrieves mountains by minimum altitude.
     *
     * @param i The minimum altitude for filtering mountains.
     * @return Optional containing a Response object with the filtered mountains, or empty if the request fails.
     */
    public Optional<Response> getByAltitude(int i) {
        return get(null, null, null, i, null, 0);
    }

    /**
     * Retrieves mountains by name.
     *
     * @param continent The continent to filter mountains by.
     * @param country   The country to filter mountains by.
     * @param name      The name of the mountain to search for.
     * @return Optional containing a Response object with the filtered mountains, or empty if the request fails.
     */
    public Optional<Response> getByName(String continent, String country, String name) {
        return get(name, continent, country, null, null, 0);
    }

    /**
     * Retrieves a mountain by its ID.
     *
     * @param id The ID of the mountain to search for.
     * @return Optional containing a Response object with the mountain, or empty if the request fails.
     */
    public Optional<Response> getById(int id) {
        return get(null, null, null, null, id, 0);
    }

    /**
     * Deletes a mountain by its ID.
     *
     * @param id The ID of the mountain to delete.
     * @return Optional containing a Response object indicating the result of the operation, or empty if the request fails.
     */
    public Optional<Response> deleteMountain(int id){
        return delete(id);
    }

    /**
     * Clears all the mountain data on the server.
     *
     * @return Optional containing a Response object indicating the success of the operation, or empty if the request fails.
     */
    public Optional<Response> zapData() {
        try {
            String param = buildParam(null, null, null, null, null, 4);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(param))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204) {
                return Optional.of(new Response(parseMountains(response.body()), response));
            } else if (response.statusCode() == 400
                    || response.statusCode() == 404) {
                return Optional.of(new Response(parseMountains(response.body()), response));
            } else {
                return Optional.empty();
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Constructs the full URI with the given parameters.
     *
     * @param name      The name of the mountain (optional).
     * @param continent The continent the mountain is located in (optional).
     * @param country   The country the mountain is located in (optional).
     * @param height    The height of the mountain (optional).
     * @param id        The ID of the mountain (optional).
     * @param type      The type of request (e.g., search, add, delete).
     * @return The constructed URI as a string.
     */
    private String buildParam(String name, String continent, String country, Integer height, Integer id, int type) {

        String params = this.baseUri;
        if (params.endsWith("/")) {
            params = params.substring(0, params.length() - 1);
        }

        if (type == 0) {
            params += "/get";
        } else if (type == 1) {
            params += "/post";
        } else if (type == 2) {
            params += "/delete";
        } else if (type == 3) {
            params += "/getAll";
            return params;
        } else if (type == 4) {
            params += "/zap";
            return params;
        }

        if (name != null || continent != null || country != null || height != null|| id != null) {
            params += "?";
        }

        if (name != null) {
            params += "name=" + name + "&";
        }
        if (continent != null) {
            params += "continent=" + continent + "&";
        }
        if (country != null) {
            params += "country=" + country + "&";
        }
        if (height != null) {
            params += "height=" + height + "&";
        }
        if (id != null) {
            params += "id=" + id + "&";
        }

        if (params.endsWith("&")) {
            params = params.substring(0, params.length() - 1);
        }

        return params;
    }

    /**
     * Parses the response body to extract a list of mountains.
     *
     * @param body The response body as a string.
     * @return A list of Mountain objects parsed from the response body.
     */
    private ArrayList<Mountain> parseMountains(String body) {
        ArrayList<Mountain> mountains = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        if (body.isEmpty()) {
            return mountains;
        } else {
            try {
                Mountain mountain = objectMapper.readValue(body, Mountain.class);
                mountains.add(mountain);
            } catch (IOException e) {
                try {
                    mountains = objectMapper.readValue(body, new TypeReference<>() {});
                } catch (IOException b){
                    System.out.println(e.getMessage());
                }
            }
            return mountains;
        }
    }
}
