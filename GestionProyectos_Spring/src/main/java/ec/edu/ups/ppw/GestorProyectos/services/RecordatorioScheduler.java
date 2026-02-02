package ec.edu.ups.ppw.GestorProyectos.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ec.edu.ups.ppw.GestorProyectos.DAO.AsesoriaRepository;
import ec.edu.ups.ppw.GestorProyectos.modelo.Asesoria;

@Component
public class RecordatorioScheduler {

    @Autowired
    private AsesoriaRepository repo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProgramadorClient programadorClient;

    @Autowired
    private TelegramClient telegram;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Scheduled(fixedRate = 60000)
    public void enviarRecordatorios() {
        List<Asesoria> asesorias = repo.findAll();

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime ventanaInicio = ahora.plusMinutes(0);
        LocalDateTime ventanaFin = ahora.plusMinutes(1);

        for (Asesoria a : asesorias) {
            if (!"aprobada".equalsIgnoreCase(a.getEstado())) continue;

            try {
                LocalDateTime fechaHora = LocalDateTime.parse(
                        a.getFecha() + " " + a.getHora(),
                        formatter
                );

                if (fechaHora.isAfter(ventanaInicio) && fechaHora.isBefore(ventanaFin)) {
                    String emailCliente = a.getEmailCliente();
                    String emailProgramador = programadorClient.obtenerEmailProgramador(a.getProgramadorId());

                    String asunto = "Recordatorio de asesoría";
                    String cuerpo = "Hola, tu asesoría está programada para hoy a las " + a.getHora()
                            + ".\n\nCliente: " + a.getNombreCliente()
                            + "\nDescripción: " + a.getDescripcionProyecto();

                    emailService.enviar(emailCliente, asunto, cuerpo);
                    emailService.enviar(emailProgramador, asunto, cuerpo);

                    telegram.send(
                        null,
                        "⏰ Recordatorio: tu asesoría inicia en 1 minuto. Hora: " + a.getHora()
                    );
                }

            } catch (Exception ignored) {}
        }
    }
}
