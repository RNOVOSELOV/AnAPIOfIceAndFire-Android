package xyz.rnovoselov.enterprise.aniceandfire.data.errors;

/**
 * Created by roman on 05.05.17.
 */

public class ApiError extends AbstractApplicationError {

    public ApiError() {
        super("Ошибка API");
    }

    public ApiError(int code) {
        super("Ошибка API. Код ошибки: " + String.valueOf(code));
    }
}
