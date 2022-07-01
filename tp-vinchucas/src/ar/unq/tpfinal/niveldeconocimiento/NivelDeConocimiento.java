package ar.unq.tpfinal.niveldeconocimiento;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.muestra.Muestra;

// Participant -  State
public interface NivelDeConocimiento {

	public abstract void enviarMuestra(AplicacionWeb app, Usuario usuario, Muestra muestra);

	public abstract void opinarMuestra(AplicacionWeb app, Usuario usuario, Muestra muestra, Opinion opinion);
	
	public abstract boolean puedeOpinarEnMuestraParcialmenteVerificada();
	
}
