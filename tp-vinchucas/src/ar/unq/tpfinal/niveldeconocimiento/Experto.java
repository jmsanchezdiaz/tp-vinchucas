package ar.unq.tpfinal.niveldeconocimiento;

import ar.unq.tpfinal.usuario.Usuario;

public class Experto implements NivelDeConocimiento {

	@Override
	public void subirNivel(Usuario user) {
		// Podria ir una excepcion?
	}

	@Override
	public void bajarNivel(Usuario user) {
		user.setNivelDeConocimiento(new Basico());
	}

	@Override
	public boolean puedeOpinarEnMuestrasVerificadasParcialcialmente() {
		return true;
	}

}
