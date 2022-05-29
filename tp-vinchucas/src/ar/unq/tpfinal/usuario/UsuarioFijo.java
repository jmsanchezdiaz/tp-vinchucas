package ar.unq.tpfinal.usuario;

import java.util.UUID;

import ar.unq.tpfinal.NivelDeConocimiento;

public class UsuarioFijo extends Usuario {
	
	public UsuarioFijo(String nombre, String id) {
		String uniqueID = UUID.randomUUID().toString();
		this.setId(uniqueID);
		this.setNombre(nombre);
	}
	
	@Override
	public NivelDeConocimiento getNivelDeConocimiento() {
		return NivelDeConocimiento.EXPERTO;
	}
	
	@Override
	public void setNivelDeConocimiento(NivelDeConocimiento conocimiento) {
		
	}

	@Override
	public void subirDeNivel() {
		throw new RuntimeException("No se puede subir de nivel a un usuario fijo");
	}

	@Override
	public void bajarDeNivel() {
		throw new RuntimeException("No se puede bajar de nivel a un usuario fijo");
	}
	
}
