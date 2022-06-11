package ar.unq.tpfinal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

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
		when(muestraMock1.elUsuarioYaOpino(userMock)).thenReturn(true);
		when(muestraMock2.elUsuarioYaOpino(userMock)).thenReturn(false);
		when(muestraMock3.elUsuarioYaOpino(userMock)).thenReturn(true);
		
		int cantidadEsperada = app.cantidadDeOpinionesDe(userMock, muestras);
		
		muestras.forEach(muestra -> verify(muestra)
				.elUsuarioYaOpino(userMock));
		
		assertEquals(cantidadEsperada, 2, 0);
	}
	
	@Test
	void puedoAgregarUnaOpinionAUnaMuestraAlmacenada() {
		when(opMock.getUsuario()).thenReturn(userMock);
		app.agregarOpinionA(muestraMock1, opMock);
		
		verify(muestraMock1).agregarOpinion(opMock);
		verify(opMock).getUsuario();
	}
	
	@Test
	void puedoBuscarMuestrasConUnFiltro() {
		IFiltro filtroMock = mock(IFiltro.class);
		
		app.buscar(filtroMock);
		
		verify(filtroMock).filter(muestras);
	}
	
	@Test
	void puedoBajarElNivelDeConocimientoDeUnUsuarioSiCorresponde() {
		Muestra envioMockeado = mock(Muestra.class);
		
		//Mockeo los valores del envio mockeado.
		when(envioMockeado.fueEnviadaPor(userMock))
			.thenReturn(true);
		when(envioMockeado.getUsuario())
			.thenReturn(userMock);
		when(envioMockeado.elUsuarioYaOpino(userMock))
		.thenReturn(false);
		when(envioMockeado.fuePublicadaDentroDeEsteRango(any(LocalDate.class), any(LocalDate.class)))
			.thenReturn(true);
		
		app.agregarMuestra(envioMockeado);
		
		verify(userMock, atLeastOnce()).bajarDeNivel();
	}
	
	@Test
	void puedoSubirElNivelDeConocimientoDeUnUsuarioSiCorresponde() {
		//Lleno la app con muestras enviadas 
		for(int i = 0; i < 31; i++){
			Muestra envioMockeado = mock(Muestra.class);
			Muestra muestraConOpinionMockeada = mock(Muestra.class);
			
			//Mockeo los valores del envio mockeado.
			when(envioMockeado.fueEnviadaPor(userMock))
				.thenReturn(true);
			when(envioMockeado.getUsuario())
				.thenReturn(userMock);
			when(envioMockeado.elUsuarioYaOpino(userMock))
			.thenReturn(false);
			when(envioMockeado.fuePublicadaDentroDeEsteRango(any(LocalDate.class), any(LocalDate.class)))
				.thenReturn(true);
			
			//Mockeo los valores del envio mockeado.
			when(muestraConOpinionMockeada.fueEnviadaPor(userMock))
				.thenReturn(false);
			when(muestraConOpinionMockeada.getUsuario())
				.thenReturn(userMock2);
			when(muestraConOpinionMockeada.elUsuarioYaOpino(userMock))
			.thenReturn(true);
			when(muestraConOpinionMockeada.fuePublicadaDentroDeEsteRango(any(LocalDate.class), any(LocalDate.class)))
				.thenReturn(true);
			
			app.agregarMuestra(envioMockeado);
			app.agregarMuestra(muestraConOpinionMockeada);
		};
		
		//Verifico que se le suba el nivel al usuario
		verify(userMock,atLeastOnce()).subirDeNivel();
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