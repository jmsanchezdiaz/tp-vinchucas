package ar.unq.tpfinal;

public class UsuarioMutable extends Usuario {

	// El id deberia encontrar una forma de crearlo automaticamente sin tener que pasarselo.
	public UsuarioMutable(String nombre, String id) {
		this.setId(id);
		this.setNombre(nombre);
		this.setNivelDeConocimiento(NivelDeConocimiento.BASICO);
	}
	
}
