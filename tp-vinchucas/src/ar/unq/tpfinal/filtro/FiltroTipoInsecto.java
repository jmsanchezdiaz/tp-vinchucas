package ar.unq.tpfinal.filtro;

import java.util.List;
import java.util.stream.Collectors;

import ar.unq.tpfinal.Insecto;
import ar.unq.tpfinal.Muestra;

public class FiltroTipoInsecto implements IFiltro {

	Insecto valorBuscado;
	
	public FiltroTipoInsecto(Insecto insecto) {
		this.valorBuscado = insecto;
	}
	
	@Override
	public List<Muestra> filter(List<Muestra> muestras) {
		return muestras.stream()
				.filter(muestra -> muestra.esInsecto(this.valorBuscado))
				.collect(Collectors.toList());
	}

}
