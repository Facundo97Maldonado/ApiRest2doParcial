package ApiRest2.Controller;


import ApiRest2.App;
import ApiRest2.Entities.Ciudad;
import ApiRest2.Entities.Pais;
import ApiRest2.Entities.Provincia;
import ApiRest2.Entities.Usuario;
import ApiRest2.Response.LoginResponseWrapper;
import ApiRest2.Service.UsuarioService;
import ApiRest2.Util.SessionData;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import junit.framework.TestCase;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;


import java.net.URL;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.mockito.Mockito.mock;



/**
 * Created by Facundo on 14/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class})
@WebAppConfiguration
public class UsuarioControllerTest extends TestCase{

    @Autowired
    private SessionData sessionData;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private LoginResponseWrapper loginResponseWrapper;
    private Usuario usuario;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .build();
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre("Nombre Ciudad");
        Provincia provincia = new Provincia();
        provincia.setNombre("Nombre Provincia");
        Pais pais = new Pais();
        pais.setNombre("Nombre Pais");
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Nombre");
        usuario.setApellido("Apellido");
        usuario.setDireccion("Direccion");
        usuario.setTelefono("Telefono");
        usuario.setCiudad(ciudad);
        usuario.setProvincia(provincia);
        usuario.setPais(pais);
        usuario.setEmail("Email");
        usuario.setUserName("Username");
        usuario.setContrasena("Contrasena");

        this.loginResponseWrapper = new LoginResponseWrapper();
        this.loginResponseWrapper.setSessionId(this.sessionData.addSession(usuario));
    }

    @After
    public void tearDown() throws Exception{
        this.sessionData.removeSession(loginResponseWrapper.getSessionId());
    }

    @Test
    public void testLoginOk() throws Exception{
        mockMvc.perform(
                post("/login")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(EntityUtils.toString(new UrlEncodedFormEntity(asList(
                            new BasicNameValuePair("username", "facu"),
                            new BasicNameValuePair("password", "1234")
                    ))))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginForbidden() throws Exception{
        mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(asList(
                                new BasicNameValuePair("username", "diferentUserName"),
                                new BasicNameValuePair("password", "diferentContrasena")
                        ))))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void logoutOk() throws Exception {
        mockMvc.perform(get("/logout")
                .header("sessionid", loginResponseWrapper.getSessionId()))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testAgregarUsuario() throws Exception{
        URL url  = Resources.getResource("usuario.json");
        String json = Resources.toString(url, Charsets.UTF_8);
        mockMvc.perform(

                post("/api/usuarios/")
                        .header("sessionid", loginResponseWrapper.getSessionId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void testListarUsuariosOk() throws Exception{
        mockMvc.perform(
                get("/api/usuarios/")
                    .header("sessionid", loginResponseWrapper.getSessionId())

        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    //Para ver en clase!!, si hago asi, y ademas hacer test de los daos, o como antes
    @Test
    public void testListarUsuariosNoContent() throws Exception {
        usuarioService = mock(UsuarioService.class);
        when(usuarioService.listarUsuarios()).thenReturn(new ArrayList<Usuario>());
        /*
        assertEquals(usuarioController.getUsuarios(), HttpStatus.NO_CONTENT);*/
    }

    @Test
    public void testListarUsuariosByNameOk() throws Exception{
        mockMvc.perform(
                get("/api/usuarios")
                    .header("sessionid", loginResponseWrapper.getSessionId())
                    .param("nombre", usuario.getNombre())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testListarUsuariosByNameNotFound() throws Exception{
        mockMvc.perform(
                get("/api/usuarios")
                        .header("sessionid", loginResponseWrapper.getSessionId())
                        .param("nombre", "penene")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUserOk() throws Exception{
        mockMvc.perform(delete
                ("/api/usuarios/")
                    .header("sessionid", loginResponseWrapper.getSessionId())
                    .header("id", this.usuario.getId())
                )
                            .andExpect(status().isOk());

    }

}
