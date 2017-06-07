package ApiRest2.Persistence;

import ApiRest2.Entities.Mensaje;
import ApiRest2.Entities.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 05/06/2017.
 */
@Repository
public class MensajeDao extends FatherDao{

    public MensajeDao() {
        super();
    }

    public void sendMensaje(Mensaje mensaje){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO mensajes_bandeja_entrada " +
                    "(remitente_id, recipiente_id, asunto, " +
                    "contenido_mensaje, fecha) VALUES  ( ?, ?, ?, ?, ?)");
            ps.setInt(1,mensaje.getRemitente().getId() );
            ps.setInt(2,mensaje.getRecipiente().getId());
            ps.setString(3,mensaje.getAsunto());
            ps.setString(4,mensaje.getContenido());
            ps.setTimestamp(5,mensaje.getFecha());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //AHORA NO SE USA ESTO
    public Mensaje getMensaje(int id) throws SQLException {
        String sql = "SELECT * FROM mensajes where id = ?";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()){
            Mensaje mensaje = new Mensaje((Usuario) rs.getObject("remitente_id"),
                    (Usuario) rs.getObject("recipiente_id"),
                    rs.getString("asunto"), rs.getString("contenido_mensaje"),
                    rs.getTimestamp("fecha"));

            return mensaje;
        }else
            return null;
    }

    public List<Mensaje> getMensajesInbox() throws SQLException {
        String sql = "SELECT * FROM mensajes_bandeja_entrada";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Mensaje> mensajesInbox = new ArrayList<Mensaje>();
        while(rs.next()){
            Mensaje mensaje = new Mensaje((Usuario) rs.getObject("remitente_id"),
                    (Usuario) rs.getObject("recipiente_id"),
                    rs.getString("asunto"), rs.getString("contenido_mensaje"),
                    rs.getTimestamp("fecha"));
            mensajesInbox.add(mensaje);
        }
        return mensajesInbox;
    }

    public List<Mensaje> getMensajesTrash() throws SQLException{
        String sql = "SELECT * FROM mensajes_eliminados";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Mensaje> mensajesTrash = new ArrayList<Mensaje>();
        while(rs.next()){
            Mensaje mensaje = new Mensaje((Usuario) rs.getObject("remitente_id"),
                    (Usuario) rs.getObject("recipiente_id"),
                    rs.getString("asunto"), rs.getString("contenido_mensaje"),
                    rs.getTimestamp("fecha"));
            mensajesTrash.add(mensaje);
        }
        return mensajesTrash;
    }

    public void deleteMensaje(int id) throws SQLException{
        String sql = "DELETE FROM mensajes_bandeja_entrada WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

}
