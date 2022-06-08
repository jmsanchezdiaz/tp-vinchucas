package ar.unq.tpfinal.filtro;

import java.util.List;
import java.util.stream.Collectors;

import ar.unq.tpfinal.Insecto;
import ar.unq.tpfinal.Muestra;

public class FiltroTipoInsecto implements IFiltro {

	Insecto valorBuscado;
	
	public void changeValue(Insecto newValue) {
		this.valorBuscado = newValue;
	}
	
	@Override
	public List<Muestra> filter(List<Muestra> muestras) {
		return muestras.stream().filter(muestra -> muestra.esEspecie(this.valorBuscado)).collect(Collectors.toList());
	}

}
