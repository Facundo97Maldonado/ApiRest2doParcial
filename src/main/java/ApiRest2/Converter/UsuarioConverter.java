package ApiRest2.Converter;

import ApiRest2.Entities.Usuario;
import ApiRest2.Wrapper.UsuarioWrapper;

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

}
