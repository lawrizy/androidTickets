package common;

/**
 * Created by User on 3/04/2014.
 */
public enum Error {

    SERVEUR_INACESSIBLE(-3),
    DB_INDISPONIBLE(-2),
    ERROR_USER(-1);


    private int error;

    Error(int i) {
    error=i;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
