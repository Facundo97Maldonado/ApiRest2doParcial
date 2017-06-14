package ApiRest2.Controller;

import ApiRest2.Converter.UsuarioConverter;
import ApiRest2.Entities.Usuario;
import ApiRest2.Response.LoginResponseWrapper;
import ApiRest2.Response.UsuarioWrapper;
import ApiRest2.Service.UsuarioService;
import ApiRest2.Util.SessionData;
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
/*@RequestMapping(
        value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)*/
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    SessionData sessionData;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<LoginResponseWrapper> login
            (@RequestParam("username") String username, @RequestParam("password") String pass){
        Usuario user = usuarioService.login(username, pass);
        if (null != user) {
            String sessionId = sessionData.addSession(user);
            return new ResponseEntity<LoginResponseWrapper>(new LoginResponseWrapper(sessionId), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


    @RequestMapping("/logout")
    public @ResponseBody ResponseEntity logout(@RequestHeader("sessionid") String sessionId) {
        sessionData.removeSession(sessionId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    //Agrego un usuario
    @RequestMapping(value = "/api/usuarios/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity agregarUsuario(@RequestBody Usuario usuario){
        try {
            usuarioService.agregarUsuario(usuario);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listo un usuario por nombre
    @RequestMapping(value = "/api/usuarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioWrapper> getUsuarioByName(@RequestParam("nombre") String nombre){
        if (usuarioService.listarUsuario(nombre) != null){
            UsuarioConverter conversor = new UsuarioConverter();
            UsuarioWrapper wrapper = conversor.convertir(usuarioService.listarUsuario(nombre));
            return new ResponseEntity<UsuarioWrapper>(wrapper, HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //Listo muchos usuarios
    @RequestMapping(value = "/api/usuarios/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<UsuarioWrapper>> getUsuarios(){
        if (this.usuarioService.listarUsuarios().size() > 0){
            UsuarioConverter conversor = new UsuarioConverter();
            List<UsuarioWrapper> wrappers = conversor.convertirLista(this.usuarioService.listarUsuarios());
            return new ResponseEntity<List<UsuarioWrapper>>(wrappers, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<UsuarioWrapper>>(HttpStatus.NO_CONTENT);
        }
    }

    //Borro un usuario
    @RequestMapping(value = "/api/usuarios/", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarUsuario(@RequestHeader int id){
        try{
            usuarioService.eliminarUsuario(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
