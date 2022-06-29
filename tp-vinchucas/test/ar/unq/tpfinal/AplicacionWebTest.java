package ar.unq.tpfinal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.unq.tpfinal.filtro.IFiltro;
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
	ZonaDeCobertura zonaMock2;
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
		zonaMock2 = mock(ZonaDeCobertura.class);
		userMock = mock(Usuario.class);
		userMock2 = mock(Usuario.class);
		
		//Mockeamos los metodos en común.
		when(muestraMock1.getUsuario()).thenReturn(userMock);
		when(muestraMock2.getUsuario()).thenReturn(userMock2);
		when(muestraMock3.getUsuario()).thenReturn(userMock);
		when(muestraMock1.fuePublicadaDentroDeEsteRango(any(LocalDate.class), any(LocalDate.class))).thenReturn(true);
		when(muestraMock2.fuePublicadaDentroDeEsteRango(any(LocalDate.class), any(LocalDate.class))).thenReturn(false);
		when(muestraMock3.fuePublicadaDentroDeEsteRango(any(LocalDate.class), any(LocalDate.class))).thenReturn(true);
		
		app.agregarMuestra(muestraMock1);
		app.agregarMuestra(muestraMock2);
		app.agregarMuestra(muestraMock3);
		
	}
	
	@AfterEach
	void cleanUp() {
		app.setMuestras(new ArrayList<Muestra>());
		app.setZonasDeCobertura(new ArrayList<ZonaDeCobertura>());
	}
	
	@Test
	void cuandoSeAgregaUnaMuestraSeNotificaALasZonaQueLaContienen() {
		app.agregarZona(zonaMock);
		app.agregarZona(zonaMock2);
		
		when(zonaMock.contieneMuestra(muestraMock1)).thenReturn(false);
		when(zonaMock2.contieneMuestra(muestraMock1)).thenReturn(true);
		
		app.eliminarMuestra(muestraMock1);
		app.agregarMuestra(muestraMock1);
		
		app.zonasDeLaMuestra(muestraMock1).forEach(zona -> verify(zona).notificar(muestraMock1, Aspecto.MUESTRA_ENVIADA));
	}
	
	@Test
	void puedoObtenerLasZonasEnLasQueEstaUnaMuestra() {
		app.agregarZona(zonaMock);
		app.agregarZona(zonaMock2);
		
		when(zonaMock.contieneMuestra(muestraMock1)).thenReturn(false);
		when(zonaMock2.contieneMuestra(muestraMock1)).thenReturn(true);
		
		List<ZonaDeCobertura> zonas = app.zonasDeLaMuestra(muestraMock1);
		
		assertEquals(zonas.size(), 1, 0);
		assertTrue(zonas.contains(zonaMock2));
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
		
		List<Muestra> muestrasFiltradas = app.obtenerMuestrasHace(5);
		
		muestras.forEach(muestra -> verify(muestra).fuePublicadaDentroDeEsteRango(fechaInicio, fechaDeHoy));
	
		assertEquals(muestrasFiltradas.size(), 2, 0);
		assertTrue(muestrasFiltradas.containsAll(Arrays.asList(muestraMock1, muestraMock3)));
	}
	
	@Test
	void puedoObtenerLaCantidadDeMuestrasEnviadasPorUnUsuarioEnUnaListaDeMuestras() {
		when(muestraMock1.fueEnviadaPor(userMock)).thenReturn(true);
		when(muestraMock3.fueEnviadaPor(userMock)).thenReturn(true);
		
		int cantidadEsperada = app.cantidadDeEnviosDeHace(userMock, 30);
		
		Arrays.asList(muestraMock1, muestraMock3)
			.forEach(muestra -> verify(muestra)
					.fueEnviadaPor(userMock));
		assertEquals(cantidadEsperada, 2, 0);
	}
	
	@Test
	void puedoObtenerLaCantidadDeOpinionesPorUnUsuarioEnUnaListaDeMuestras() {
		when(muestraMock1.elUsuarioYaOpino(userMock)).thenReturn(true);
		when(muestraMock3.elUsuarioYaOpino(userMock)).thenReturn(true);
		
		int cantidadEsperada = app.cantidadDeOpinionesDeHace(userMock, 30);
		
		Arrays.asList(muestraMock1, muestraMock3)
			.forEach(muestra -> verify(muestra)
				.elUsuarioYaOpino(userMock));
		
		assertEquals(cantidadEsperada, 2, 0);
	}
	
	@Test
	void puedoAgregarUnaOpinionAUnaMuestraAlmacenada() {
		when(opMock.getUsuario()).thenReturn(userMock);
		app.agregarOpinionA(muestraMock1, opMock);
		
		verify(muestraMock1).agregarOpinion(opMock);
		verify(muestraMock1).notificarValidacionSiCorresponde(anyList());
	}
	
	@Test
	void puedoBuscarMuestrasConUnFiltro() {
		IFiltro filtroMock = mock(IFiltro.class);
		
		app.buscar(filtroMock);
		
		verify(filtroMock).filter(muestras);
	}
	
	@Test
	void puedoAgregarUnaMuestra() {
		assertTrue(app.getMuestras().containsAll(muestras));
	}
	
	@Test
	void puedoSaberSiUnaMuestraEstaAlmacenada() {
		assertTrue(app.contieneMuestra(muestraMock1));
	}
	
	@Test
	void puedoSaberSiUnaZonaEstaAlmacenada() {
		app.agregarZona(zonaMock);
		assertTrue(app.contieneMuestra(muestraMock1));
	}
	
	@Test
	void puedoAgregarUnaZonaDeCobertura() {
		app.agregarZona(zonaMock);
		
		assertTrue(app.contieneZona(zonaMock));
	}
	
	@Test
	void puedoEliminarUnaZonaDeCoberturaAlmacenada() {
		app.agregarZona(zonaMock);
		app.eliminarZona(zonaMock);
		
		assertFalse(app.contieneZona(zonaMock));
	}
	
	@Test
	void puedoEliminarUnaMuestraAlmacenada() {
		app.eliminarMuestra(muestraMock1);
		
		assertFalse(app.contieneMuestra(muestraMock1));
	}
	
	@Test
	void noPuedoEliminarUnaMuestraQueNoEstaAlmacenada() {
		int sizeEsperado = app.getMuestras().size();
		Muestra muestraQueNoEsta = mock(Muestra.class);
		
		app.eliminarMuestra(muestraQueNoEsta);
		
		assertEquals(sizeEsperado, app.getMuestras().size(), 0);
		assertFalse(app.contieneMuestra(muestraQueNoEsta));
	}
	
	@Test
	void noPuedoEliminarUnaZonaQueNoEstaAlmacenada() {
		app.agregarZona(zonaMock);
		ZonaDeCobertura zonaQueNoEsta = mock(ZonaDeCobertura.class);
		
		int sizeEsperado = app.getZonasDeCobertura().size();
		
		app.eliminarZona(zonaQueNoEsta);
		
		assertEquals(sizeEsperado, app.getZonasDeCobertura().size(), 0);
		assertFalse(app.contieneZona(zonaQueNoEsta));
	}
	
	@Test
	void noPuedoAgregarUnaZonaQueYaEstaAlmacenada() {
		app.agregarZona(zonaMock);
		
		int sizeEsperado = app.getZonasDeCobertura().size();
		
		app.agregarZona(zonaMock);
		
		assertEquals(sizeEsperado, app.getZonasDeCobertura().size(), 0);
	}
	
	@Test
	void noPuedoMuestraUnaZonaQueYaEstaAlmacenada() {
		
		int sizeEsperado = app.getMuestras().size();
		
		app.agregarMuestra(muestraMock1);
		
		assertEquals(sizeEsperado, app.getMuestras().size(), 0);
	}
	
	

}