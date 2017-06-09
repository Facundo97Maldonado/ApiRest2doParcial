package ApiRest2.Service;

import ApiRest2.Entities.Usuario;
import ApiRest2.Persistence.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Facundo on 06/06/2017.
 */
@Service
public class UsuarioService {

    @Autowired
    UsuarioDao usuarioDao;

    public UsuarioService(UsuarioDao dao){
        this.usuarioDao = dao;
    }

    //Agregar un usuario
    public void agregarUsuario(Usuario usuario){

        this.usuarioDao.insert(usuario);
    }

    //Listar un usuario
    public Usuario listarUsuario(int id){
        return this.usuarioDao.getById(id);
    }

    //Listar todos los usuarios
    public List<Usuario> listarUsuarios(){
        return this.usuarioDao.getAll();
    }


    //Eliminar usuario
    public void eliminarUsuario(int id){
        this.usuarioDao.deleteUser(id);
    }

}
