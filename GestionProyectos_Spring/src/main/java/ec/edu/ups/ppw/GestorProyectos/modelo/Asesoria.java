package ec.edu.ups.ppw.GestorProyectos.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_asesoria")
public class Asesoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ase_id")
    private Long id;

    @Column(name = "ase_progId")
    private Long programadorId;

    @Column(name = "ase_nombreCliente")
    private String nombreCliente;

    @Column(name = "ase_emailCliente")
    private String emailCliente;

    @Column(name = "ase_fecha")
    private String fecha;

    @Column(name = "ase_hora")
    private String hora;

    @Column(name = "ase_descripcionProyecto")
    private String descripcionProyecto;

    @Column(name = "ase_estado")
    private String estado;

    @Column(name = "ase_mensajeRespuesta")
    private String mensajeRespuesta;

    public Asesoria() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProgramadorId() { return programadorId; }
    public void setProgramadorId(Long programadorId) { this.programadorId = programadorId; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getDescripcionProyecto() { return descripcionProyecto; }
    public void setDescripcionProyecto(String descripcionProyecto) { this.descripcionProyecto = descripcionProyecto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMensajeRespuesta() { return mensajeRespuesta; }
    public void setMensajeRespuesta(String mensajeRespuesta) { this.mensajeRespuesta = mensajeRespuesta; }
}
