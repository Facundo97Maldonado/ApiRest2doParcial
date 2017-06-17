package ApiRest2.Controller;

import ApiRest2.Converter.MensajeConverter;
import ApiRest2.Entities.Mensaje;
import ApiRest2.Response.MensajeWrapper;
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


    //Mostrar mensajes inbox
    @RequestMapping(value = "/api/mensajes/inbox", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<MensajeWrapper>> getBandejaEntrada() throws SQLException {

        if (this.mensajeService.getInbox().size() > 0) {
            MensajeConverter conversor = new MensajeConverter();
            List<MensajeWrapper> wrappers = conversor.convertirLista(this.mensajeService.getInbox());
            return new ResponseEntity<List<MensajeWrapper>>(wrappers, HttpStatus.OK);
        }else {
            return new ResponseEntity<List<MensajeWrapper>>(HttpStatus.NO_CONTENT);
        }
    }


    //Mostrar mensajes outbox
    @RequestMapping(value = "/api/mensajes/outbox", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<MensajeWrapper>> getBandejaSalida() throws SQLException {

        if (this.mensajeService.getOutbox().size() > 0) {
            MensajeConverter conversor = new MensajeConverter();
            List<MensajeWrapper> wrappers = conversor.convertirLista(this.mensajeService.getOutbox());
            return new ResponseEntity<List<MensajeWrapper>>(wrappers, HttpStatus.OK);
        }else {
            return new ResponseEntity<List<MensajeWrapper>>(HttpStatus.NO_CONTENT);
        }
    }

    //Mostrar mensajes en la bandeja de borrados
    @RequestMapping(value = "/api/mensajes/borrados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<MensajeWrapper>> getMensajesEliminados() throws SQLException {
        if (this.mensajeService.getTrash().size() > 0){
            MensajeConverter conversor = new MensajeConverter();
            List<MensajeWrapper> wrappers = conversor.convertirLista(this.mensajeService.getTrash());
            return new ResponseEntity<List<MensajeWrapper>>(wrappers, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<MensajeWrapper>>(HttpStatus.NO_CONTENT);
        }
    }

    //Borrar un mensaje
    @RequestMapping(value = "/api/mensajes/", method = RequestMethod.DELETE)
    public ResponseEntity borrarMensaje(@RequestHeader int id){
        try{
            mensajeService.borrarMensaje(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //Enviar un mensaje
    @RequestMapping(value = "/api/mensajes/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity agregarMensaje(@RequestBody Mensaje mensaje){
        try {
            mensajeService.agregarMensaje(mensaje);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
