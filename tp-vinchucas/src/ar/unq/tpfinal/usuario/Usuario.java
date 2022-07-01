package ar.unq.tpfinal.usuario;


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

//Context
public class Usuario {
	private String nombre;
	private NivelDeConocimiento nivelDeConocimiento;
	
	/**
	 * Instancia un usuario con nivel de conocimiento basico.
	 * @param nombre
	 */
	public Usuario(String nombre) {
		this.setNombre(nombre);
		this.setNivelDeConocimiento(new Basico());
	}
	
	/**
	 * Instancia un usuario con nivel de conocimiento suministrado
	 * @param nombre
	 */
	public Usuario(String nombre, NivelDeConocimiento nivel) {
		this.setNombre(nombre);
		this.setNivelDeConocimiento(nivel);
	}

	public void enviarMuestra(AplicacionWeb app, Ubicacion ubi, Foto foto, Vinchuca especie) {
		this.getNivelDeConocimiento().enviarMuestra(app,this, new Muestra(this, ubi, foto, especie));
	}
	
	public void opinarMuestra(AplicacionWeb app, Muestra muestra, Opinable opinion){
		this.getNivelDeConocimiento().opinarMuestra(app, this, muestra, new Opinion(this, opinion));
	}
	
	public boolean puedeOpinarEnMuestraParcialmenteVerificada() {
		return this.getNivelDeConocimiento().puedeOpinarEnMuestraParcialmenteVerificada();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public NivelDeConocimiento getNivelDeConocimiento() {
		return nivelDeConocimiento;
	}

	public void setNivelDeConocimiento(NivelDeConocimiento nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	};

}
