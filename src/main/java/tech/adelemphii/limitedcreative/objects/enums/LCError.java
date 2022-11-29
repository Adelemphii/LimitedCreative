package tech.adelemphii.limitedcreative.objects.enums;

public enum LCError {

    SESSION_NULL("An error has occured with trying to obtain a NULL session.");

    private final String error;

    LCError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
