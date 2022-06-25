package ar.unq.tpfinal.niveldeconocimiento;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.usuario.Usuario;

//ConcreteState
public class Experto implements NivelDeConocimiento {

	@Override
	public void enviarMuestra(AplicacionWeb app, Usuario usuario, Muestra muestra) {
		app.agregarMuestra(muestra);
		
		if(this.debeBajarDeNivel(app, usuario)) {
			usuario.setNivelDeConocimiento(new Basico());
		}
	}

	public boolean debeBajarDeNivel(AplicacionWeb app, Usuario usuario) {
		// Obtengo la cantidad de esos envios que son del usuario
		int cantidadDeEnviosDelUsuario = app.cantidadDeEnviosDeHace(usuario, 30);

		// Obtengo la cantidad de opiniones mandadas del usuario
		int cantidadDeOpinionesDelUsuario = app.cantidadDeOpinionesDeHace(usuario, 30);
		
		return cantidadDeEnviosDelUsuario <= 10 || cantidadDeOpinionesDelUsuario <= 30;
	}

	@Override
	public void opinarMuestra(AplicacionWeb app, Usuario usuario, Muestra muestra, Opinion opinion) {
		app.agregarOpinionA(muestra, opinion);
		
		if(this.debeBajarDeNivel(app, usuario)) {
			usuario.setNivelDeConocimiento(new Basico());
		}
	}

	@Override
	public boolean puedeOpinarEnMuestraParcialmenteVerificada() {
		return true;
	}
}
