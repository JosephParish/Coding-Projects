package com.example.mountainclient;

import java.net.http.HttpResponse;
import java.util.List;

/**
 * The response that must be returned by your client code (in an Optional) - it consists of the data returned by the
 * service (if any) and the "raw" response object - which contains a reference to the original request you sent. This is
 * so my test code can automatically detect various properties of your calls and responses.
 *
 * Note that for simplicity this fixes the type of data in the HttpResponse object as a String - which means
 * that your client code must ALWAYS use String. The examples in the notes either use String or Void - for cases where
 * no data is returned. But you cannot do this here. It's mildly annoying but doesn't stop anything working
 */
public class Response {
    private final List<Mountain> mountains;
    private final HttpResponse<String> response;

    /**
     * Generate a Response object consisting of the list of Mountain objects returned by the server - which may be
     * empty if e.g. none were found, or the operation returns no data (e.g. POST), and the "raw" response the server
     * returned (which contains a reference to the request object
     * @param mountains The (possibly empty) list of Mountain objects
     * @param response The "raw" response object
     */
    public Response(final List<Mountain> mountains, final HttpResponse<String> response) {
        this.mountains = mountains;
        this.response = response;
    }

    /**
     * Get the possibly empty list of Mountain objects
     * @return the List of Mountain objects
     */
    public List<Mountain> getMountains() {
        return mountains;
    }

    /**
     * Get the response returned by the server - note that response.request() is the orginal request so this contains
     * details of both the request made and the response returned.
     * @return the HttpResponse object created from the response sent by the server
     */
    public HttpResponse<String> getResponse() {
        return response;
    }
}
