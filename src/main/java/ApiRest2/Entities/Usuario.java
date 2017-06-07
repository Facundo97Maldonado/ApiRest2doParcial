package ApiRest2.Entities;

/**
 * Created by Facundo on 05/06/2017.
 */
public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private Ciudad ciudad;
    private Provincia provincia;
    private Pais pais;
    private String email;
    private String userName;
    private String contrasena;

    public Usuario(){
        this.id = 0;
        this.nombre = "";
        this.apellido = "";
        this.direccion = "";
        this.telefono = "";
        this.ciudad = new Ciudad();
        this.provincia = new Provincia();
        this.pais = new Pais();
        this.email = "";
        this.userName = "";
        this.contrasena ="";
    }

    public Usuario(String nombre, String apellido, String direccion,String telefono,
                   Ciudad ciudad, Provincia provincia, Pais pais, String email, String userName, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.pais = pais;
        this.email = email;
        this.userName = userName;
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
