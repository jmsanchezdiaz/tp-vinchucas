package ar.unq.tpfinal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;

public class AplicacionWebTest {
	AplicacionWeb app;
	Muestra muestraMock1;
	Muestra muestraMock2;
	Muestra muestraMock3;
	Opinion opMock;
	ZonaDeCobertura zonaMock;
	
	@BeforeEach
	void setUp(){
		app = AplicacionWeb.getAplicacionWeb();
		muestraMock1 = mock(Muestra.class);
		muestraMock2 = mock(Muestra.class);
		muestraMock3 = mock(Muestra.class);
		opMock = mock(Opinion.class);
		zonaMock = mock(ZonaDeCobertura.class);
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
		app.agregarMuestra(muestraMock1);
		app.eliminarMuestra(muestraMock1);
		
		assertFalse(app.getMuestras().contains(muestraMock1));
	}
	
}