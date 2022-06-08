package ar.unq.tpfinal.filtro;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ar.unq.tpfinal.Muestra;

public class FiltroFechaUltimaVotacion implements IFiltro {
	
	LocalDate valorBuscado;
	
	public void changeValue(LocalDate newValue) {
		this.valorBuscado = newValue;
	}
	
	@Override
	public List<Muestra> filter(List<Muestra> muestras) {
//		Tengo una duda, con los filtros de fecha, se busca encontrar las muestras de la fecha exacta (??)
		return muestras.stream()
				.filter(muestra -> muestra.getOpiniones().stream().anyMatch(opi -> opi.getFechaCreacion().equals(this.valorBuscado)))
				.collect(Collectors.toList());
	}
}
