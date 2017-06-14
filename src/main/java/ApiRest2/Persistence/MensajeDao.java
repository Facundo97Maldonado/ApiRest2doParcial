package ApiRest2.Persistence;

import ApiRest2.Entities.Mensaje;
import ApiRest2.Entities.Usuario;
import ApiRest2.Util.AuthenticationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 05/06/2017.
 */
@Repository
public class MensajeDao extends FatherDao<Mensaje>{

    @Autowired
    private AuthenticationData aData;

    @Autowired
    public MensajeDao(@Value("${db.host}") String host, @Value("${db.port}")String port,
                      @Value("${db.name}") String dbName, @Value("${db.username}")String dbUserName,
                      @Value("${db.pw}")String dbPass) {
        super(host, port, dbName, dbUserName, dbPass);
    }

    public void insert(Mensaje mensaje){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO mensajes" +
            " (remitente_email, recipiente_email, asunto, contenido_mensaje, fecha) VALUES  ( ?, ?, ?, ?, ?)");

            ps.setString(1,mensaje.getRemitente().getEmail());
            ps.setString(2,mensaje.getRecipiente().getEmail());
            ps.setString(3,mensaje.getAsunto());
            ps.setString(4,mensaje.getContenido());
            ps.setTimestamp(5,mensaje.getFecha());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //AHORA NO SE USA ESTO
    public Mensaje getById(int id){
        String sql = "SELECT * FROM mensajes where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Mensaje mensaje = new Mensaje((Usuario) rs.getObject("remitente_id"),
                        (Usuario) rs.getObject("recipiente_id"),
                        rs.getString("asunto"), rs.getString("contenido_mensaje"),
                        rs.getTimestamp("fecha"));

                return mensaje;
            } else
                return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Mensaje> getAll() {
        String sql = "SELECT * FROM mensajes" +
                " WHERE recipiente_email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, aData.getUsuario().getEmail());
            ResultSet rs = ps.executeQuery();
            List<Mensaje> mensajesInbox = new ArrayList<Mensaje>();
            while (rs.next()) {
                Mensaje mensaje = new Mensaje((Usuario) rs.getObject("remitente_email"),
                        (Usuario) rs.getObject("recipiente_email"),
                        rs.getString("asunto"), rs.getString("contenido_mensaje"),
                        rs.getTimestamp("fecha"));
                mensajesInbox.add(mensaje);
            }
            return mensajesInbox;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Mensaje> getEnviados() {
        String sql = "SELECT * FROM mensajes" +
                " WHERE remitente_email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, aData.getUsuario().getEmail());
            ResultSet rs = ps.executeQuery();
            List<Mensaje> mensajesInbox = new ArrayList<Mensaje>();
            while (rs.next()) {
                Mensaje mensaje = new Mensaje((Usuario) rs.getObject("remitente_email"),
                        (Usuario) rs.getObject("recipiente_email"),
                        rs.getString("asunto"), rs.getString("contenido_mensaje"),
                        rs.getTimestamp("fecha"));
                mensajesInbox.add(mensaje);
            }
            return mensajesInbox;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Mensaje> getTrash(){
        String sql = "SELECT * FROM mensajes_eliminados";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Mensaje> mensajesTrash = new ArrayList<Mensaje>();
            while (rs.next()) {
                Mensaje mensaje = new Mensaje((Usuario) rs.getObject("remitente_email"),
                        (Usuario) rs.getObject("recipiente_email"),
                        rs.getString("asunto"), rs.getString("contenido_mensaje"),
                        rs.getTimestamp("fecha"));
                mensajesTrash.add(mensaje);
            }
            return mensajesTrash;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void deleteMensaje(int id) throws SQLException{
        String sql = "DELETE FROM mensajes_bandeja_entrada WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

}
