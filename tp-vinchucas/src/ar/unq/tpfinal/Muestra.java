package ar.unq.tpfinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ar.unq.tpfinal.niveldeconocimiento.Experto;
import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.usuario.Usuario;

public class Muestra {
	
	private EspecieVinchuca especie;
	private Foto foto;
	private Ubicacion ubicacion;
	private Usuario usuario;
	private List<Opinion> opiniones;
	private LocalDate fechaDeCreacion;
	
	public Muestra(Usuario usuario, Ubicacion ubicacion, Foto foto, EspecieVinchuca especieSospechada) {
		fechaDeCreacion = LocalDate.now();
		opiniones = new ArrayList<>();
	}

	public EspecieVinchuca getEspecie() {
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
		//getOpiniones().stream().anyMatch(op -> op.esExperto());
		return false;
	}

	public List<Opinion> getResultadoActual() {
		//System.out.print(opiniones.stream().collect(Collectors.groupingBy(op -> op.getOpinion())));
		
		for (Opinion opinion : opiniones) {
			Opiniones op = opinion.getOpinion();
			switch (op) { 
		    	case VINCHUCA:
		    		break;
		    	case CHINCHE_FOLIADA:
		    		break;
		    	case PHTIA_CHINCHE:
		    		break;
		    	case IMAGEN_POCO_CLARA:
		    		break;
		    	default:
		    }
		}
		return this.opiniones;
	}
	
	public int cantidadDeOpinionesDe(Usuario usuario) {
		return opiniones.stream().filter(op -> op.esOpinionDe(usuario)).collect(Collectors.toList()).size();
	}
	
	public NivelDeVerificacion getVerificacionActual() {
		return null;
	}

	public List<Opinion> getOpiniones() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean esInsecto(Insecto valorBuscado) {
		// TODO Auto-generated method stub
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
		return null;
	}
}
