package soap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Hashtable;

/**
 * Created by User on 1/04/2014.
 */


public class Response implements KvmSerializable {
    public String NAMESPACE = "localhost//W3S-tickets/index.php/user/";
    public String METHOD_NAME = "getLogin";
    public String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
    public String SOAP_ACTION = "localhost//W3S-tickets/index.php/user/quote";

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

    public void getUser(String email, String password) {
        SoapObject MethodeGetLogin;
        MethodeGetLogin = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo emailPropriety = new PropertyInfo();
        emailPropriety.setName("email");
        emailPropriety.setValue(email);
        emailPropriety.setType(String.class);
        MethodeGetLogin.addProperty(emailPropriety);
        PropertyInfo motDePassePropriety = new PropertyInfo();
        motDePassePropriety.setName("motDePasse");
        motDePassePropriety.setValue(password);
        motDePassePropriety.setType(String.class);
        MethodeGetLogin.addProperty(motDePassePropriety);
    }

}
