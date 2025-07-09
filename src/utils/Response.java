package utils;

public record Response<T>(boolean success, String msg, T data) {

    public static <U> Response<U> good (String msg, U data) {
        return new Response<>(true, msg, data);
    }

    public static <U> Response<U> bad (String msg) {
        return new Response<>(false, msg, null);
    }

}
