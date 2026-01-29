package ec.edu.ups.ppw.GestorProyectos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.edu.ups.ppw.GestorProyectos.bussines.GestionAsesorias;
import ec.edu.ups.ppw.GestorProyectos.modelo.Asesoria;

@RestController
@RequestMapping("/api/asesorias")
@CrossOrigin(origins = "*")
public class AsesoriaWS {

    @Autowired
    private GestionAsesorias gp;

    @GetMapping
    public List<Asesoria> getAll() {
        return gp.getAsesorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asesoria> getById(@PathVariable Long id) {
        return gp.getAsesoriaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Asesoria> create(@RequestBody Asesoria ase) {
        Asesoria created = gp.crearAsesoria(ase);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asesoria> update(@PathVariable Long id, @RequestBody Asesoria ase) {
        return gp.actualizarAsesoria(id, ase)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = gp.eliminarAsesoria(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}