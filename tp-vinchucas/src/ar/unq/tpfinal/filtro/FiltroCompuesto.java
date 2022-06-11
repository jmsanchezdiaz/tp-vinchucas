package ar.unq.tpfinal.filtro;

import java.util.List;

public abstract class FiltroCompuesto implements IFiltro {
	List<IFiltro> filtros;
	
	public void addFilter(IFiltro filter) {		
		if(!this.containsFilter(filter)) {
			this.getFiltros().add(filter);
		}
	}

	public List<IFiltro> getFiltros() {
		return this.filtros;
	}
	
	public boolean containsFilter(IFiltro filter) {
		return this.getFiltros().contains(filter);
	}

	public void deleteFilter(IFiltro filter) {
		if(this.containsFilter(filter)) {
			this.getFiltros().remove(filter);
		}
	}
	
}

