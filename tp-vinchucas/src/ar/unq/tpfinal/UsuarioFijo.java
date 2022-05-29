package ar.unq.tpfinal;

public class UsuarioFijo extends Usuario {
	
	// El id deberia encontrar una forma de crearlo automaticamente sin tener que pasarselo.
	public UsuarioFijo(String nombre, String id) {
		super.setId(id);
		super.setNombre(nombre);
		super.setNivelDeConocimiento(NivelDeConocimiento.EXPERTO);
	}
	
	@Override
	public NivelDeConocimiento getNivelDeConocimiento() {
		return NivelDeConocimiento.EXPERTO;
	}
	
	@Override
	public void setNivelDeConocimiento(NivelDeConocimiento conocimiento) {
		
	}
	
}
