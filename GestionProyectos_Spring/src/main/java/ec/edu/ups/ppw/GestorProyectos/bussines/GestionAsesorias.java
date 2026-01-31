package ec.edu.ups.ppw.GestorProyectos.bussines;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.ups.ppw.GestorProyectos.DAO.AsesoriaRepository;
import ec.edu.ups.ppw.GestorProyectos.modelo.Asesoria;
import ec.edu.ups.ppw.GestorProyectos.services.EmailService;
import ec.edu.ups.ppw.GestorProyectos.services.ProgramadorClient;

@Service
public class GestionAsesorias {

    @Autowired
    private AsesoriaRepository daoAsesoria;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProgramadorClient programadorClient;

    public List<Asesoria> getAsesorias() {
        return daoAsesoria.findAll();
    }

    public Optional<Asesoria> getAsesoriaPorId(Long id) {
        return daoAsesoria.findById(id);
    }

    public Asesoria crearAsesoria(Asesoria asesoria) {
        Asesoria created = daoAsesoria.save(asesoria);

        String emailProg = programadorClient.obtenerEmailProgramador(created.getProgramadorId());
        String asunto = "Nueva asesoría solicitada";
        String cuerpo = "Se ha creado una asesoría:\n\n"
                + "Cliente: " + created.getNombreCliente() + "\n"
                + "Fecha: " + created.getFecha() + " " + created.getHora() + "\n"
                + "Descripción: " + created.getDescripcionProyecto();

        emailService.enviar(created.getEmailCliente(), asunto, cuerpo);
        emailService.enviar(emailProg, asunto, cuerpo);

        return created;
    }

    public Optional<Asesoria> actualizarAsesoria(Long id, Asesoria data) {
        return daoAsesoria.findById(id).map(existing -> {
            existing.setProgramadorId(data.getProgramadorId());
            existing.setNombreCliente(data.getNombreCliente());
            existing.setEmailCliente(data.getEmailCliente());
            existing.setFecha(data.getFecha());
            existing.setHora(data.getHora());
            existing.setDescripcionProyecto(data.getDescripcionProyecto());
            existing.setEstado(data.getEstado());
            existing.setMensajeRespuesta(data.getMensajeRespuesta());

            Asesoria updated = daoAsesoria.save(existing);

            String emailProg = programadorClient.obtenerEmailProgramador(updated.getProgramadorId());

            String asunto = "Actualización de asesoría (" + updated.getEstado() + ")";
            String cuerpo = "Tu asesoría fue " + updated.getEstado() + ".\n\n"
                    + "Fecha: " + updated.getFecha() + " " + updated.getHora() + "\n"
                    + "Mensaje: " + updated.getMensajeRespuesta();

            emailService.enviar(updated.getEmailCliente(), asunto, cuerpo);
            emailService.enviar(emailProg, asunto, cuerpo);

            return updated;
        });
    }

    public boolean eliminarAsesoria(Long id) {
        if (!daoAsesoria.existsById(id)) return false;
        daoAsesoria.deleteById(id);
        return true;
    }
}
