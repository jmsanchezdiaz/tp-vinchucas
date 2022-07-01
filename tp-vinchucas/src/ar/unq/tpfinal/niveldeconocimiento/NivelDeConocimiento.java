package ar.unq.tpfinal.niveldeconocimiento;

import Muestra.Muestra;
import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.usuario.Usuario;

// Participant -  State
public interface NivelDeConocimiento {

	public abstract void enviarMuestra(AplicacionWeb app, Usuario usuario, Muestra muestra);

	public abstract void opinarMuestra(AplicacionWeb app, Usuario usuario, Muestra muestra, Opinion opinion);
	
	public abstract boolean puedeOpinarEnMuestraParcialmenteVerificada();
	
}
