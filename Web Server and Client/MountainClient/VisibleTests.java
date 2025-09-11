package com.example.mountainclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Perform a range of calls to test the service:
1. Loads the initial data set
2. Checks the methods to get/read data work
3. Adds additional data and checks it's accessible - checks that adding it again does not duplicate it
4. Deletes data -
 */
public class VisibleTests {
    final static MountainClient connector = new MountainClient(SharedResources.BASE_URI);

    public static void main(final String[] args) {
        /* You need to provide this and it is often convenient to have a method to clear all stored
        data for testing - but you can just supply an empty method as it will not affect marking.
        E.g. this is OK:
        public void zapData() {}
         */
        connector.zapData();

        //LOAD INITIAL DATA - this loads data you can find in SharedResources.java
        System.out.println("**Level 1 - Uploading Data**");
        System.out.print("Load Initial Data: ");
        /*
        Not used for marking but potentially useful for debugging - first call should work and
        return whatever return code you have chosen for success; second call should return whatever
        return code you have chosen for failure (and should *not* add the mountain a 2nd time)
         */
        /*
        SharedResources.addOneMountain(connector,
        Mountain.makeMountain("Europe", "Italy", "Vesuvius", 1281, 0));
        SharedResources.addOneMountain(connector,
                Mountain.makeMountain("Europe", "Italy", "Vesuvius", 1281, 0));
        */
        final List<Mountain> data = SharedResources.addTestData();
        addData(data, 2);

        //GET TESTS - attempts some basic data reading operations
        System.out.println("\n**Level 2 - Basic Reading**");
        System.out.print("Get all Mountains: ");
        System.out.println(SharedResources.checkResult(SharedResources.GET_ALL_INITIAL,
                connector.getAll()) ? "Pass" : "Fail");
        System.out.print("Get mountains in SouthAmerica: ");
        System.out.println(SharedResources.checkResult(SharedResources.SOUTH_AMERICA_INITIAL,
                connector.getByContinent("SouthAmerica")) ? "Pass" : "Fail");
        System.out.print("Get mountains in Argentina: ");
        System.out.println(SharedResources.checkResult(SharedResources.ARGENTINA_INITIAL,
                connector.getByContinentAndCountry("SouthAmerica", "Argentina")) ? "Pass" : "Fail");

        //GET TESTS - attempts some slightly more advanced reading operations
        System.out.println("\n**Level 3 - More Advanced Reading");
        System.out.print("Get mountains over 6500m high: ");
        System.out.println(SharedResources.checkResult(SharedResources.OVER_6500,
                connector.getByAltitude(6500)) ? "Pass" : "Fail");
        System.out.print("Get a specific mountain: ");
        System.out.println(SharedResources.checkResult(SharedResources.GET_YR_WYDDFA,
                connector.getByName("Europe", "Cymru", "YrWyddfa")) ? "Pass" : "Fail");
        System.out.print("Get a specific mountain by id: ");
        connector.getByName("Africa", "Tanzania", "Kilimanjaro")
                .ifPresentOrElse(resp -> {
                    final int id = resp.getMountains().get(0).getId();
                    System.out.println(SharedResources.checkResult(SharedResources.LOOKUP_KILIMANJARO,
                            connector.getById(id)) ? "Pass" : "Fail");
                }, () -> System.out.print("Fail - Empty Optional"));
        System.out.print("Simple error test: ");
        System.out.println(SharedResources.checkResult(SharedResources.EMPTY_RESULT,
                connector.getByContinent("lemon")) ? "Pass" : "Fail");

        //ADD ADDITIONAL DATA
        System.out.println("\n**Level 4 - Add Data**");
        //Create a list of two new mountains
        Mountain penYFan = Mountain.makeMountain("Europe", "Cymru", "PenYFan", 886, 0);
        Mountain cadairIdris = Mountain.makeMountain("Europe", "Cymru","CadairIdris", 893, 0);
        List<Mountain> addList = new ArrayList<>();
        addList.add(penYFan);
        addList.add(cadairIdris);
        //Now try adding these to the list
        System.out.print("Adding new mountains: ");
        addData(addList, 2);

        System.out.print("Get all mountains in Wales: ");
        System.out.println(SharedResources.checkResult(SharedResources.WALES_AFTER_ADDING,
                connector.getByContinentAndCountry("Europe", "Cymru"))? "Pass" : "Fail");


        System.out.print("Try adding again: ");
        addData(addList, 4);
        System.out.print("Get all mountains again (should be unchanged): ");
        System.out.println(SharedResources.checkResult(SharedResources.WALES_AFTER_ADDING,
                connector.getByContinentAndCountry("Europe", "Cymru"))? "Pass" : "Fail");

        //DELETE
        System.out.println("\n**Level 5 - Deleting**");
        //Delete Antofalla
        Optional<Response> getAntofalla = connector.getByName("SouthAmerica", "Argentina", "Antofalla");
        int id = getAntofalla.get().getMountains().get(0).getId();//** see similar code and comment in UpdateData
        System.out.print("Delete mountain by id: ");
        System.out.println(SharedResources.checkResult(SharedResources.EMPTY_RESULT,
                connector.deleteMountain(id)) ? "Pass" : "Fail");
        System.out.print("Check mountains in Argentina updated: ");
        System.out.println(SharedResources.checkResult(SharedResources.UPDATED_IN_ARGENTINA,
                connector.getByContinentAndCountry("SouthAmerica","Argentina"))? "Pass" : "Fail");
    }

    /*
    Method that is used to upload a list of data by repeatedly calling the addMountain method in the connector object
    within the MountainConnector class. The parameters are:
    1. the list of data
    2. The first digit of the expected return code (e.g. if this is meant to be successful this would be 2; if it's meant
    to not be successful that would be 4
     */
    private static void addData(final List<Mountain> addList, final int responseCodeStart) {
        System.out.println(addList.stream()
                .map(m -> connector.addMountain(m))
                .filter(Optional::isPresent)
                .filter(m -> m.get().getResponse().statusCode() / 100 == responseCodeStart)
                .toList().size() == addList.size() ? "Pass" : "Fail");
    }
}