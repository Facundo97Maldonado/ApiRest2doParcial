package ApiRest2.Wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Facundo on 08/06/2017.
 */
public class UsuarioWrapper  {

    @JsonProperty
    String nombre;
    @JsonProperty
    String apellido;
    @JsonProperty
    String email;
    @JsonProperty
    String username;

    @JsonProperty("NombreyApellido")
    public String getNameandSurname(){
        return this.nombre + " " + this.apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
