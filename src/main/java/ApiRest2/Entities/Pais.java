package ApiRest2.Entities;

/**
 * Created by Facundo on 06/06/2017.
 */
public class Pais {

    private int id;
    private String nombre;

    public Pais()
    {
        this.id = 0;
        this.nombre = "";
    }
    public Pais(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
