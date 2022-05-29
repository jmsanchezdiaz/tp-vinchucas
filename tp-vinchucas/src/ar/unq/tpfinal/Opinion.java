package ar.unq.tpfinal;

import java.time.LocalDate;
import java.util.Date;

public class Opinion {
	
	private NivelDeConocimiento estadoAlOpinar;
	private LocalDate fechaCreacion;
	private Opiniones opinion;
	
	public Opinion(Usuario usuario, Date fechaCreacion, Opiniones opinion) {
		
		this.estadoAlOpinar = usuario.getNivelDeConocimiento();
		this.fechaCreacion = LocalDate.now();
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
	
}
