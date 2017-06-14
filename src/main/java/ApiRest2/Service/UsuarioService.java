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

    //Login
    public Usuario login(String nombreUsuario, String password) {
        return usuarioDao.getToLogin(nombreUsuario,password);
    }

    //Agregar un usuario
    public void agregarUsuario(Usuario usuario){

        this.usuarioDao.insert(usuario);
    }

    //Listar un usuario por nombre
    public Usuario listarUsuario(String nombre){
        return this.usuarioDao.getByName(nombre);
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
