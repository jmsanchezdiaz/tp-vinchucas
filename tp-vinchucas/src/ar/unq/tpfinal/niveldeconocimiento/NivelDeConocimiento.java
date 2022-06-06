package ar.unq.tpfinal.niveldeconocimiento;

import ar.unq.tpfinal.usuario.Usuario;

// Participant -  State
public interface NivelDeConocimiento {

	public abstract void subirNivel(Usuario user);

	public abstract void bajarNivel(Usuario user);

	public abstract boolean puedeOpinarEnMuestrasVerificadasParcialcialmente();
	
}
