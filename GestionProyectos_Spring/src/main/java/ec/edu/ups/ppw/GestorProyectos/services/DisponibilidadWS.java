package ec.edu.ups.ppw.GestorProyectos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.edu.ups.ppw.GestorProyectos.bussines.GestionDisponibilidades;
import ec.edu.ups.ppw.GestorProyectos.modelo.Disponibilidad;

@RestController
@RequestMapping("/api/disponibilidades")
@CrossOrigin(origins = "*")
public class DisponibilidadWS {

    @Autowired
    private GestionDisponibilidades gd;

    @GetMapping
    public List<Disponibilidad> getAll() {
        return gd.getDisponibilidades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disponibilidad> getById(@PathVariable Long id) {
        return gd.getDisponibilidadPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Disponibilidad> create(@RequestBody Disponibilidad disp) {
        Disponibilidad created = gd.crearDisponibilidad(disp);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disponibilidad> update(@PathVariable Long id, @RequestBody Disponibilidad disp) {
        return gd.actualizarDisponibilidad(id, disp)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = gd.eliminarDisponibilidad(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}