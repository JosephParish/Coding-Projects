package com.example.mountainserver;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Handles requests related to mountain data. This includes operations such as adding a mountain, searching for mountains,
 * deleting a mountain, and clearing all mountain data.
 * The class uses a ReentrantReadWriteLock for thread-safe access to the list of mountains.
 *
 * @author Joseph Parish
 * @date 02/04/25
 * @version 2.6.12
 */
@RestController
public class MountainConnector {

    private List<Mountain> mountainList = new ArrayList<>();
    private int currentId = 1;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Adds a new mountain to the list.
     *
     * @param mountain The mountain to be added.
     * @return ResponseEntity with status indicating whether the operation was successful.
     */
    @PostMapping(value="/post", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> post(@RequestBody final Mountain mountain) {
        try {
            if (!(areValidStrings(mountain.getName(), mountain.getContinent(), mountain.getCountry()) && mountain.getHeight() >= 0)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            lock.writeLock().lock();

            for(Mountain elem: mountainList) {
                if (elem.getName().equalsIgnoreCase(mountain.getName()) &&
                        elem.getContinent().equalsIgnoreCase(mountain.getContinent()) &&
                        elem.getCountry().equalsIgnoreCase(mountain.getCountry()) &&
                        elem.getHeight() == mountain.getHeight()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            }

            mountain.setId(currentId++);
            mountainList.add(mountain);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Retrieves all mountains from the list.
     *
     * @return ResponseEntity with a list of all mountains.
     */
    @GetMapping(value="/getAll", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Mountain>> getAll() {
        lock.readLock().lock();
        try {
            if (mountainList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            return ResponseEntity.status(HttpStatus.OK).body(mountainList);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Searches for mountains that match the given parameters.
     *
     * @param name      The name of the mountain to search for (optional).
     * @param continent The continent the mountain is located in (optional).
     * @param country   The country the mountain is located in (optional).
     * @param height    The minimum height of the mountain (optional).
     * @param id        The ID of the mountain (optional).
     * @return ResponseEntity with a list of matching mountains.
     */
    @GetMapping(value = {"/get"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Mountain>> get(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String continent,
            @RequestParam(required = false) final String country,
            @RequestParam(required = false) final Integer height,
            @RequestParam(required = false) final Integer id) {

        try {
            if ((height != null && height < 0) || (id != null && id < 0)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            lock.readLock().lock();
            List<Mountain> filteredMountains = new ArrayList<>();

            for (Mountain elem : mountainList) {
                boolean matches = true;

                if (id != null && elem.getId() != id) {
                    matches = false;
                }
                if (height != null && elem.getHeight() < height) {
                    matches = false;
                }
                if (name != null && !elem.getName().equals(name)) {
                    matches = false;
                }
                if (country != null && !elem.getCountry().equals(country)) {
                    matches = false;
                }
                if (continent != null && !elem.getContinent().equals(continent)) {
                    matches = false;
                }

                if (matches) {
                    filteredMountains.add(elem);
                }
            }

            if (filteredMountains.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(filteredMountains);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Deletes the mountain with the specified ID.
     *
     * @param id The ID of the mountain to delete.
     * @return ResponseEntity with the result of the deletion.
     */
    @DeleteMapping(value="/delete", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@RequestParam final Integer id) {
        try {
            if (!(id > 0)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            lock.writeLock().lock();

            for (Mountain elem : mountainList) {
                if (elem.getId() == id) {
                    mountainList.remove(elem);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Clears all the data in the mountain list and resets the current ID.
     *
     * @return ResponseEntity indicating the success of the operation.
     */
    @PostMapping(value = "/zap", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> zap() {
        lock.writeLock().lock();
        try {
            currentId = 1;
            mountainList.clear();
            return ResponseEntity.status(HttpStatus.OK).build();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Checks if all provided strings are non-null and non-empty after trimming.
     *
     * @param strings The strings to validate.
     * @return True if all strings are valid, otherwise false.
     */
    private boolean areValidStrings(String... strings) {
        for (String str : strings) {
            if (str == null || str.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
