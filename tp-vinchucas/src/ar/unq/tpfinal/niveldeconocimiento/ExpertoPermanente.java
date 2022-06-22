package ar.unq.tpfinal.niveldeconocimiento;

import ar.unq.tpfinal.usuario.Usuario;

//Concrete State
public class ExpertoPermanente implements NivelDeConocimiento {

	@Override
	public void subirNivel(Usuario user) {
		throw new RuntimeException("No se puede subir de nivel a un usuario fijo");
	}

	@Override
	public void bajarNivel(Usuario user) {
		throw new RuntimeException("No se puede bajar de nivel a un usuario fijo");
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
