package ApiRest2.Controller;

import ApiRest2.Entities.Mensaje;
import ApiRest2.Service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by Facundo on 05/06/2017.
 */

@RestController
public class MensajeController {

    @Autowired
    MensajeService mensajeService;


    //Mostrar todos los mensajes = "BANDEJA DE ENTRADA o INBOX"
    @RequestMapping(value = "/inbox/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Mensaje>> getBandejaEntrada() throws SQLException {

        if (this.mensajeService.getInbox().size() > 0) {
            return new ResponseEntity<List<Mensaje>>(this.mensajeService.getInbox(), HttpStatus.OK);
        }else {
            return new ResponseEntity<List<Mensaje>>(HttpStatus.NO_CONTENT);
        }
    }

    //Mostrar mensajes en la bandeja de borrados
    @RequestMapping(value = "/mensajesBorrados/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Mensaje>> getMensajesEliminados() throws SQLException {
        if (this.mensajeService.getTrash().size() > 0){
            return new ResponseEntity<List<Mensaje>>(this.mensajeService.getTrash(), HttpStatus.OK);
        }else{
            return new ResponseEntity<List<Mensaje>>(HttpStatus.NO_CONTENT);
        }
    }

    //Borrar un mensaje
    @RequestMapping(value = "/eliminarMensaje", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarMensaje(@RequestHeader int id){
        try{
            mensajeService.borrarMensaje(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //Enviar un mensaje
    @RequestMapping(value = "/enviarMensaje", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity agregarMensaje(@RequestBody Mensaje mensaje){
        try {
            mensajeService.agregarMensaje(mensaje);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
