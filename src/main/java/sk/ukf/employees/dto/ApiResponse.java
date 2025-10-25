package sk.ukf.employees.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String datetime;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data, String datetime) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.datetime = datetime;
    }

    // Pre úspešnú odpoveď
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now().toString());
    }

    // Pre error, keď nechceme posielať dáta
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now().toString());
    }

    // Pre error, keď chceme poslať aj dáta (napr. zoznam validačných chýb)
    public static <T> ApiResponse<T> error(T data, String message) {
        return new ApiResponse<>(false, message, data, LocalDateTime.now().toString());
    }

    // Gettre
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public String getDatetime() { return datetime; }
}
