package ar.unq.tpfinal.usuario;

import java.util.UUID;

import ar.unq.tpfinal.NivelDeConocimiento;

public class UsuarioMutable extends Usuario {
	
	public UsuarioMutable(String nombre) {
		String uniqueID = UUID.randomUUID().toString();
		this.setId(uniqueID);
		this.setNombre(nombre);
		this.setNivelDeConocimiento(NivelDeConocimiento.BASICO);
	}

	@Override
	public void subirDeNivel() {
		this.setNivelDeConocimiento(this.getNivelDeConocimiento().siguienteNivel());
	}

	@Override
	public void bajarDeNivel() {
		this.setNivelDeConocimiento(this.getNivelDeConocimiento().anteriorNivel());
	}
}
