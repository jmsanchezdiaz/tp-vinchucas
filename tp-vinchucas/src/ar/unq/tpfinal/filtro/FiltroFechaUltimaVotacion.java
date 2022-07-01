package ar.unq.tpfinal.filtro;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ar.unq.tpfinal.muestra.Muestra;

public class FiltroFechaUltimaVotacion implements IFiltro {
	
	LocalDate valorBuscado;
	
	public FiltroFechaUltimaVotacion(LocalDate date) {
		this.valorBuscado = date;
	}
	
	@Override
	public List<Muestra> filter(List<Muestra> muestras) {
		return muestras.stream()
				.filter(muestra -> muestra.fueVotadaEn(this.valorBuscado))
				.collect(Collectors.toList());
	}
}
