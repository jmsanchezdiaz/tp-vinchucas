package ar.unq.tpfinal.usuario;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.EspecieVinchuca;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.NivelDeConocimiento;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Opiniones;
import ar.unq.tpfinal.ubicacion.Ubicacion;

/**
 * <p>
 * Clase abstracta que representa al concepto de un usuario, definiendo su
 * comportamiento por defecto y el q puede ser extendido
 * 
 * </p>
 * 
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

	public void opinarMuestra(AplicacionWeb app, Muestra muestra, Opiniones opinion) throws Exception {
		app.agregarOpinionA(muestra, new Opinion(this, opinion));
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

<<<<<<< HEAD

=======
	public void setNivelDeConocimiento(NivelDeConocimiento nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	};
	
>>>>>>> refs/remotes/origin/master
	@Override
	public boolean equals(Object obj) {
		Usuario otherUser = (Usuario) obj;
		return this.getId().equals(otherUser.getId());
	}

	public abstract void subirDeNivel();

	public abstract void bajarDeNivel();

}
