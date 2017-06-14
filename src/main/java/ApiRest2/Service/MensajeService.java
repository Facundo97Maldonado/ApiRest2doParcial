package ApiRest2.Service;

import ApiRest2.Entities.Mensaje;
import ApiRest2.Entities.Usuario;
import ApiRest2.Persistence.MensajeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Facundo on 06/06/2017.
 */
@Service
public class MensajeService {

    @Autowired
    MensajeDao mensajeDao;

    public MensajeService(MensajeDao mensajeDao){
        this.mensajeDao = mensajeDao;
    }

    public List<Mensaje> getInbox() throws SQLException {
        return this.mensajeDao.getAll();
    }

    public List<Mensaje> getOutbox() throws SQLException {
        return this.mensajeDao.getEnviados();
    }

    public List<Mensaje> getTrash() throws SQLException {
        return this.mensajeDao.getTrash();
    }

    public void agregarMensaje(Mensaje mensaje){
        this.mensajeDao.insert(mensaje);
    }

    public void borrarMensaje(int id) throws SQLException {
        this.mensajeDao.deleteMensaje(id);
    }

}
