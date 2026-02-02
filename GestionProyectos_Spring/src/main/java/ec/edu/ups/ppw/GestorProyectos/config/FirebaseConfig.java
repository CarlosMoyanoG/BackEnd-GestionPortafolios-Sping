package ec.edu.ups.ppw.GestorProyectos.config;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

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

            String json = System.getenv("FIREBASE_CREDENTIALS_JSON");
            if (json == null || json.isBlank()) {
                log.warn("FIREBASE_CREDENTIALS_JSON no está definido. Firebase NO se inicializa.");
                return; // no tumbar el server
            }

            GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))
            );

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

            FirebaseApp.initializeApp(options);
            log.info("Firebase inicializado correctamente (env var).");

        } catch (Exception e) {
            log.error("Firebase init error: {}", e.getMessage(), e);
        }
    }
}
