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
            " (remitente_id, recipiente_id, asunto, contenido_mensaje, fecha) VALUES  ( ?, ?, ?, ?, ?)");
            ps.setInt(1,mensaje.getRemitente().getId());
            ps.setInt(2,mensaje.getRecipiente().getId());
            ps.setString(3,mensaje.getAsunto());
            ps.setString(4,mensaje.getContenido());
            ps.setTimestamp(5,mensaje.getFecha());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /*AHORA NO SE USA ESTO
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
    }*/

    public List<Mensaje> getAll() {
        String sql = "SELECT u.email as RecipienteEmail, u2.email as RemitenteEmail" +
                ", m.asunto, m.contenido_mensaje, m.fecha FROM mensajes as m" +
                " INNER JOIN usuarios as u ON m.recipiente_id = u.id"+
                " INNER JOIN usuarios as u2 ON m.remitente_id = u2.id"+
                " WHERE m.recipiente_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aData.getUsuario().getId());
            ResultSet rs = ps.executeQuery();
            List<Mensaje> mensajesInbox = new ArrayList<Mensaje>();
            while (rs.next()) {
                Usuario userEmisor = new Usuario();
                userEmisor.setEmail(rs.getString("RemitenteEmail"));
                Usuario userReceptor = new Usuario();
                userReceptor.setEmail(rs.getString("RecipienteEmail"));
                Mensaje mensaje = new Mensaje(userEmisor, userReceptor,
                        rs.getString("m.asunto"), rs.getString("m.contenido_mensaje"),
                        rs.getTimestamp("m.fecha"));
                mensajesInbox.add(mensaje);
            }
            return mensajesInbox;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Mensaje> getEnviados() {
        String sql = "SELECT u.email as RecipienteEmail, u2.email as RemitenteEmail" +
                ", m.asunto, m.contenido_mensaje, m.fecha FROM mensajes as m" +
                " INNER JOIN usuarios as u ON m.recipiente_id = u.id"+
                " INNER JOIN usuarios as u2 ON m.remitente_id = u2.id"+
                " WHERE m.remitente_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aData.getUsuario().getId());
            ResultSet rs = ps.executeQuery();
            List<Mensaje> mensajesOutbox = new ArrayList<Mensaje>();
            while (rs.next()) {
                Usuario userEmisor = new Usuario();
                userEmisor.setEmail(rs.getString("RemitenteEmail"));
                Usuario userReceptor = new Usuario();
                userReceptor.setEmail(rs.getString("RecipienteEmail"));
                Mensaje mensaje = new Mensaje(userEmisor, userReceptor,
                        rs.getString("m.asunto"), rs.getString("m.contenido_mensaje"),
                        rs.getTimestamp("m.fecha"));
                mensajesOutbox.add(mensaje);
            }
            return mensajesOutbox;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Mensaje> getTrash(){
        String sql = "SELECT u.email as RecipienteEmail, u2.email as RemitenteEmail" +
                ", m.asunto, m.contenido_mensaje, m.fecha FROM mensajes_eliminados as m" +
                " INNER JOIN usuarios as u ON m.recipiente_id = u.id"+
                " INNER JOIN usuarios as u2 ON m.remitente_id = u2.id";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Mensaje> mensajesTrash = new ArrayList<Mensaje>();
            while (rs.next()) {
                Usuario userEmisor = new Usuario();
                userEmisor.setEmail(rs.getString("RemitenteEmail"));
                Usuario userReceptor = new Usuario();
                userEmisor.setEmail(rs.getString("RecipienteEmail"));
                Mensaje mensaje = new Mensaje(userEmisor, userReceptor,
                        rs.getString("m.asunto"), rs.getString("m.contenido_mensaje"),
                        rs.getTimestamp("m.fecha"));
                mensajesTrash.add(mensaje);
            }
            return mensajesTrash;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void deleteMensaje(int id) throws SQLException{
        String sql = "DELETE FROM mensajes WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

}
