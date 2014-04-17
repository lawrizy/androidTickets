package soap;

import android.util.Log;
import common.Error;
import enums.Langue;
import model.CategorieIncident;
import model.UserSessionInfo;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class WebServiceSoap implements KvmSerializable {
    //----Attibut de la classe-------
    final public static String NAMESPACE = "urn:AndroidControllerwsdl";//Nom du controlleur lié au webservice
    final public static String URL = "http://192.168.1.25/W3S-tickets/index.php/android/websys?ws=1";// url de récupération des données
    private static int timeout = 6000000;

    //--------Méthode Override------
    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }

    //-----------------------------

    /**
     * Méthode qui vérifie le login
     *
     * @param email    email de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @return statement si >0 alors login réussi sinon echec
     */
    public static int getUser(String email, String password) {
        final String METHOD_NAME = "testLogin";// nom de la méthode du webservice à appelé
        final String SOAP_ACTION = "urn:AndroidControllerwsdl#testLogin"; //url (NAMESPACE +METHODE_NAME
        int statement = 0; // réponse du serveur
        //--------------Préparation de la requête--------------
        SoapObject MethodGetLogin = new SoapObject(NAMESPACE, METHOD_NAME); //Création d'un SoapObject
        //--------------Création des propriétés à envoyé-------
        PropertyInfo emailPropriety = new PropertyInfo();//Crétation de la propriété email
        emailPropriety.setName("email");//asignation du nom de la propriété
        emailPropriety.setValue(email);//assignation de sa  valeur
        emailPropriety.setType(String.class);// Définition du type
        MethodGetLogin.addProperty(emailPropriety);// Ajout de la propriété email à la requête
        PropertyInfo motDePassePropriety = new PropertyInfo();// Création de la propriété mot de passe
        motDePassePropriety.setName("password");//asignation du nom de la propriété
        motDePassePropriety.setValue(password);//assignation de sa  valeur
        motDePassePropriety.setType(String.class);// Définition du type
        MethodGetLogin.addProperty(motDePassePropriety);// Ajout de la propriété mot de passe  à la requête

        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);//Creation de l'enveloppe soap
        envelope.setOutputSoapObject(MethodGetLogin); //Mise du SoapObject dans l'enveloppe
        envelope.dotNet = true;// Compatibilité DotNet
        final HttpTransportSE androidHttp = new HttpTransportSE(URL, timeout);//
        ArrayList<HeaderProperty> properties = new ArrayList<>();
        properties.add(new HeaderProperty("Connection", "close"));
        try {
            androidHttp.call(SOAP_ACTION, envelope, properties);//appele de l'action du webservice
            SoapObject result = (SoapObject) envelope.bodyIn; //recupoération de la reponse + cast en SoapObject
            if (result != null) {
                statement = Integer.parseInt(result.getProperty(0).toString()); //Recupère la reponse du webservice
            }
            //TODO test si null
        } catch (Exception ex) {
            ex.printStackTrace();
            statement = Error.SERVEUR_INACESSIBLE.getError(); //erreur server
        } finally {
            androidHttp.reset(); //reset
        }
        return statement;
    }


    /**
     * Méhtode qui permet la création d'un ticket
     *
     * @param id_user       l'id de l'utilisateur
     * @param sousCategorie id de la sous categorie de l'incident
     * @param id_batiment   id du bâtiment de l'incident
     * @param etage         etage
     * @param bureau        bureau de l'incident
     * @param descriptif    descriptif de l'incident
     * @return String  "OK" si la création à réussie
     */
    public static String createTicket(int id_user, int sousCategorie, int id_batiment, String etage, String bureau, String descriptif) {
        String resultTicket = "OK";
        final String METHOD_NAME = "createTicket";
        final String SOAP_ACTION = "urn:AndroidControllerwsdl#createTicket";

        SoapObject MethodCreateTicket = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo id_userPropriety = new PropertyInfo();
        id_userPropriety.setName("id_user");
        id_userPropriety.setValue(id_user);
        id_userPropriety.setType(int.class);
        MethodCreateTicket.addProperty(id_userPropriety);
        PropertyInfo sousCategoriePropriety = new PropertyInfo();
        sousCategoriePropriety.setName("sousCategorie");
        sousCategoriePropriety.setValue(sousCategorie);
        sousCategoriePropriety.setType(int.class);
        MethodCreateTicket.addProperty(sousCategoriePropriety);
        PropertyInfo id_batimentPropriety = new PropertyInfo();
        id_batimentPropriety.setName("fk_batiment");
        id_batimentPropriety.setValue(id_batiment);
        id_batimentPropriety.setType(int.class);
        MethodCreateTicket.addProperty(id_batimentPropriety);
        PropertyInfo etageProriety = new PropertyInfo();
        etageProriety.setName("etage");
        etageProriety.setValue(etage);
        etageProriety.setType(String.class);
        MethodCreateTicket.addProperty(etageProriety);
        PropertyInfo bureauProriety = new PropertyInfo();
        bureauProriety.setName("bureau");
        bureauProriety.setValue(bureau);
        bureauProriety.setType(String.class);
        MethodCreateTicket.addProperty(bureauProriety);
        PropertyInfo descriptifProriety = new PropertyInfo();
        descriptifProriety.setName("descriptif");
        descriptifProriety.setValue(descriptif);
        descriptifProriety.setType(String.class);
        MethodCreateTicket.addProperty(descriptifProriety);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(MethodCreateTicket);
        envelope.dotNet = true;
        final HttpTransportSE androidHttp = new HttpTransportSE(URL, timeout);
        ArrayList<HeaderProperty> properties = new ArrayList<>();
        properties.add(new HeaderProperty("Connection", "close"));
        try {
            androidHttp.call(SOAP_ACTION, envelope, properties);
            SoapObject result = (SoapObject) envelope.bodyIn;
            if (result != null) {
                resultTicket = result.getProperty(0).toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resultTicket = Error.SERVEUR_INACESSIBLE.name(); //erreur server
        } finally {
            androidHttp.reset();
        }
        return resultTicket;
    }

    /**
     *
     * @param id_user id de l'id de l'utilisateur
     * @return maList
     */
    public static List<String> listIdBuilding(int id_user) {
        List<String> maList = new ArrayList<>();
        final String METHOD_NAME = "getMyBuilding";
        final String SOAP_ACTION = "urn:AndroidControllerwsdl#getMyBuilding";

        SoapObject MethodeGetMyBuilding = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo id_userPropriety = new PropertyInfo();
        id_userPropriety.setName("id_user");
        id_userPropriety.setValue(id_user);
        id_userPropriety.setType(int.class);
        MethodeGetMyBuilding.addProperty(id_userPropriety);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(MethodeGetMyBuilding);
        envelope.dotNet = true;
        final HttpTransportSE androidHttp = new HttpTransportSE(URL, timeout);
        ArrayList<HeaderProperty> properties = new ArrayList<>();
        properties.add(new HeaderProperty("Connection", "close"));
        try {
            androidHttp.call(SOAP_ACTION, envelope, properties);
            SoapObject result = (SoapObject) envelope.bodyIn;
            SoapObject result1 = (SoapObject) result.getProperty(0);
            SoapObject result2 = (SoapObject) result.getProperty(0);
            if (result != null) {
                for (int c = 0; c < result1.getPropertyCount(); c++) {
                    String id = result1.getPropertyAsString(c);
                    maList.add(id);
                }
            }
            //TODO test si null
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            androidHttp.reset();
        }
        return maList;
    }


    public static List<CategorieIncident> getPieDatas(int id_batiment, Langue langue) {
        List<CategorieIncident> CategorieDash = new ArrayList<>();
        final String METHOD_NAME = "getPieDatas";
        final String SOAP_ACTION = "urn:AndroidControllerwsdl#getPieDatas";

        SoapObject methodeGetPieDatas = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo id_batimentPropriety = new PropertyInfo();
        id_batimentPropriety.setName("idBatiment");
        id_batimentPropriety.setValue(id_batiment);
        id_batimentPropriety.setType(int.class);
        methodeGetPieDatas.addProperty(id_batimentPropriety);
        PropertyInfo langueProperty = new PropertyInfo();
        langueProperty.setName("langue");
        langueProperty.setValue(langue.getID());
        langueProperty.setType(int.class);
        methodeGetPieDatas.addProperty(langueProperty);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(methodeGetPieDatas);
        envelope.dotNet = true;
        HttpTransportSE androidHttp = new HttpTransportSE(URL, timeout);
        ArrayList<HeaderProperty> properties = new ArrayList<>();
        properties.add(new HeaderProperty("Connection", "close"));
        try {
            androidHttp.call(SOAP_ACTION, envelope, properties);
            SoapObject result = (SoapObject) envelope.bodyIn;
            SoapObject result1 = (SoapObject) result.getProperty(0);
            SoapObject result2;

            if (result != null) {
                for (int c = 0; c < result1.getPropertyCount(); c++) {
                    result2 = (SoapObject) result1.getProperty(c);
                    CategorieDash.add(new CategorieIncident(result2.getProperty(0).toString(), Integer.parseInt(result2.getProperty(1).toString())));
                }
            }
            //TODO test si null
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            androidHttp.reset();
        }
        return CategorieDash;
    }

    /**
     * Méthode qui récupére les information nécessaire pour le graphique en batonnet
     *
     * @param id_batiment id du bâtiment de l'incident
     * @param langue      langue de l'utilisateur
     * @return CategorieDash liste de d'objet "CategorieIncident"
     */
    public static List<CategorieIncident> getBarsDatas(int id_batiment, Langue langue) {
        List<CategorieIncident> CategorieDash = new ArrayList<>();
        final String METHOD_NAME = "getBarsDatas";
        final String SOAP_ACTION = "urn:AndroidControllerwsdl#getBarsDatas";

        SoapObject MethodegetBarsDatas = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo id_batimentPropriety = new PropertyInfo();
        id_batimentPropriety.setName("idBatiment");
        id_batimentPropriety.setValue(id_batiment);
        id_batimentPropriety.setType(int.class);
        MethodegetBarsDatas.addProperty(id_batimentPropriety);
        PropertyInfo langueProperty = new PropertyInfo();
        langueProperty.setName("langue");
        langueProperty.setValue(langue.getID());
        langueProperty.setType(int.class);
        MethodegetBarsDatas.addProperty(langueProperty);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(MethodegetBarsDatas);
        envelope.dotNet = true;
        final HttpTransportSE androidHttp = new HttpTransportSE(URL, timeout);
        ArrayList<HeaderProperty> properties = new ArrayList<>();
        properties.add(new HeaderProperty("Connection", "close"));
        try {
            androidHttp.call(SOAP_ACTION, envelope, properties);
            SoapObject result = (SoapObject) envelope.bodyIn;
            SoapObject result1 = (SoapObject) result.getProperty(0);
            SoapObject result2;

            if (result != null) {
                for (int c = 0; c < result1.getPropertyCount(); c++) {
                    result2 = (SoapObject) result1.getProperty(c);
                    CategorieDash.add(new CategorieIncident(result2.getProperty(0).toString(), Integer.parseInt(result2.getProperty(1).toString())));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            androidHttp.reset();
        }
        return CategorieDash;
    }

    public static UserSessionInfo.UserFunction getUserFunction(int userID) {
        UserSessionInfo.UserFunction result = UserSessionInfo.UserFunction.Unknown;
        final String METHOD_NAME = "getUserPermissionLevel";
        final String SOAP_ACTION = "urn:AndroidControllerwsdl#getUserPermissionLevel";

        SoapObject requete = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo idUser = new PropertyInfo();
        idUser.setName("idUser");
        idUser.setValue(userID);
        idUser.setType(int.class);
        requete.addProperty(idUser);

        final SoapSerializationEnvelope packet = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        packet.setOutputSoapObject(requete);
        packet.dotNet = true;

        final HttpTransportSE androidHttp = new HttpTransportSE(URL, timeout);
        ArrayList<HeaderProperty> properties = new ArrayList<>();
        properties.add(new HeaderProperty("Connection", "close"));
        try {
            androidHttp.call(SOAP_ACTION, packet, properties);
            Object answer = packet.bodyIn;
            if (answer instanceof SoapObject) {
                SoapObject finalAnswer = (SoapObject) answer;
                result = UserSessionInfo.UserFunction.getUserFunctionByFunctionID(Integer.parseInt(finalAnswer.getProperty(0).toString()));
            } else if (answer instanceof SoapFault) {
                SoapFault fault = (SoapFault) answer;
                Log.i("AndroidTickets", "Fatal error: " + fault.getMessage());
            }
        } catch (EOFException ex) {
            Log.e("AndroidTickets", ex.getMessage());
        } catch (XmlPullParserException ex) {
            Log.e("AndroidTickets", ex.getMessage());
        } catch (HttpResponseException ex) {
            Log.e("AndroidTickets", ex.getMessage());
        } catch (IOException ex) {
            Log.e("AndroidTickets", ex.getMessage());
        } finally {
            androidHttp.reset();
        }
        return result;
    }
}

/**
 * Created by User on 1/04/2014.
 */
