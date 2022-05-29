package ar.unq.tpfinal;

/**
 * <p>
 * 	Clase abstracta que representa al concepto de un usuario,
 *  definiendo su comportamiento por defecto y el q puede ser extendido
 * 
 * </p>
 * @author juan manuel sanchez diaz
 *
 */

public abstract class Usuario {
	private String nombre;
	private String id;
	private NivelDeConocimiento nivelDeConocimiento;
	
	public void enviarMuestra(AplicacionWeb app, Ubicacion ubi, Foto foto, EspecieVinchuca especie) {
		app.agregarMuestra(new Muestra(this, ubi, foto, especie));
	}
	
	public void opinarMuestra(AplicacionWeb app, Muestra muestra, Opiniones opinion) {
		app.agregarOpinionA(muestra, opinion);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public NivelDeConocimiento getNivelDeConocimiento() {
		return nivelDeConocimiento;
	}

	public void setNivelDeConocimiento(NivelDeConocimiento nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	}

}
