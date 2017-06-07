package ApiRest2.Controller;

import ApiRest2.Entities.Usuario;
import ApiRest2.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Facundo on 06/06/2017.
 */
@RestController
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    //Agrego un usuario
    @RequestMapping(value = "/agregarUsuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity agregarUsuario(@RequestBody Usuario usuario){
        try {
            usuarioService.agregarUsuario(usuario);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listo un usuario
    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Usuario> getUsuario(@PathVariable("id") int id){
        if (usuarioService.listarUsuario(id) != null){
            return new ResponseEntity<Usuario>(usuarioService.listarUsuario(id), HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //Listo muchos usuarios
    @RequestMapping(value = "/usuarios/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Usuario>> getUsuarios(){
        if (this.usuarioService.listarUsuarios().size() > 0){
            return new ResponseEntity<List<Usuario>>(this.usuarioService.listarUsuarios(), HttpStatus.OK);
        }else{
            return new ResponseEntity<List<Usuario>>(HttpStatus.NO_CONTENT);
        }
    }

    //Borro un usuario
    @RequestMapping(value = "/eliminarUsuario", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarUsuario(@RequestHeader int id){
        try{
            usuarioService.eliminarUsuario(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
