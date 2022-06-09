package ar.unq.tpfinal;

import java.time.LocalDate;
import java.util.List;

import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.usuario.Usuario;

public class Muestra {

	public Muestra(Usuario usuario, Ubicacion ubicacion, Foto foto, Vinchuca especieSospechada) {
		// Solamente lo agregue para poder seguir con mi clase.
	}

	public Usuario getUsuario() {
		return null;
	}

	public int cantidadDeOpinionesDe(Usuario usuario) {
		return 0;
	}

	public LocalDate getFechaCreacion() {
		return null;
	}

	public void agregarOpinion(Opinion opinion) {

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

	

}
