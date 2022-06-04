package ar.unq.tpfinal.niveldeconocimiento;

import ar.unq.tpfinal.usuario.Usuario;

public class Basico implements NivelDeConocimiento {

	@Override
	public void subirNivel(Usuario user) {
		user.setNivelDeConocimiento(new Experto());
	}

	@Override
	public void bajarNivel(Usuario user) {
		throw new RuntimeException("No se puede bajar más de nivel");
	}

	@Override
	public boolean puedeOpinarEnMuestrasVerificadasParcialcialmente() {
		return false;
	}

}
