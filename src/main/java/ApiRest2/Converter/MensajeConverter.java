package ApiRest2.Converter;

import ApiRest2.Entities.Mensaje;
import ApiRest2.Response.MensajeWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 14/06/2017.
 */
public class MensajeConverter {

    public MensajeConverter() {

    }

    public MensajeWrapper convertir(Mensaje mensaje){

        MensajeWrapper mensajeWrapper = new MensajeWrapper();
        mensajeWrapper.setEmisor(mensaje.getRemitente().getEmail());
        mensajeWrapper.setReceptor(mensaje.getRecipiente().getEmail());
        mensajeWrapper.setFecha(mensaje.getFecha());

        return mensajeWrapper;

    }

    public List<MensajeWrapper> convertirLista(List<Mensaje> mensajes){
        List<MensajeWrapper> mensajesWrappers = new ArrayList<MensajeWrapper>();
        for (Mensaje message:
                mensajes) {
            MensajeWrapper messageWrapper = this.convertir(message);
            mensajesWrappers.add(messageWrapper);
        }
        return mensajesWrappers;
    }

}
