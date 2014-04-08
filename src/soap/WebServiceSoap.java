package soap;

import android.util.Log;
import common.Error;
import model.CategorieIncident;
import org.ksoap2.SoapEnvelope;
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
    //    public static  String NAMESPACE = "http://localhost/W3S-tickets/index.php/";
//    public static  String METHOD_NAME = "giveLogin";
//    public static String URL_WSDL = "http://localhost/W3S-tickets/index.php/user/quote?wsdl";
//    public static String SOAP_ACTION = "http://localhost/W3S-tickets/index.php/wsdl#giveLogin";
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

    public static List<CategorieIncident> getCategories() {
        String NAMESPACE = "urn:AndroidControllerwsdl";
        String METHOD_NAME = "getCategorie";
        String URL = "http://192.168.1.25/W3S-tickets/index.php/android/websys?ws=1";
        String SOAP_ACTION = "urn:AndroidControllerwsdl#getCategorie";
        SoapObject MethodGetCategories = new SoapObject(NAMESPACE, METHOD_NAME);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(MethodGetCategories);
        envelope.dotNet = true;
        final HttpTransportSE androidHttp = new HttpTransportSE(URL);

        List<CategorieIncident> listCat = new ArrayList<CategorieIncident>();
        Log.i("Avant Try", "WSDL result");
        try {
            androidHttp.call(SOAP_ACTION, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            SoapObject result1 = (SoapObject) result.getProperty(0);
            SoapObject result2;

            if (result != null) {
                for (int c = 0; c < ((SoapObject) result.getProperty(0)).getPropertyCount(); c++) {
                    result2 = (SoapObject) result1.getProperty(c);
                    CategorieIncident cat = new CategorieIncident(Integer.parseInt(result2.getProperty(0).toString()), result2.getProperty(1).toString());
                    listCat.add(cat);
                }


            }
            //TODO test si null
        } catch (Exception ex) {
            //  System.out.println(ex.getMessage());
            ex.printStackTrace();
            //  statement = Error.SERVEUR_INACESSIBLE.getError(); //erreur server
        }
        return listCat;
    }
}



