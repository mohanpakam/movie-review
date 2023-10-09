package dev.mpakam.cinecritic.exception;

public enum ExceptionEnum {
    REVIEW_ALREADY_EXISTS(409, 1, "Review already exists with review id %s"),
    USER_DOES_NOT_EXIST(404, 2, "User does not exist with id %s");
    private final int httpStatusCode;
    private final int errorCode;
    private final String errorMessage;

    public CustomException createCustomException(Object ... args){
        return CustomException.builder()
                .httpStatusCode(httpStatusCode)
                .errorCode(errorCode)
                .errorMessage(String.format(errorMessage, args)).build();
    }

    ExceptionEnum(int httpStatusCode, int errorCode, String errorMessage){
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
