package ApiRest2.Converter;

import ApiRest2.Entities.Usuario;
import ApiRest2.Response.UsuarioWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 08/06/2017.
 */
public class UsuarioConverter {

    public UsuarioConverter(){
    }

    public UsuarioWrapper convertir(Usuario usuario){

        UsuarioWrapper userWrapper = new UsuarioWrapper();
        userWrapper.setNombre(usuario.getNombre());
        userWrapper.setApellido(usuario.getApellido());
        userWrapper.setEmail(usuario.getEmail());
        userWrapper.setUsername(usuario.getUserName());

        return userWrapper;

    }

    public List<UsuarioWrapper> convertirLista(List<Usuario> usuarios){
        List<UsuarioWrapper> usuariosWrappers = new ArrayList<UsuarioWrapper>();
        for (Usuario user:
             usuarios) {
            UsuarioWrapper userWrapper = this.convertir(user);
            usuariosWrappers.add(userWrapper);
        }
        return usuariosWrappers;

    }

}
