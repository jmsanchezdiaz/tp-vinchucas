package ar.unq.tpfinal.filtro;

import java.util.ArrayList;
import java.util.List;

import ar.unq.tpfinal.Muestra;

public class FiltroAND extends FiltroCompuesto {

	public FiltroAND() {
		this.filtros = new ArrayList<IFiltro>();
	}
	
	@Override
	public List<Muestra> filter(List<Muestra> muestras) {
		List<Muestra> resultados = muestras;
		
		//Recorro por todos los filtros, filtrando todas las muestras.
		for(IFiltro filtros : this.getFiltros()) {
			resultados = filtros.filter(resultados);
		}

		return resultados;		
	}

}
