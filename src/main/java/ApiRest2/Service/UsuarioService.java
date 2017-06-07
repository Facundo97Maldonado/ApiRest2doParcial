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

    UsuarioDao usuarioDao;

    @Autowired
    public UsuarioService(UsuarioDao usuarioDao){
        this.usuarioDao = usuarioDao;
    }

    //Agregar un usuario
    public void agregarUsuario(Usuario usuario){

        this.usuarioDao.addUser(usuario);
    }

    //Listar un usuario
    public Usuario listarUsuario(int id){
        return this.usuarioDao.listOneUser(id);
    }

    //Listar todos los usuarios
    public List<Usuario> listarUsuarios(){
        return this.usuarioDao.listUsers();
    }


    //Eliminar usuario
    public void eliminarUsuario(int id){
        this.usuarioDao.deleteUser(id);
    }

}
