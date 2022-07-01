package ar.unq.tpfinal.niveldeconocimiento;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.muestra.Muestra;

//Concrete State
public class ExpertoPermanente implements NivelDeConocimiento {

	@Override
	public void enviarMuestra(AplicacionWeb app, Usuario usuario, Muestra muestra) {
		app.agregarMuestra(muestra);
	}

	@Override
	public void opinarMuestra(AplicacionWeb app, Usuario usuario, Muestra muestra, Opinion opinion) {
		app.agregarOpinionA(muestra, opinion);
	}

	@Override
	public boolean puedeOpinarEnMuestraParcialmenteVerificada() {
		return true;
	}
}
