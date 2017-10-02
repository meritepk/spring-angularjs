package pk.merite.webapp.info;

public class ErrorInfo {

    private String code;
    private String message;

    public ErrorInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
