package ar.unq.tpfinal.filtro;

import java.util.List;

import ar.unq.tpfinal.Muestra;

public interface IFiltro {

	public List<Muestra> filter(List<Muestra> muestras);
}
