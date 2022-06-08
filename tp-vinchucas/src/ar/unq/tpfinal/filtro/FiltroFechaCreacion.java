package ar.unq.tpfinal.filtro;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import ar.unq.tpfinal.Muestra;

public class FiltroFechaCreacion implements IFiltro {

	LocalDate valorBuscado;
	
	public void changeValue(LocalDate newValue) {
		this.valorBuscado = newValue;
	}
	
	@Override
	public List<Muestra> filter(List<Muestra> muestras) {
		return muestras.stream().filter(muestra -> muestra.getFechaCreacion().equals(this.valorBuscado)).collect(Collectors.toList());
	}
}
