package ec.edu.ups.ppw.GestorProyectos.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ProgramadorClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String BASE_URL = "http://127.0.0.1:8080/GestorProyectos/api/programador";

    public String obtenerEmailProgramador(Long programadorId) {
        if (programadorId == null) return null;
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    BASE_URL + "/" + programadorId,
                    Map.class
            );

            if (response.getBody() == null) return null;

            // Lee posibles nombres de campo
            Object email = response.getBody().get("emailContacto");
            if (email == null) email = response.getBody().get("pro_emailContacto");
            if (email == null) email = response.getBody().get("proEmailContacto");

            return email != null ? email.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
