package ar.unq.tpfinal;

import static org.mockito.ArgumentMatchers.anyMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import ar.unq.tpfinal.niveldeconocimiento.Experto;
import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.usuario.Usuario;

public class Muestra {
	
	private Insecto especie;
	private Foto foto;
	private Ubicacion ubicacion;
	private Usuario usuario;
	private List<Opinion> opiniones;
	private LocalDate fechaDeCreacion;
	
	public Muestra(Usuario usuario, Ubicacion ubicacion, Foto foto, Insecto especieSospechada) {
		setFechaCreacion(LocalDate.now());
		opiniones = new ArrayList<>();
	}

	public Insecto getEspecie() {
		return this.especie;
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
		getOpiniones().add(opinion);
	}
	
	public Boolean opinoUnExperto() {
		return getOpiniones().stream().anyMatch(op -> op.getUsuario().esExperto());
	}

	public Opinable getResultadoActual() {

		Map<Opinable, Long> mapOpiniones = opiniones.stream().collect(Collectors.groupingBy(op -> op.getOpinion(), Collectors.counting()));

	    Opinable resultado = null;
	    Long actualMayor = (long) 0;
	    
	    for (Entry<Opinable, Long> op : mapOpiniones.entrySet()) {
	        System.out.println(op.getKey() + "/" + op.getValue());
	        if(op.getValue() > actualMayor) {
	    		resultado = op.getKey();
	    		actualMayor = op.getValue();
	    	}
	    }
	    return resultado;
	}
	
	public int cantidadDeOpinionesDe(Usuario usuario) {
		return opiniones.stream().filter(op -> op.esOpinionDe(usuario)).collect(Collectors.toList()).size();
	}
	
	public NivelDeVerificacion getVerificacionActual() {
		return null;
	}

	public boolean esInsecto(Insecto valorBuscado) {
		return false;
	}

	public boolean fueVotadaEn(LocalDate fecha) {
		return this.getOpiniones()
				.stream()
				.anyMatch(opinion -> opinion
						.getFechaCreacion()
						.equals(fecha));
	}


	public LocalDate getFechaCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaCreacion(LocalDate fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}
}
