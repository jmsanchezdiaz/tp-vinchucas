package ar.unq.tpfinal;

import Muestra.Muestra;
import ar.unq.tpfinal.organizacion.Organizacion;
import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;

public interface FuncionalidadExterna {

	public void nuevoEvento(ZonaDeCobertura zona, Organizacion organizacion, Muestra muestra);
}
