package ar.unq.tpfinal.filtro;

import java.util.List;
import java.util.stream.Collectors;

import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.NivelDeVerificacion;

public class FiltroTipoVerificacion implements IFiltro {

	NivelDeVerificacion valorBuscado;
	
	public FiltroTipoVerificacion(NivelDeVerificacion verificacion) {
		this.valorBuscado = verificacion;
	}

	public void changeValue(NivelDeVerificacion newValue) {
		this.valorBuscado = newValue;
	}
	
	@Override
	public List<Muestra> filter(List<Muestra> muestras) {
		return muestras.stream()
				.filter(muestra -> muestra.getVerificacionActual() == this.valorBuscado)
				.collect(Collectors.toList());
	}
}
