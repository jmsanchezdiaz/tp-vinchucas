package ar.unq.tpfinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;

public class Muestra {

	private Opinion opinionDeUsuario;
	private Foto foto;
	private Ubicacion ubicacion;
	private Usuario usuario;
	private List<Opinion> opiniones;
	private LocalDate fechaDeCreacion;

	public Muestra(Usuario usuario, Ubicacion ubicacion, Foto foto, Insecto especieSospechada) {
		this.fechaDeCreacion = LocalDate.now();
		this.opiniones = new ArrayList<>();
		this.opinionDeUsuario = new Opinion(usuario, especieSospechada);
		this.foto = foto;
		this.usuario = usuario;
		this.ubicacion = ubicacion;
		
		getOpiniones().add(opinionDeUsuario);
	}

	public Opinion getOpinionDeUsuario() {
		return this.opinionDeUsuario;
	}

	public Foto getFoto() {
		return this.foto;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public Ubicacion getUbicacion() {
		return this.ubicacion;
	}

	public List<Opinion> getOpiniones() {
		return opiniones;
	}

	public void agregarOpinion(Opinion opinion) {

		Boolean opinaUnExperto = opinion.getUsuario().esExperto();

		if (esMuestraVerificada() || elUsuarioYaOpino(opinion.getUsuario())) {
			return;
		} else if (!opinoUnExperto() || opinaUnExperto) {
			getOpiniones().add(opinion);
		}

	}

	public Boolean elUsuarioYaOpino(Usuario usuario) {
		return getOpiniones().stream().anyMatch(op -> op.getUsuario().equals(usuario));
	}

	public Boolean esMuestraVerificada() {
		return getVerificacionActual() == NivelDeVerificacion.VERIFICADA;
	}

	public Boolean opinoUnExperto() {
		return getOpiniones().stream().anyMatch(op -> op.getUsuario().esExperto());
	}

	public Resultado getResultadoActual() {

		Map<Opinable, Long> mapOpiniones = contarOpiniones(getOpiniones());

		Resultado resultado = ResultadoEmpate.NO_DEFINIDO;
		Long actualMayor = (long) 0;

		for (Entry<Opinable, Long> op : mapOpiniones.entrySet()) {
			if (op.getValue() > actualMayor) {
				resultado = op.getKey();
				actualMayor = op.getValue();
			} else if (op.getValue() == actualMayor) {
				resultado = ResultadoEmpate.NO_DEFINIDO;
			}
		}
		return resultado;

	}

	public NivelDeVerificacion getVerificacionActual() {

		if (opinoUnExperto()) {

			Map<Opinable, Long> mapOpiniones = contarOpiniones(getOpinionesDeExperto());

			for (Entry<Opinable, Long> op : mapOpiniones.entrySet()) {
				if (op.getValue() > 1) {
					return NivelDeVerificacion.VERIFICADA;
				} else {
					return NivelDeVerificacion.VERIFICADA_PARCIAL;
				}
			}
		}

		return NivelDeVerificacion.NO_VERIFICADA;
	}

	public Map<Opinable, Long> contarOpiniones(List<Opinion> opiniones) {
		return opiniones.stream().collect(Collectors.groupingBy(op -> op.getOpinion(), Collectors.counting()));
	}

	public List<Opinion> getOpinionesDeExperto() {
		return opiniones.stream().filter(op -> op.getUsuario().esExperto()).collect(Collectors.toList());

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

	public boolean fueEnviadaPor(Usuario usuario) {
		return this.getUsuario().equals(usuario);
	}

	public void notificarValidacionSiCorresponde(List<ZonaDeCobertura> zonasDeLaMuestra) {
		if (this.esMuestraVerificada()) {
			zonasDeLaMuestra.forEach(zona -> zona.notificar(this, Aspecto.MUESTRA_VERIFICADA));
		}

	}
}
