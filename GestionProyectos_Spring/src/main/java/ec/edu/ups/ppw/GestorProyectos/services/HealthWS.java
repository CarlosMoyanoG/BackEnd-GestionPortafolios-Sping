package ec.edu.ups.ppw.GestorProyectos.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthWS {

    @GetMapping("/")
    public Map<String, String> health() {
        return Map.of(
            "status", "ok",
            "service", "GestionProyectos Spring",
            "message", "Backend activo y funcionando"
        );
    }
}
