package model;

/**
 * Created by User on 11/04/2014.
 */

/**
 * Cette classe stocke toutes les informations utiles pour pouvoir reconnaître l'utilisateur et ainsi l'identifier
 * tout au long de la durée de l'application. (ex: envoi d'emails, recherche DB par rapport au numéro d'utilisateur, ...)
 */
public class UserSessionInfo
{
    public enum UserFunction
    {
        Unknown(0, "Unknown"),
        User(1, "User"),
        Admin(2, "Admin"),
        Root(3, "Root"),
        Locataire(4, "Locataire");

        public final int functionNo;
        public final String functionName;
        public String idLangue;

        UserFunction(int functionNo, String functionName)
        {
            this.functionNo = functionNo;
            this.functionName = functionName;
        }

        public static UserFunction getUserFunctionByFunctionID(int id)
        {
            switch(id)
            {
                case 1:
                    return User;
                case 2:
                    return Admin;
                case 3:
                    return Root;
                case 4:
                    return Locataire;
                default:
                    return Unknown;
            }
        }
    }

    public static int USER_ID;
    public static String USER_EMAIL;
    public static String USER_NAME;
    public static UserFunction USER_FUNCTION;
}
