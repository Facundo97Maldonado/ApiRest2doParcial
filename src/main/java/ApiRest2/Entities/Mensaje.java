package ApiRest2.Entities;

import java.sql.Timestamp;

/**
 * Created by Facundo on 05/06/2017.
 */
public class Mensaje {
    private int id;
    private Usuario remitente;
    private Usuario recipiente;
    private String asunto;
    private String contenido;
    private Timestamp fecha;

    public Mensaje(){
        this.id = 0;
        this.remitente = new Usuario();
        this.recipiente = new Usuario();
        this.asunto = "";
        this.contenido = "";
    }

    public Mensaje(Usuario remitente, Usuario recipiente, String asunto, String contenido, Timestamp fecha) {
        this.remitente = remitente;
        this.recipiente = recipiente;
        this.asunto = asunto;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public Usuario getRecipiente() {
        return recipiente;
    }

    public void setRecipiente(Usuario recipiente) {
        this.recipiente = recipiente;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
