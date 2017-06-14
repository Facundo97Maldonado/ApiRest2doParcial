package ApiRest2.Entities;

import junit.framework.TestCase;
import org.junit.Before;

import java.sql.Timestamp;
import java.text.ParseException;

/**
 * Created by Facundo on 13/06/2017.
 */
public class MensajeTest extends TestCase {
    Timestamp fehca;
    Mensaje mensaje;
    Usuario remitente;
    Usuario recipiente;

    @Before
    public void setUp() throws ParseException{
        remitente = new Usuario();
        recipiente = new Usuario();
        mensaje = new Mensaje();
        mensaje.setRemitente(remitente);
        mensaje.setRecipiente(recipiente);
        mensaje.setAsunto("Asunto");
        mensaje.setContenido("Contenido");;

    }

}
