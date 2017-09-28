package pk.merite.webapp.info;

import java.util.List;

public class ApiResponseInfo<T> {

    private List<ErrorInfo> erros;
    private T data;

    public ApiResponseInfo(List<ErrorInfo> errors, T data) {
        this.erros = errors;
        this.data = data;
    }

    public List<ErrorInfo> getErros() {
        return erros;
    }

    public T getData() {
        return data;
    }

}
