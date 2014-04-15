package soap;

import android.util.Log;
import common.Error;
import model.UserSessionInfo;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by User on 1/04/2014.
 */


public class WebServiceSoap implements KvmSerializable {

    public static String NAMESPACE = "urn:AndroidControllerwsdl";
    public static String METHOD_NAME = "testLogin";
    public static String URL = "http://192.168.1.25/W3S-tickets/index.php/android/websys?ws=1";
    public static String SOAP_ACTION = "urn:AndroidControllerwsdl#testLogin";

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

    public static int getUser(String email, String password) {
        int statement = 0; // réponse du serveur
        SoapObject MethodGetLogin = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo emailPropriety = new PropertyInfo();
        emailPropriety.setName("email");
        emailPropriety.setValue(email);
        emailPropriety.setType(String.class);
        MethodGetLogin.addProperty(emailPropriety);
        PropertyInfo motDePassePropriety = new PropertyInfo();
        motDePassePropriety.setName("password");
        motDePassePropriety.setValue(password);
        motDePassePropriety.setType(String.class);
        MethodGetLogin.addProperty(motDePassePropriety);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(MethodGetLogin);
        envelope.dotNet = true;
        final HttpTransportSE androidHttp = new HttpTransportSE(URL);

        Log.i("Avant Try", "WSDL result");
        try {
            androidHttp.call(SOAP_ACTION, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            if (result != null) {
                statement = Integer.parseInt(result.getProperty(0).toString());
            }
            //TODO test si null
        } catch (Exception ex) {
            //  System.out.println(ex.getMessage());
            ex.printStackTrace();
            statement = Error.SERVEUR_INACESSIBLE.getError(); //erreur server
        }
        return statement;
    }

    public static String createTicket(int id_user, int sousCategorie, int id_batiment,String etage,String bureau,String descriptif) {
      String resultTicket = "OK";
        String NAMESPACE = "urn:AndroidControllerwsdl";
        String METHOD_NAME = "createTicket";
        String URL ="http://192.168.1.25/W3S-tickets/index.php/android/websys?ws=1";
        String SOAP_ACTION = "urn:AndroidControllerwsdl#createTicket";

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
        MethodCreateTicket.addProperty(etageProriety);
        PropertyInfo descriptifProriety = new PropertyInfo();
        descriptifProriety.setName("descriptif");
        descriptifProriety.setValue(bureau);
        descriptifProriety.setType(String.class);
        MethodCreateTicket.addProperty(descriptifProriety);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(MethodCreateTicket);
        envelope.dotNet = true;
        final HttpTransportSE androidHttp = new HttpTransportSE(URL);
        try {
            androidHttp.call(SOAP_ACTION, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            if (result != null) {
                resultTicket = result.getProperty(0).toString();
            }
        } catch (Exception ex) {
            //  System.out.println(ex.getMessage());
            ex.printStackTrace();
            resultTicket = Error.SERVEUR_INACESSIBLE.name(); //erreur server
        }
        return resultTicket;
    }
    public static List<String> listIdBuilding(int id_user)
    {
        List<String>maList = new ArrayList<>();
        String NAMESPACE = "urn:AndroidControllerwsdl";
        String METHOD_NAME = "getMyBuilding";
        String URL ="http://192.168.1.25/W3S-tickets/index.php/android/websys?ws=1";
        String SOAP_ACTION = "urn:AndroidControllerwsdl#getMyBuilding";

        SoapObject MethodeGetMyBuilding = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo id_userPropriety = new PropertyInfo();
        id_userPropriety.setName("id_user");
        id_userPropriety.setValue(id_user);
        id_userPropriety.setType(int.class);
        MethodeGetMyBuilding.addProperty(id_userPropriety);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(MethodeGetMyBuilding);
        envelope.dotNet = true;
        final HttpTransportSE androidHttp = new HttpTransportSE(URL);
        try {
            androidHttp.call(SOAP_ACTION, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            SoapObject result1=(SoapObject)result.getProperty(0);
            SoapObject result2=(SoapObject)result.getProperty(0);
            if (result != null) {
                    for(int c=0;c<result1.getPropertyCount();c++)
                    {
                        String id=result1.getPropertyAsString(c);
                            maList.add( id);
                    }
            }
            //TODO test si null
        } catch (Exception ex) {
            //  System.out.println(ex.getMessage());
            ex.printStackTrace();
            //  resultTicket = Error.SERVEUR_INACESSIBLE.getError(); //erreur server
        }


        return maList;
    }

    public static void getBarsDatas(int id_batiment,int langue)
    {
        String NAMESPACE = "urn:AndroidControllerwsdl";
        String METHOD_NAME = "getBarsDatas";
        String URL ="http://192.168.1.25/W3S-tickets/index.php/android/websys?ws=1";
        String SOAP_ACTION = "urn:AndroidControllerwsdl#getBarsDatas";

        SoapObject MethodegetBarsDatas= new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo id_batimentPropriety = new PropertyInfo();
        id_batimentPropriety.setName("idBatiment");
        id_batimentPropriety.setValue(id_batiment);
        id_batimentPropriety.setType(int.class);
        MethodegetBarsDatas.addProperty(id_batimentPropriety);
        PropertyInfo langueProperty = new PropertyInfo();
        langueProperty.setName("langue");
        langueProperty.setValue(langue);
        langueProperty.setType(int.class);
        MethodegetBarsDatas.addProperty(langueProperty);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(MethodegetBarsDatas);
        envelope.dotNet = true;
        final HttpTransportSE androidHttp = new HttpTransportSE(URL);
        try {
            androidHttp.call(SOAP_ACTION, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            SoapObject result1=(SoapObject)result.getProperty(0);
            SoapObject result2=(SoapObject)result.getProperty(0);
            if (result != null) {
                for(int c=0;c<result1.getPropertyCount();c++)
                {
                 //
                }
            }
            //TODO test si null
        } catch (Exception ex) {
            //  System.out.println(ex.getMessage());
            ex.printStackTrace();
            //  resultTicket = Error.SERVEUR_INACESSIBLE.getError(); //erreur server
        }
    }

    public static UserSessionInfo.UserFunction getUserFunction(int userID) {
        UserSessionInfo.UserFunction result = UserSessionInfo.UserFunction.Unknown;
        String NAMESPACE = "urn:AndroidControllerwsdl";
        String METHOD_NAME = "getUserPermissionLevel";
        String URL = "http://192.168.1.25/W3S-tickets/index.php/android/websys?ws=1";
        String SOAP_ACTION = "urn:AndroidControllerwsdl#getUserPermissionLevel";

        SoapObject requete = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo idUser = new PropertyInfo();
        idUser.setName("idUser");
        idUser.setValue(userID);
        idUser.setType(int.class);
        requete.addProperty(idUser);

        final SoapSerializationEnvelope packet = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        packet.setOutputSoapObject(requete);
        packet.dotNet = true;

        final HttpTransportSE androidHttp = new HttpTransportSE(URL);

        try {
            androidHttp.call(SOAP_ACTION, packet);
            Object answer = packet.bodyIn;
            if (answer instanceof SoapObject) {
                SoapObject finalAnswer = (SoapObject) answer;
                result = UserSessionInfo.UserFunction.getUserFunctionByFunctionID(Integer.parseInt(finalAnswer.getProperty(0).toString()));
            } else if(answer instanceof SoapFault)
            {
                SoapFault fault = (SoapFault) answer;
                Log.i("AndroidTickets", "Fatal error: " + fault.getMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
