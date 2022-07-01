package ar.unq.tpfinal;

import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;
import ar.unq.tpfinal.muestra.Muestra;

public interface Observador {
	public void eventoEnMuestra(ZonaDeCobertura zona, Muestra muestra, Aspecto aspecto);
}
