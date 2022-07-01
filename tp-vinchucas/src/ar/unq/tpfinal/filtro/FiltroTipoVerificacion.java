package ar.unq.tpfinal.filtro;

import java.util.List;
import java.util.stream.Collectors;

import ar.unq.tpfinal.NivelDeVerificacion;
import ar.unq.tpfinal.muestra.Muestra;

public class FiltroTipoVerificacion implements IFiltro {

	NivelDeVerificacion valorBuscado;
	
	public FiltroTipoVerificacion(NivelDeVerificacion verificacion) {
		this.valorBuscado = verificacion;
	}

	@Override
	public List<Muestra> filter(List<Muestra> muestras) {
		return muestras.stream()
				.filter(muestra -> muestra.getEstadoDeVerificacion().valor() == this.valorBuscado)
				.collect(Collectors.toList());
	}
}
