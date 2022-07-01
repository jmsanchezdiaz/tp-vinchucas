package ar.unq.tpfinal.muestra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ar.unq.tpfinal.Aspecto;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Insecto;
import ar.unq.tpfinal.NivelDeVerificacion;
import ar.unq.tpfinal.Opinable;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Resultado;
import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;


public class Muestra {
	
	private EstadoVerificacion estadoDeVerificacion;
	private Opinion opinionDeUsuario;
	private Foto foto;
	private Ubicacion ubicacion;
	private Usuario usuario;
	private List<Opinion> opiniones;
	private LocalDate fechaDeCreacion;
	
	public Muestra(Usuario usuario, Ubicacion ubicacion, Foto foto, Insecto especieSospechada) {
		
		this.setEstadoDeVerificacion(new NoVerificada());
		this.setFechaDeCreacion(LocalDate.now());
		this.opiniones = new ArrayList<>();
		this.opinionDeUsuario = new Opinion(usuario, especieSospechada);
		this.foto = foto;
		this.usuario = usuario;
		this.ubicacion = ubicacion;
		
		getOpiniones().add(opinionDeUsuario);
	}

	public List<Opinion> getOpiniones() {
		return opiniones;
	}
	
	private List<Opinion> getOpinionesDeExpertos() {
		return opiniones.stream().filter(op -> op.getUsuario().puedeOpinarEnMuestraParcialmenteVerificada()).collect(Collectors.toList());
	}

	public Opinion getOpinionDeUsuario() {
		return this.opinionDeUsuario;
	}
	
	public EstadoVerificacion getEstadoDeVerificacion() {
		return estadoDeVerificacion;
	}

	public void setEstadoDeVerificacion(EstadoVerificacion estadoDeVerificacion) {
		this.estadoDeVerificacion = estadoDeVerificacion;
	}

	public Foto getFoto() {
		return foto;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public LocalDate getFechaDeCreacion() {
		return fechaDeCreacion;
	}
	
	public void setFechaDeCreacion(LocalDate fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public Map<Opinable, Long> contarOpiniones(List<Opinion> opiniones) {
		return opiniones.stream().collect(Collectors.groupingBy(op -> op.getOpinion(), Collectors.counting()));
	}

	public Boolean opinoUnExperto() {
		return getOpiniones()
				.stream().anyMatch(op -> op
				.getUsuario()
				.puedeOpinarEnMuestraParcialmenteVerificada());
	}
	
	public Map<Opinable, Long> mapOpinionesDeExperto() {
		return contarOpiniones(getOpinionesDeExpertos());
	}

	public void agregarOpinion(Opinion opinion) {
		if(!elUsuarioYaOpino(opinion.getUsuario())) {
			estadoDeVerificacion.agregarOpinion(this, opinion);
		}
	}

	public Resultado getResultadoActual() {
		return estadoDeVerificacion.resultadoActual(this);
	}

	public List<Opinion> getOpinionesDeExperto() {
		return opiniones.stream()
				.filter(op -> op
						.getUsuario()
						.puedeOpinarEnMuestraParcialmenteVerificada())
				.collect(Collectors.toList());
	}

	public boolean esInsecto(Insecto valorBuscado) {
		return getResultadoActual() == valorBuscado;
	}
	
	public boolean fueVotadaEn(LocalDate fecha) {
		return this.getOpiniones().stream().anyMatch(opinion -> opinion.getFechaCreacion().equals(fecha));
	}
	
	public LocalDate getFechaCreacion() {
		return fechaDeCreacion;
	}
	
	public void setFechaCreacion(LocalDate fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}
	
	public boolean elUsuarioYaOpino(Usuario usuario) {
		return getOpiniones().stream().anyMatch(op -> op.getUsuario().equals(usuario));
	}

	/**
	 * Indica si esta muestra fue publicada entre el rango de fechas suministrado en
	 * los parametros.
	 * 
	 * @param {LocalDate} fechaInicio
	 * @param {LocalDate} fechaFin
	 * @return boolean
	 */
	public boolean fuePublicadaDentroDeEsteRango(LocalDate fechaInicio, LocalDate fechaFin) {
		return getFechaCreacion().isBefore(fechaInicio) && getFechaCreacion().isAfter(fechaFin);
	}

	/**
	 * Indica si la muestra fue enviada por el usuario pasado por parametros.
	 * @param usuario
	 * @return boolean
	 */
	public boolean fueEnviadaPor(Usuario usuario) {
		return this.getUsuario().equals(usuario);
	}

	/**
	 * Si la muestra esta verificada, notifica a las zonas pasadas por parametro.
	 * @param {List<ZonaDeCobertura>} zonasDeLaMuestra
	 */
	public void notificarValidacionSiCorresponde(List<ZonaDeCobertura> zonasDeLaMuestra) {
		if (estadoDeVerificacion instanceof Verificada) {
			zonasDeLaMuestra.forEach(zona -> zona.notificar(this, Aspecto.MUESTRA_VERIFICADA));
		}

	}

	void addOpinion(Opinion opinion) {
		getOpiniones().add(opinion);
	}

	public boolean seEncuentraEnEstado(NivelDeVerificacion nivel) {
		return this.estadoDeVerificacion.valor() == nivel;
	}
}
