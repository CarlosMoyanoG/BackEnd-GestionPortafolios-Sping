package ec.edu.ups.ppw.GestorProyectos.config;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

    @PostConstruct
    public void initFirebase() {
        try {
            if (!FirebaseApp.getApps().isEmpty()) {
                log.info("Firebase ya estaba inicializado.");
                return;
            }

            String credentialsPath = System.getenv("FIREBASE_CREDENTIALS_PATH");
            String credentialsJson = System.getenv("FIREBASE_CREDENTIALS_JSON");

            InputStream serviceAccountStream;

            if (credentialsPath != null && !credentialsPath.isBlank()) {
                Path p = Path.of(credentialsPath);
                if (!Files.exists(p)) {
                    log.warn("FIREBASE_CREDENTIALS_PATH apunta a un archivo que no existe: {}", credentialsPath);
                    return;
                }
                serviceAccountStream = Files.newInputStream(p);
                log.info("Firebase credentials cargadas desde PATH: {}", credentialsPath);

            } else if (credentialsJson != null && !credentialsJson.isBlank()) {
                serviceAccountStream = new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8));
                log.info("Firebase credentials cargadas desde FIREBASE_CREDENTIALS_JSON (texto).");

            } else {
                log.warn("No hay FIREBASE_CREDENTIALS_PATH ni FIREBASE_CREDENTIALS_JSON. Firebase NO se inicializa.");
                return;
            }

            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            FirebaseApp.initializeApp(options);
            log.info("Firebase inicializado correctamente.");

        } catch (Exception e) {
            log.error("Firebase init error: {}", e.getMessage(), e);
        }
    }
}
