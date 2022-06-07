package ar.unq.tpfinal.filtro;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.Muestra;

public class FiltrosTest {
	List<Muestra> muestras;
	IFiltro filtroInsecto;
	IFiltro filtroVerificacion;
	IFiltro filtroFechaCreacion;
	IFiltro filtroFechaUltimaVotacion;
	FiltroCompuesto filtroAND;
	FiltroCompuesto filtroOR;
	
	@BeforeEach
	void setup() {
		muestras = Arrays.asList(null);
		filtroInsecto = new FiltroTipoInsecto();
		filtroVerificacion = new FiltroTipoVerificacion();
		
		filtroOR = new FiltroOR();
		filtroAND = new FiltroAND();
	}
	
	@Test
	void puedoFiltrarMuestrasPorTipoDeInsecto() {
		List<Muestra> filtradas = filtroInsecto.filter(muestras);
		
	}
}
