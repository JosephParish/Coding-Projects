package com.example.mountainclient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/*
This class contains a number of constants and methods that are used by the test code
 */
public class SharedResources {

    //URL of server
    public static final String BASE_URI = "http://localhost:8080/";

    //Result strings for comparison
    static final String EMPTY_RESULT = ""; //We make this explicit to be clear it should be empty not e.g. null
    static final String GET_ALL_INITIAL = """
            Aconcagua is 6961m high. It is in Argentina, which is in SouthAmerica.
            Annapurna is 8091m high. It is in Nepal, which is in Asia.
            Antofalla is 6409m high. It is in Argentina, which is in SouthAmerica.
            Huascarán is 6768m high. It is in Peru, which is in SouthAmerica.
            Incahuasi is 6621m high. It is in Argentina, which is in SouthAmerica.
            Kilimanjaro is 5985m high. It is in Tanzania, which is in Africa.
            YrWyddfa is 1085m high. It is in Cymru, which is in Europe.
            """;
    static final String SOUTH_AMERICA_INITIAL = """
            Aconcagua is 6961m high. It is in Argentina, which is in SouthAmerica.
            Antofalla is 6409m high. It is in Argentina, which is in SouthAmerica.
            Huascarán is 6768m high. It is in Peru, which is in SouthAmerica.
            Incahuasi is 6621m high. It is in Argentina, which is in SouthAmerica.
            """;
    static final String ARGENTINA_INITIAL = """
            Aconcagua is 6961m high. It is in Argentina, which is in SouthAmerica.
            Antofalla is 6409m high. It is in Argentina, which is in SouthAmerica.
            Incahuasi is 6621m high. It is in Argentina, which is in SouthAmerica.
            """;
    static final String OVER_6500 = """
            Aconcagua is 6961m high. It is in Argentina, which is in SouthAmerica.
            Annapurna is 8091m high. It is in Nepal, which is in Asia.
            Huascarán is 6768m high. It is in Peru, which is in SouthAmerica.
            Incahuasi is 6621m high. It is in Argentina, which is in SouthAmerica.
            """;
    static final String GET_YR_WYDDFA = """
            YrWyddfa is 1085m high. It is in Cymru, which is in Europe.
            """;
    static final String LOOKUP_KILIMANJARO = """
            Kilimanjaro is 5985m high. It is in Tanzania, which is in Africa.
            """;
    static final String WALES_AFTER_ADDING = """
            CadairIdris is 893m high. It is in Cymru, which is in Europe.
            PenYFan is 886m high. It is in Cymru, which is in Europe.
            YrWyddfa is 1085m high. It is in Cymru, which is in Europe.
            """;

    static final String UPDATED_IN_NEPAL = """
            Annapurna is in the Annapurna range in Nepal. It is in the Northern hemisphere and is 8091m high.
            Makalu is in the Himalayas range in Nepal. It is in the Northern hemisphere and is 8485m high.
            """;

    static final String UPDATED_IN_ARGENTINA = """
            Aconcagua is 6961m high. It is in Argentina, which is in SouthAmerica.
            Incahuasi is 6621m high. It is in Argentina, which is in SouthAmerica.
            """;

    /*
    You can use this for debugging - it will output the path of the request, the status code of the response,
    the content type of the response, and the request's query parameters. You can edit it to access other
    parts of the response or the request. Call it e.g. like this:
    SharedResources.outputResult(connector.getByContinent("Africa"));
     */
    static void outputResult(final Optional<Response> response) {
        System.out.println("Result Returned:");
        response.ifPresentOrElse(item -> {
            for (final Mountain mountain : item.getMountains()) {
                System.out.println(mountain);
            }
            System.out.println("Request path: " + item.getResponse().request().uri().getPath()
                    + ", Response status code: " + item.getResponse().statusCode()
                    + ", Response content type: " + item.getResponse().headers().firstValue("content-type")
                    + ", Request query parameters: " + item.getResponse().request().uri().getQuery());
            //If it prints the message below, then the result is an optional with empty content.
        }, () -> System.out.println("Error Response (empty optional)"));
    }

    /*
    Used to check that the result (and *only* the result - not e.g. the return code, content type or parameters) is
    correct. Checks to see if the appropriate parameter passing methods are used; the appropriate return codes are
    used; and the returned data types are best practice are in the hidden tests. Note that to simplify things when
    comparing expected and actual output strings, this method strips any whitespace from the start and end - this is
    mainly to make sure you don't have to worry about having, or not having, a newline at the end of strings.
     */
    public static boolean checkResult(final String expectedOutput, final Optional<Response> response) {
        if (response.isPresent()) {
            final String resStr = arrayListToString(response.get().getMountains()).trim();
            final boolean result = expectedOutput.trim().equals(resStr);
            //Can be handy for debugging:
            if (!result) {
                System.out.println("Expected:");
                System.out.println(expectedOutput);
                outputResult(response);
            }
            return result;
        } else {
            return false;
        }
    }

    /*
    This builds a list of initial test data
     */
    public static ArrayList<Mountain> addTestData() {
        final Mountain yrWyddfa = Mountain.makeUploadMountain("Europe",
                "Cymru", "YrWyddfa", 1085);
        final Mountain aconcagua = Mountain.makeUploadMountain("SouthAmerica", "Argentina",
                "Aconcagua", 6961);
        final Mountain annapurna = Mountain.makeUploadMountain("Asia", "Nepal",
                "Annapurna", 8091);
        final Mountain incahuasi = Mountain.makeUploadMountain("SouthAmerica", "Argentina",
                "Incahuasi", 6621);
        final Mountain huascaran = Mountain.makeUploadMountain("SouthAmerica", "Peru", "Huascarán",
                6768);
        final Mountain antofalla = Mountain.makeUploadMountain("SouthAmerica", "Argentina",
                "Antofalla", 6409);
        final Mountain kilamanjaro = Mountain.makeUploadMountain("Africa", "Tanzania", "Kilimanjaro",
                5985);//This is deliberately wrong - it should be 5895
        final ArrayList<Mountain> addList = new ArrayList<>();
        addList.add(yrWyddfa);
        addList.add(aconcagua);
        addList.add(annapurna);
        addList.add(incahuasi);
        addList.add(huascaran);
        addList.add(antofalla);
        addList.add(kilamanjaro);
        return addList;
    }

    /*
    Convert a list of mountains to a string - will crash if the list is null (so always ensure you return an
    empty list when you don't want to return data).
     */
    private static String arrayListToString(final List<Mountain> mountainList) {
        return mountainList.stream()
                .sorted(Comparator.comparing(Mountain::toString))
                .map(Mountain::toString)
                .collect(Collectors.joining("\n"));
    }

    /*
    Not used by the test code but you can uncomment calls to it at the start of VisibleTests.java, and you can write
    your own tests using it - it may be helpful for debugging because it gives more information on what is returned
    when you add a mountain.
     */
    //public static void addOneMountain(final MountainConnector connector, Mountain mountain) {
    //    final Optional<Response> response = connector.addMountain(mountain);
    //    if (response.isEmpty()) {
    //        System.out.printf("Adding mountain with name %s returned empty Optional.\n", mountain.getName());
    //    } else if (response.get().getResponse().statusCode() != 200) {
    //        System.out.printf("Adding mountain with name %s returned invalid response code %d.\n",
    //                mountain.getName(), response.get().getResponse().statusCode());
    //    } else {
    //        System.out.printf("Adding mountain with name %s returned successfully.\n", mountain.getName());
    //    }
    //}
}
