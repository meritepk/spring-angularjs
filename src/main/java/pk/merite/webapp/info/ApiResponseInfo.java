package pk.merite.webapp.info;

public class ApiResponseInfo<T> {

    private T data;

    public ApiResponseInfo(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
