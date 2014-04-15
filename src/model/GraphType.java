package model;

/**
 * Created by User on 15/04/2014.
 */
public class GraphType {
    private static int id_giver = 1;
    private final int id;
    private final String typeName;

    public GraphType(String typeName)
    {
        this.id = id_giver;
        ++id_giver;
        this.typeName = typeName;
    }

    public int getID()
    {
        return id;
    }

    public String getTypeName()
    {
        return typeName;
    }

    @Override
    public String toString()
    {
        return typeName;
    }
}
