package model;

import activities.Dashboard;

/**
 * Created by User on 15/04/2014.
 */
public class GraphType {
    private static int id_giver = 1;
    private final int id;
    private final Dashboard.DrawMode mode;

    public GraphType(Dashboard.DrawMode mode)
    {
        this.id = id_giver;
        ++id_giver;
        this.mode = mode;
    }

    public int getID()
    {
        return id;
    }

    public Dashboard.DrawMode getMode()
    {
        return mode;
    }

    @Override
    public String toString()
    {
        return mode.getName();
    }
}
