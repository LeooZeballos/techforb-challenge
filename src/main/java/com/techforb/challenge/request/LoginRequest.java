package com.techforb.challenge.request;

/**
 * A request to login. This class is used to map the request body to an object.
 *
 * @author Leonel Zeballos
 */
public record LoginRequest(String dni, String password) {
}
