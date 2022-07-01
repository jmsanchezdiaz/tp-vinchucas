package ar.unq.tpfinal.filtro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Muestra.Muestra;

public class FiltroOR extends FiltroCompuesto {

	public FiltroOR() {
		this.filtros = new ArrayList<IFiltro>();
	}
	
	@Override
	public List<Muestra> filter(List<Muestra> muestras) {
		List<Muestra> resultados = new ArrayList<Muestra>();
		
		//Obtengo las muestras filtradas y las agrego a los resultados.
		for (IFiltro filtro : this.getFiltros()) {
			//Filtro los resultados que son distintos
			List<Muestra> newest = filtro
					.filter(muestras)
					.stream()
					.filter(muestra -> !resultados.contains(muestra))
					.collect(Collectors.toList());
			
			//Agrego todos los muestras filtradas a los resultados.
			resultados.addAll(newest);
		}
		
		return resultados;
	}

}
