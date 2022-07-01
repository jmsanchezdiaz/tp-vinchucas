package ar.unq.tpfinal;

import ar.unq.tpfinal.organizacion.Organizacion;
import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;
import ar.unq.tpfinal.muestra.Muestra;

public interface FuncionalidadExterna {

	public void nuevoEvento(ZonaDeCobertura zona, Organizacion organizacion, Muestra muestra);
}
