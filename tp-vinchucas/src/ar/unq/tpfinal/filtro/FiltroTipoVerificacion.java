package ar.unq.tpfinal.filtro;

import java.util.List;
import java.util.stream.Collectors;

import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.Resultado;

public class FiltroTipoVerificacion implements IFiltro {

	Resultado valorBuscado;
	
	public void changeValue(Resultado newValue) {
		this.valorBuscado = newValue;
	}
	
	@Override
	public List<Muestra> filter(List<Muestra> muestras) {
		return muestras.stream()
				.filter(muestra -> muestra.getResultadoActual() == this.valorBuscado)
				.collect(Collectors.toList());
	}
}
