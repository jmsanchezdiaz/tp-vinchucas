package ar.unq.tpfinal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
	Opinion opMock;
	ZonaDeCobertura zonaMock;
	Usuario userMock;
	
	@BeforeEach
	void setUp(){
		app = AplicacionWeb.getAplicacionWeb();
		muestraMock1 = mock(Muestra.class);
		muestraMock2 = mock(Muestra.class);
		muestraMock3 = mock(Muestra.class);
		opMock = mock(Opinion.class);
		zonaMock = mock(ZonaDeCobertura.class);
		userMock = mock(Usuario.class);
	}
	
	@Test
	void solamenteHayUnaInstanciaDeAplicacionWeb() {
		
		AplicacionWeb app2 = AplicacionWeb.getAplicacionWeb();
		
		assertEquals(app, app2);
	}
	
	@Test
	void alInicioUnaAplicacionWebNoTieneZonasDeCoberturasAlmacenadasNiMuestras() {
		assertTrue(app.getMuestras().isEmpty());
		assertTrue(app.getZonasDeCobertura().isEmpty());
	}
	
	@Test
	void puedoObtenerLasMuestrasDeLosUltimosDiasQueYoLePase() {
		LocalDate fechaInicio = LocalDate.now().minusDays(5);
		LocalDate fechaFin = LocalDate.now();
		
		when(muestraMock1.getUsuario()).thenReturn(userMock);
		when(muestraMock2.getUsuario()).thenReturn(userMock);
		when(muestraMock3.getUsuario()).thenReturn(userMock);
		when(muestraMock1.fuePublicadaDentroDeEsteRango(fechaInicio, fechaFin)).thenReturn(true);
		when(muestraMock2.fuePublicadaDentroDeEsteRango(fechaInicio, fechaFin)).thenReturn(false);
		when(muestraMock3.fuePublicadaDentroDeEsteRango(fechaInicio, fechaFin)).thenReturn(true);
		
		List<Muestra> muestras = Arrays.asList(muestraMock1,muestraMock2,muestraMock3);
		
		muestras.forEach(muestra -> app.agregarMuestra(muestra));
		
		List<Muestra> muestrasFiltradas = app.obtenerMuestrasHace(5);
		
		muestras.forEach(muestra -> verify(muestra)
				.fuePublicadaDentroDeEsteRango(fechaInicio, fechaFin));
		
		assertEquals(muestrasFiltradas.size(), 2);
		assertTrue(muestrasFiltradas.contains(muestraMock1) && muestrasFiltradas.contains(muestraMock3));
	}
	
	@Test
	void puedoObtenerLaCantidadDeMuestrasEnviadasPorUnUsuarioEnUnaListaDeMuestras() {
		
	}
	
	@Test
	void puedoObtenerLaCantidadDeOpinionesPorUnUsuarioEnUnaListaDeMuestras() {
		
	}
	
	@Test
	void puedoActualizarElNivelDeConocimientoDeUnUsuarioSiCorresponde() {
		
	}
	
	@Test
	void puedoAñadirUnaMuestra() {
		when(muestraMock1.getUsuario()).thenReturn(userMock);
		app.agregarMuestra(muestraMock1);
		
		assertTrue(app.getMuestras().contains(muestraMock1));
	}
	
	@Test
	void puedoAñadirUnaZonaDeCobertura() {
		app.agregarZona(zonaMock);
		
		assertTrue(app.getZonasDeCobertura().contains(zonaMock));
	}
	
	@Test
	void puedoEliminarUnaZonaDeCoberturaAlmacenada() {
		app.agregarZona(zonaMock);
		app.eliminarZona(zonaMock);
		
		assertFalse(app.getZonasDeCobertura().contains(zonaMock));
	}
	
	@Test
	void puedoEliminarUnaMuestraAlmacenada() {
		when(muestraMock1.getUsuario()).thenReturn(userMock);
		
		app.agregarMuestra(muestraMock1);
		app.eliminarMuestra(muestraMock1);
		
		assertFalse(app.getMuestras().contains(muestraMock1));
	}
	
}