package ApiRest2.Entities;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

/**
 * Created by Facundo on 13/06/2017.
 */
public class UsuarioTest extends TestCase {

    Usuario usuario;
    Ciudad ciudad;
    Provincia provincia;
    Pais pais;

    @Before
    public void setUp() throws ParseException {
        ciudad = new Ciudad();
        ciudad.setNombre("Nombre Ciudad");
        provincia = new Provincia();
        provincia.setNombre("Nombre Provincia");
        pais = new Pais();
        pais.setNombre("Nombre Pais");
        usuario = new Usuario();
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
    }


    @Test
    public void testNombre(){

        assertEquals(usuario.getNombre(), "Nombre");
    }
    @Test
    public void testApellido(){
        assertEquals(usuario.getApellido(), "Apellido");
    }
    @Test
    public void testDireccion(){
        assertEquals(usuario.getDireccion(), "Direccion");
    }
    @Test
    public void testTelefono(){
        assertEquals(usuario.getTelefono(), "Telefono");
    }
    @Test
    public void testCiudad(){
        assertEquals(usuario.getCiudad(), ciudad);
    }
    @Test
    public void textProvincia(){
        assertEquals(usuario.getProvincia(), provincia);
    }
    @Test
    public void testPais(){
        assertEquals(usuario.getPais(), pais);
    }
    @Test
    public void testEmail(){
        assertEquals(usuario.getEmail(), "Email");
    }
    @Test
    public void testUsername(){
        assertEquals(usuario.getUserName(), "Username");
    }
    @Test
    public void testContrasena() {
        assertEquals(usuario.getContrasena(), "Contrasena");
    }



}
