package ar.unq.tpfinal.filtro;

import java.util.List;

import ar.unq.tpfinal.Muestra;

public abstract class FiltroCompuesto implements IFiltro {
	List<IFiltro> filtros;
	
	public void addFilter(IFiltro filter) {
		this.getFiltros().add(filter);
	}

	public List<IFiltro> getFiltros() {
		return this.filtros;
	}
	
}

