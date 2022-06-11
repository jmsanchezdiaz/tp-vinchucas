package ar.unq.tpfinal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;

public class AplicacionWebTest {
	AplicacionWeb app;
	Muestra muestraMock1;
	Muestra muestraMock2;
	Muestra muestraMock3;
	List<Muestra> muestras;
	Opinion opMock;
	ZonaDeCobertura zonaMock;
	Usuario userMock;
	Usuario userMock2;
	
	@BeforeEach
	void setUp(){
		app = AplicacionWeb.getAplicacionWeb();
		muestraMock1 = mock(Muestra.class);
		muestraMock2 = mock(Muestra.class);
		muestraMock3 = mock(Muestra.class);
		muestras = Arrays.asList(muestraMock1,muestraMock2,muestraMock3);
		opMock = mock(Opinion.class);
		zonaMock = mock(ZonaDeCobertura.class);
		userMock = mock(Usuario.class);
		userMock2 = mock(Usuario.class);
		
		when(muestraMock1.getUsuario()).thenReturn(userMock);
		when(muestraMock2.getUsuario()).thenReturn(userMock2);
		when(muestraMock3.getUsuario()).thenReturn(userMock);
		
		muestras.forEach(muestra -> app.agregarMuestra(muestra));
		
	}
	
	@Test
	void solamenteHayUnaInstanciaDeAplicacionWeb() {
		AplicacionWeb app2 = AplicacionWeb.getAplicacionWeb();
		
		assertEquals(app, app2);
	}
	
	@Test
	void puedoObtenerLasMuestrasDeLosUltimosDiasQueYoLePase() {
		LocalDate fechaDeHoy = LocalDate.now();
		LocalDate fechaInicio = fechaDeHoy.minusDays(5);
		
		when(muestraMock1.fuePublicadaDentroDeEsteRango(fechaInicio, fechaDeHoy)).thenReturn(true);
		when(muestraMock2.fuePublicadaDentroDeEsteRango(fechaInicio, fechaDeHoy)).thenReturn(false);
		when(muestraMock3.fuePublicadaDentroDeEsteRango(fechaInicio, fechaDeHoy)).thenReturn(true);
		
		List<Muestra> muestrasFiltradas = app.obtenerMuestrasHace(5);
		
		muestras.forEach(muestra -> verify(muestra).fuePublicadaDentroDeEsteRango(fechaInicio, fechaDeHoy));
	
		assertEquals(muestrasFiltradas.size(), 2, 0);
		assertTrue(muestrasFiltradas.contains(muestraMock1) && muestrasFiltradas.contains(muestraMock3));
	}
	
	@Test
	void puedoObtenerLaCantidadDeMuestrasEnviadasPorUnUsuarioEnUnaListaDeMuestras() {
		when(muestraMock1.fueEnviadaPor(userMock)).thenReturn(true);
		when(muestraMock2.fueEnviadaPor(userMock)).thenReturn(false);
		when(muestraMock3.fueEnviadaPor(userMock)).thenReturn(true);
		
		int cantidadEsperada = app.cantidadDeEnviosDe(userMock, muestras);
		
		muestras.forEach(muestra -> verify(muestra)
				.fueEnviadaPor(userMock));
		assertEquals(cantidadEsperada, 2, 0);
	}
	
	@Test
	void puedoObtenerLaCantidadDeOpinionesPorUnUsuarioEnUnaListaDeMuestras() {
		
	}
	
	@Test
	void puedoActualizarElNivelDeConocimientoDeUnUsuarioSiCorresponde() {
		
	}
	
	@Test
	void puedoAñadirUnaMuestra() {
		assertTrue(app.getMuestras().containsAll(muestras));
	}
	
	@Test
	void puedoAñadirUnaZonaDeCobertura() {
		app.agregarZona(zonaMock);
		
		assertTrue(app.getZonasDeCobertura().contains(zonaMock));
	}
	
	@Test
	void puedoEliminarUnaZonaDeCoberturaAlmacenada() {
		app.eliminarZona(zonaMock);
		
		assertFalse(app.getZonasDeCobertura().contains(zonaMock));
	}
	
	@Test
	void puedoEliminarUnaMuestraAlmacenada() {
		app.eliminarMuestra(muestraMock1);
		
		assertFalse(app.getMuestras().contains(muestraMock1));
	}

}