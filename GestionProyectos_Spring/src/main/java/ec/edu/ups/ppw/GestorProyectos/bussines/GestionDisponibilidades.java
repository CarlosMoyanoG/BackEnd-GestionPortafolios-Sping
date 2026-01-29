package ec.edu.ups.ppw.GestorProyectos.bussines;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.ups.ppw.GestorProyectos.DAO.DisponibilidadRepository;
import ec.edu.ups.ppw.GestorProyectos.modelo.Disponibilidad;

@Service
public class GestionDisponibilidades {

    @Autowired
    private DisponibilidadRepository daoDisponibilidad;

    public List<Disponibilidad> getDisponibilidades() {
        return daoDisponibilidad.findAll();
    }

    public Optional<Disponibilidad> getDisponibilidadPorId(Long id) {
        return daoDisponibilidad.findById(id);
    }

    public Disponibilidad crearDisponibilidad(Disponibilidad disponibilidad) {
        // disponibilidad.setId(null);
        return daoDisponibilidad.save(disponibilidad);
    }

    public Optional<Disponibilidad> actualizarDisponibilidad(Long id, Disponibilidad data) {
        return daoDisponibilidad.findById(id).map(existing -> {
            existing.setProgramadorId(data.getProgramadorId());
            existing.setTipo(data.getTipo());
            existing.setModalidad(data.getModalidad());
            existing.setDiaSemana(data.getDiaSemana());
            existing.setFecha(data.getFecha());
            existing.setHoraInicio(data.getHoraInicio());
            existing.setHoraFin(data.getHoraFin());
            existing.setHora(data.getHora());
            return daoDisponibilidad.save(existing);
        });
    }

    public boolean eliminarDisponibilidad(Long id) {
        if (!daoDisponibilidad.existsById(id)) return false;
        daoDisponibilidad.deleteById(id);
        return true;
    }
}