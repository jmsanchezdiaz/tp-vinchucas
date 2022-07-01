package ar.unq.tpfinal.usuario;

import java.util.UUID;

import Muestra.Muestra;
import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.Vinchuca;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Opinable;
import ar.unq.tpfinal.niveldeconocimiento.*;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.ubicacion.Ubicacion;

/**
 * <p>
 * 	Clase abstracta que representa al concepto de un usuario,
 *  definiendo su comportamiento por defecto y el q puede ser extendido
 * 
 * </p>
 * @author juan manuel sanchez diaz
 *
 */

//Concrete State
public class Usuario {
	private String nombre;
	private String id;
	private NivelDeConocimiento nivelDeConocimiento;
	
	/**
	 * Instancia un usuario con nivel de conocimiento basico.
	 * @param nombre
	 */
	public Usuario(String nombre) {
		this.setId(UUID.randomUUID().toString());
		this.setNombre(nombre);
		this.setNivelDeConocimiento(new Basico());
	}
	
	
	/**
	 * Instancia un usuario con nivel de conocimiento suministrado
	 * @param nombre
	 */
	public Usuario(String nombre, NivelDeConocimiento nivel) {
		this.setId(UUID.randomUUID().toString());
		this.setNombre(nombre);
		this.setNivelDeConocimiento(nivel);
	}

	public void enviarMuestra(AplicacionWeb app, Ubicacion ubi, Foto foto, Vinchuca especie) {
		app.agregarMuestra(new Muestra(this, ubi, foto, especie));
	}
	
	public void opinarMuestra(AplicacionWeb app, Muestra muestra, Opinable opinion){
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

	public void setNivelDeConocimiento(NivelDeConocimiento nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	};

	public void subirDeNivel() {
		this.getNivelDeConocimiento().subirNivel(this);
	};
	
	public void bajarDeNivel() {
		this.getNivelDeConocimiento().bajarNivel(this);
	}

	public boolean esBasico() {
		return this.getNivelDeConocimiento().esBasico();
	}
	
	public boolean esExperto() {
		return this.getNivelDeConocimiento().esExperto();
	}

}
