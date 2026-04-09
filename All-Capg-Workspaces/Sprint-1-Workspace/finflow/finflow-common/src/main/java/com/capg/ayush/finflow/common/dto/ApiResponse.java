/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.finflow.common.dto;

/**
 * Represents a standard API response wrapper for all microservices.
 * @param <T> The type of data contained in the response
 */
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    /**
     * Constructs a new ApiResponse.
     * @param success Indicates if the operation was successful
     * @param message A descriptive message about the operation
     * @param data The payload of the response
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}