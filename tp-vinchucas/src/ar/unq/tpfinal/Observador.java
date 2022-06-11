package ar.unq.tpfinal;

import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;

public interface Observador {
	public void eventoEnMuestra(ZonaDeCobertura zona, Muestra muestra, Aspecto aspecto);
}
