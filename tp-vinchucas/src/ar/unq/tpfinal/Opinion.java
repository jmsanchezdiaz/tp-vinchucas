package ar.unq.tpfinal;

import java.time.LocalDate;

import ar.unq.tpfinal.niveldeconocimiento.NivelDeConocimiento;
import ar.unq.tpfinal.usuario.Usuario;

public class Opinion {
	private Usuario usuario;
	private NivelDeConocimiento estadoAlOpinar;
	private LocalDate fechaCreacion;
	private Opinable opinion;

	public Opinion(Usuario usuario, Opinable opinion) {
		this.setUsuario(usuario);
		this.estadoAlOpinar = usuario.getNivelDeConocimiento();
		this.fechaCreacion = LocalDate.now();
		this.opinion = opinion;
	}
	
	public NivelDeConocimiento getEstadoAlOpinar() {
		return estadoAlOpinar;
	}

	public void setEstadoAlOpinar(NivelDeConocimiento estadoAlOpinar) {
		this.estadoAlOpinar = estadoAlOpinar;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public void setOpinion(Opinable opinion) {
		this.opinion = opinion;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}
	
	public Opinable getOpinion() {
		return opinion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Boolean esOpinionDe(Usuario usuario) {
		return this.getUsuario().equals(usuario);
	}

	public boolean esOpinionDeExperto() {
		return getUsuario().puedeOpinarEnMuestraParcialmenteVerificada();
	}
	
}
