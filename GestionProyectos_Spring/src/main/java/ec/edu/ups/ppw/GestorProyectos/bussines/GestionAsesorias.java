package ec.edu.ups.ppw.GestorProyectos.bussines;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.ups.ppw.GestorProyectos.DAO.AsesoriaRepository;
import ec.edu.ups.ppw.GestorProyectos.modelo.Asesoria;

@Service
public class GestionAsesorias {

    @Autowired
    private AsesoriaRepository daoAsesoria;

    public List<Asesoria> getAsesorias() {
        return daoAsesoria.findAll();
    }

    public Optional<Asesoria> getAsesoriaPorId(Long id) {
        return daoAsesoria.findById(id);
    }

    public Asesoria crearAsesoria(Asesoria asesoria) {
        // Si quieres asegurar que sea "create" puro:
        // asesoria.setId(null);
        return daoAsesoria.save(asesoria);
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
            return daoAsesoria.save(existing);
        });
    }

    public boolean eliminarAsesoria(Long id) {
        if (!daoAsesoria.existsById(id)) return false;
        daoAsesoria.deleteById(id);
        return true;
    }
}