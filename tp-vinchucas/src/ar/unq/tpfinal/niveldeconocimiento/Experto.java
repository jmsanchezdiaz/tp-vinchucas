package ar.unq.tpfinal.niveldeconocimiento;

import ar.unq.tpfinal.usuario.Usuario;

//ConcreteState
public class Experto implements NivelDeConocimiento {

	@Override
	public void subirNivel(Usuario user) {
		throw new RuntimeException("No se puede subir más de nivel");
	}

	@Override
	public void bajarNivel(Usuario user) {
		user.setNivelDeConocimiento(new Basico());
	}
	
	@Override
	public boolean esBasico() {
		return false;
	}

	@Override
	public boolean esExperto() {
		return true;
	}
}
