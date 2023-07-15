package com.techforb.challenge.request;

/**
 * A request to register a new user. This class is used to map the request body to an object.
 *
 * @author Leonel Zeballos
 */
public record RegisterRequest(String dni, String password, String email, String firstName, String lastName, String documentType) {
}
