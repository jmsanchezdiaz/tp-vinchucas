package ar.unq.tpfinal;

import java.time.LocalDate;
import java.util.Date;

import ar.unq.tpfinal.usuario.Usuario;

public class Opinion {
	private Usuario usuario;
	private NivelDeConocimiento estadoAlOpinar;
	private LocalDate fechaCreacion;
	private Opiniones opinion;

	public Opinion(Usuario usuario, Opiniones opinion) {
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

	public void setOpinion(Opiniones opinion) {
		this.opinion = opinion;
	}

	public NivelDeConocimiento getNivelDeConocimiento() {
		return estadoAlOpinar;
	}
	
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}
	
	public Opiniones getOpinion() {
		return opinion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
