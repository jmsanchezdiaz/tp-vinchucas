package ar.unq.tpfinal.niveldeconocimiento;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.muestra.Muestra;

public class NivelDeConocimientoTest {
	Usuario userMock;
	AplicacionWeb appMock;
	Muestra muestraMock;
	Opinion opMock;
	
	ExpertoPermanente permanente;
	Experto experto;
	Basico basico;
	
	@BeforeEach
	void setUp() {
		permanente = new ExpertoPermanente();
		experto = new Experto();
		basico = new Basico();
		userMock = mock(Usuario.class);
		appMock = mock(AplicacionWeb.class);
		muestraMock = mock(Muestra.class);
		opMock = mock(Opinion.class);

	}
	
	@Test
	void unEstadoPermanentePuedeDelegarElAgregarUnaMuestra() {
		permanente.enviarMuestra(appMock, userMock, muestraMock);
		
		verify(appMock).agregarMuestra(muestraMock);
	}
	
	@Test
	void unEstadoPermanentePuedeDelegarElOpinarUnaMuestra() {
		permanente.opinarMuestra(appMock, userMock, muestraMock, opMock);
		
		verify(appMock).agregarOpinionA(muestraMock, opMock);
	}
	
	@Test
	void unEstadoPermanentePuedeOpinarEnMuestrasParcialmenteVerificadas() {
		assertTrue(permanente.puedeOpinarEnMuestraParcialmenteVerificada());
	}
	
	@Test
	void unEstadoExpertoPuedeDelegarElAgregarUnaMuestraYSiDebeBajarDeNivelLoHace() {
		mockDeEstadoConCondicionesParaBajarDeNivel();

		experto.enviarMuestra(appMock, userMock, muestraMock);
		
		verify(appMock).agregarMuestra(muestraMock);
		verify(userMock).setNivelDeConocimiento(any(Basico.class));
	}
	
	@Test
	void unEstadoExpertoPuedeDelegarElOpinarUnaMuestraYSiDebeBajarDeNivelLoHace() {
		mockDeEstadoConCondicionesParaBajarDeNivel();
		
		experto.opinarMuestra(appMock, userMock, muestraMock, opMock);
		
		verify(appMock).agregarOpinionA(muestraMock, opMock);
		verify(userMock).setNivelDeConocimiento(any(Basico.class));
	}
	
	@Test
	void unEstadoExpertoPuedeOpinarEnMuestrasParcialmenteVerificadas() {
		assertTrue(experto.puedeOpinarEnMuestraParcialmenteVerificada());
	}
	
	@Test
	void unEstadoBasicoPuedeDelegarElAgregarUnaMuestraYSiDebeSubirDeNivelLoHace() {
		mockDeEstadoConCondicionesParaSubirDeNivel();

		basico.enviarMuestra(appMock, userMock, muestraMock);
		
		verify(appMock).agregarMuestra(muestraMock);
		verify(userMock).setNivelDeConocimiento(any(Experto.class));
	}
	
	@Test
	void unEstadoBasicoPuedeDelegarElOpinarUnaMuestraYSiDebeSubirDeNivelLoHace() {
		mockDeEstadoConCondicionesParaSubirDeNivel();
		basico.opinarMuestra(appMock, userMock, muestraMock, opMock);
		
		verify(appMock).agregarOpinionA(muestraMock, opMock);
		verify(userMock).setNivelDeConocimiento(any(Experto.class));
	}
	
	@Test
	void unEstadoBasicoPuedeOpinarEnMuestrasParcialmenteVerificadas() {
		assertFalse(basico.puedeOpinarEnMuestraParcialmenteVerificada());
	}
	
	void mockDeEstadoConCondicionesParaBajarDeNivel() {
		when(appMock.cantidadDeEnviosDeHace(userMock, 30)).thenReturn(5);
		when(appMock.cantidadDeOpinionesDeHace(userMock, 30)).thenReturn(34);
	}
	
	void mockDeEstadoConCondicionesParaSubirDeNivel() {
		when(appMock.cantidadDeEnviosDeHace(userMock, 30)).thenReturn(15);
		when(appMock.cantidadDeOpinionesDeHace(userMock, 30)).thenReturn(34);
	}
	
	
}
