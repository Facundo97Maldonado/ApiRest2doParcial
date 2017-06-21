package ApiRest2.Controller;

import ApiRest2.App;
import ApiRest2.Entities.*;
import ApiRest2.Response.LoginResponseWrapper;
import ApiRest2.Util.AuthenticationData;
import ApiRest2.Util.SessionData;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

/**
 * Created by Facundo on 16/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class})
@WebAppConfiguration
public class MensajeControllerTest extends TestCase{

    @Autowired
    private AuthenticationData aData;

    @Autowired
    private SessionData sessionData;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private LoginResponseWrapper loginResponseWrapper;
    private Mensaje mensaje;
    private Usuario usuario;
    private Usuario remitente;
    private Usuario recipiente;

    @Before
    public void setUp() throws ParseException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .build();

        DateFormat myDateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
        Date date = myDateFormat.parse("24/03/1997 12:54:25");
        long myTime = date.getTime();


        Ciudad ciudad = new Ciudad();
        ciudad.setId(1);
        ciudad.setNombre("Belgrano");
        Provincia provincia = new Provincia();
        provincia.setId(1);
        provincia.setNombre("Buenos Aires");
        Pais pais = new Pais();
        pais.setId(1);
        pais.setNombre("Argentina");

        usuario = new Usuario();
        usuario.setNombre("Nombre");
        usuario.setApellido("Apellido");
        usuario.setDireccion("Direccion");
        usuario.setTelefono("Telefono");
        usuario.setCiudad(ciudad);
        usuario.setProvincia(provincia);
        usuario.setPais(pais);
        usuario.setEmail("facumaldonado@gmail.com");
        usuario.setUserName("facu");
        usuario.setContrasena("1234");

        remitente = new Usuario();
        remitente.setId(3);
        remitente.setNombre("Nombre");
        remitente.setApellido("Apellido");
        remitente.setDireccion("Direccion");
        remitente.setTelefono("Telefono");
        remitente.setCiudad(ciudad);
        remitente.setProvincia(provincia);
        remitente.setPais(pais);
        remitente.setEmail("facumaldonado@gmail.com");
        remitente.setUserName("facu");
        remitente.setContrasena("1234");

        recipiente = new Usuario();
        recipiente.setNombre("Nombre");
        recipiente.setApellido("Apellido");
        recipiente.setDireccion("Direccion");
        recipiente.setTelefono("Telefono");
        recipiente.setCiudad(ciudad);
        recipiente.setProvincia(provincia);
        recipiente.setPais(pais);
        recipiente.setEmail("elneneestabien@elnene.com");
        recipiente.setUserName("elnene");
        recipiente.setContrasena("estabien");

        mensaje = new Mensaje();
        mensaje.setId(1);
        mensaje.setRecipiente(recipiente);
        mensaje.setRemitente(remitente);
        mensaje.setAsunto("Asunto");
        mensaje.setContenido("Contenido");
        mensaje.setFecha(new Timestamp(myTime));

        this.loginResponseWrapper = new LoginResponseWrapper();
        this.loginResponseWrapper.setSessionId(this.sessionData.addSession(remitente));
        this.aData.setUsuario(remitente);
    }

    @After
    public void tearDown() {
        this.sessionData.removeSession(this.loginResponseWrapper.getSessionId());
    }

    @Test
    public void testGetInboxOk() throws Exception{
        mockMvc.perform(
                get("/api/mensajes/inbox")
                    .header("sessionid", this.loginResponseWrapper.getSessionId())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetInboxNoContent() throws Exception{
        this.loginResponseWrapper.setSessionId(this.sessionData.addSession(this.usuario));
        mockMvc.perform(
                get("/api/mensajes/inbox")
                        .header("sessionid", this.loginResponseWrapper.getSessionId())
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetOutboxOk() throws Exception {
        mockMvc.perform(
                get("/api/mensajes/outbox")
                        .header("sessionid", this.loginResponseWrapper.getSessionId())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetOutboxNoContent() throws Exception {
        this.loginResponseWrapper.setSessionId(this.sessionData.addSession(this.usuario));
        mockMvc.perform(
                get("/api/mensajes/outbox")
                        .header("sessionid", this.loginResponseWrapper.getSessionId())
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetTrashOk() throws Exception {
        mockMvc.perform(
                get("/api/mensajes/borrados")
                        .header("sessionid", this.loginResponseWrapper.getSessionId())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testBorrarMensajeOk() throws Exception {
        mockMvc.perform((
                delete("/api/mensajes/")
                    .header("sessionid", this.loginResponseWrapper.getSessionId())
                    .header("id", mensaje.getId())
                )
        )
                .andExpect(status().isOk());
    }


    @Test
    public void testEnviarMensajeOk() throws Exception {
        URL url  = Resources.getResource("mensaje.json");
        String json = Resources.toString(url, Charsets.UTF_8);

        mockMvc.perform(
                post("/api/mensajes/")
                        .header("sessionid", this.loginResponseWrapper.getSessionId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
        )
                .andExpect(status().isCreated());
    }
}
