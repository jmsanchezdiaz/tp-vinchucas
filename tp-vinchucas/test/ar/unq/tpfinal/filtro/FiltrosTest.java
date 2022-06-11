package ar.unq.tpfinal.filtro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.NivelDeVerificacion;
import ar.unq.tpfinal.NoVinchuca;

public class FiltrosTest {
	List<Muestra> muestras;
	LocalDate fecha;
	Muestra muestraMock1;
	Muestra muestraMock2;
	Muestra muestraMock3;
	Muestra muestraMock4;
	IFiltro filtroInsecto;
	IFiltro filtroVerificacion;
	IFiltro filtroFechaCreacion;
	IFiltro filtroFechaUltimaVotacion;
	FiltroCompuesto filtroAND;
	FiltroCompuesto filtroOR;
	
	@BeforeEach
	void setup() {
		fecha = LocalDate.of(2021, 5, 11);
		muestraMock1 = mock(Muestra.class);
		muestraMock2 = mock(Muestra.class);
		muestraMock3 = mock(Muestra.class);
		muestraMock4 = mock(Muestra.class);
		
		muestras = Arrays.asList(muestraMock1,muestraMock2, muestraMock3, muestraMock4);
		
		filtroInsecto = new FiltroTipoInsecto(NoVinchuca.PhtiaChinche);
		filtroVerificacion = new FiltroTipoVerificacion(NivelDeVerificacion.VERIFICADA);
		filtroFechaCreacion = new FiltroFechaCreacion(fecha);
		filtroFechaUltimaVotacion = new FiltroFechaUltimaVotacion(fecha);
		filtroOR = new FiltroOR();
		filtroAND = new FiltroAND();
		
		filtroOR.addFilter(filtroFechaUltimaVotacion);
		filtroOR.addFilter(filtroInsecto);
		
		filtroAND.addFilter(filtroFechaUltimaVotacion);
		filtroAND.addFilter(filtroInsecto);
	}
	 
	 @Test
	 void unFiltroCompuestoPuedeEliminarUnFiltroQueEstaContenido() {
		 
		 filtroOR.addFilter(filtroFechaCreacion);
		 filtroOR.deleteFilter(filtroFechaCreacion);
		 
		 assertFalse(filtroOR.containsFilter(filtroFechaCreacion));
	 }
	
	@Test
	void puedoFiltrarMuestrasConElOperadorOR() {
		//Setup
		this.mockedEsInsectoMethods();
		this.mockedFueVotadaEnMethod();
		
		List<Muestra> filtradas = filtroOR.filter(muestras);
		
		muestras.forEach(muestra -> verify(muestra).esInsecto(NoVinchuca.PhtiaChinche));
		muestras.forEach(muestra -> verify(muestra).fueVotadaEn(fecha));
		assertFalse(filtradas.contains(muestraMock1));
		assertEquals(filtradas.size(),3,0);
	}

	@Test
	void puedoFiltrarMuestrasConElOperadorORConSubFiltrosCompuestos() {
		//Setup
		this.mockedGetVerificicacionActualMethods();
		this.mockedEsInsectoMethods();
		this.mockedFueVotadaEnMethod();
		
		FiltroCompuesto otherFiltroOR = new FiltroOR();
		otherFiltroOR.addFilter(filtroVerificacion);
		otherFiltroOR.addFilter(filtroAND);
		
		List<Muestra> filtradas = otherFiltroOR.filter(muestras);
		
		muestras.forEach(muestra -> verify(muestra).getVerificacionActual());
		muestras.forEach(muestra -> verify(muestra).fueVotadaEn(fecha));
		verify(muestraMock2).esInsecto(NoVinchuca.PhtiaChinche);
		
		assertTrue(filtradas.containsAll(Arrays.asList(muestraMock1, muestraMock4, muestraMock2)));
		assertEquals(filtradas.size(),3,0);
	}
	
	@Test
	void puedoFiltrarMuestrasConElOperadorAND() {
		//Setup
		this.mockedEsInsectoMethods();
		this.mockedFueVotadaEnMethod();
		
		List<Muestra> filtradas = filtroAND.filter(muestras);
		
		muestras.forEach(muestra -> verify(muestra).fueVotadaEn(fecha));
		
		verify(muestraMock2).esInsecto(NoVinchuca.PhtiaChinche);
		verify(muestraMock3).esInsecto(NoVinchuca.PhtiaChinche);
		
		assertTrue(filtradas.contains(muestraMock2));
		assertEquals(filtradas.size(),1,0);
	}
	
	@Test
	void puedoFiltrarMuestrasPorTipoDeInsecto() {
		//Setup
		this.mockedEsInsectoMethods();
		
		//Exercise
		List<Muestra> filtradas = filtroInsecto.filter(muestras);
		
		//Verify/Assert
		muestras.forEach(muestra -> verify(muestra).esInsecto(NoVinchuca.PhtiaChinche));
		assertTrue(filtradas.containsAll(Arrays.asList(muestraMock2, muestraMock4)));
		assertEquals(filtradas.size(),2,0);
	}
	
	@Test
	void puedoFiltrarMuestrasPorFechaDeCreacion() {
		//Setup
		this.mockedGetFechaCreacionMethods();
		
		//Exercise
		List<Muestra> filtradas = filtroFechaCreacion.filter(muestras);
		
		//Verify/Assert
		muestras.forEach(muestra -> verify(muestra).getFechaCreacion());
		assertTrue(filtradas.containsAll(Arrays.asList(muestraMock1, muestraMock2, muestraMock4)));
		assertEquals(filtradas.size(),3,0);
	}


	
	@Test
	void puedoFiltrarMuestrasPorFechaDeUltimaVotacion() {
		//Setup
		this.mockedFueVotadaEnMethod();
		
		//Exercise
		List<Muestra> filtradas = filtroFechaUltimaVotacion.filter(muestras);
		
		//Verify/Assert
		muestras.forEach(muestra -> verify(muestra).fueVotadaEn(fecha));
		assertTrue(filtradas.containsAll(Arrays.asList(muestraMock2, muestraMock3)));
		assertEquals(filtradas.size(),2,0);
	}
	
	@Test
	void puedoFiltrarMuestrasPorVerificacionActual() {	
		//Setup
		mockedGetVerificicacionActualMethods();
		
		//Exercise
		List<Muestra> filtradas = filtroVerificacion.filter(muestras);
		
		//Verify/Assert
		muestras.forEach(muestra -> verify(muestra).getVerificacionActual());
		assertTrue(filtradas.containsAll(Arrays.asList(muestraMock1, muestraMock4)));
		assertEquals(filtradas.size(),2,0);
	}

	private void mockedFueVotadaEnMethod() {
		when(muestraMock1.fueVotadaEn(fecha)).thenReturn(false);
		when(muestraMock2.fueVotadaEn(fecha)).thenReturn(true);
		when(muestraMock3.fueVotadaEn(fecha)).thenReturn(true);
		when(muestraMock4.fueVotadaEn(fecha)).thenReturn(false);
	}

	private void mockedEsInsectoMethods() {
		when(muestraMock1.esInsecto(NoVinchuca.PhtiaChinche)).thenReturn(false);
		when(muestraMock2.esInsecto(NoVinchuca.PhtiaChinche)).thenReturn(true);
		when(muestraMock3.esInsecto(NoVinchuca.PhtiaChinche)).thenReturn(false);
		when(muestraMock4.esInsecto(NoVinchuca.PhtiaChinche)).thenReturn(true);
	}
	
	private void mockedGetFechaCreacionMethods() {
		when(muestraMock1.getFechaCreacion()).thenReturn(fecha);
		when(muestraMock2.getFechaCreacion()).thenReturn(fecha);
		when(muestraMock3.getFechaCreacion()).thenReturn(LocalDate.of(2022, 6, 16));
		when(muestraMock4.getFechaCreacion()).thenReturn(fecha);
	}

	private void mockedGetVerificicacionActualMethods() {
		when(muestraMock1.getVerificacionActual()).thenReturn(NivelDeVerificacion.VERIFICADA);
		when(muestraMock2.getVerificacionActual()).thenReturn(NivelDeVerificacion.NO_VERIFICADA);
		when(muestraMock3.getVerificacionActual()).thenReturn(NivelDeVerificacion.VERIFICADA_PARCIAL);
		when(muestraMock4.getVerificacionActual()).thenReturn(NivelDeVerificacion.VERIFICADA);
	}

}
