package com.example.mountainclient;

public class Main {
    public static void main(String[] args) {
        MountainClient connector = new MountainClient("http://localhost:8080/");

        try{
            System.out.println("part 1");
            connector.zapData();
            System.out.println("part 2");
            Mountain newMountain = Mountain.makeUploadMountain("Europe", "United Kingdom", "Crug Hywel", 451);
            System.out.println("part 3");
            connector.addMountain(newMountain);
            System.out.println("part 4");
            connector.getByContinent("Europe").ifPresent(response -> System.out.println(response + " HTTP CODE: " + response.getResponse().statusCode()));
            System.out.println("part 6");
            connector.zapData();
            System.out.println("part 7");
            connector.getByContinent("Europe").ifPresent(response -> System.out.println(response + " HTTP CODE: " + response.getResponse().statusCode()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}