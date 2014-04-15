package enums;

/**
 * Created by User on 15/04/2014.
 */
public enum Langue {
    FR(1),
    EN(2),
    NL(3);

    public int id;

    Langue(int id)
    {
        this.id = id;
    }

    public int getID()
    {
        return id;
    }
}
