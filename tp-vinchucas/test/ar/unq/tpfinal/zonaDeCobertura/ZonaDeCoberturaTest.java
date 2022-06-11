package ar.unq.tpfinal.zonaDeCobertura;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.Aspecto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.Observador;
import ar.unq.tpfinal.ubicacion.Ubicacion;

public class ZonaDeCoberturaTest {

	private ZonaDeCobertura zona1;
	private ZonaDeCobertura zona2;
	private ZonaDeCobertura zona3;
	private Observador observadorMock1;
	private Observador observadorMock2;
	private Muestra muestraMock1;

	@BeforeEach
	void setUp() throws Exception {

		zona1 = new ZonaDeCobertura("nombreDeEjemplo", 2000.0, new Ubicacion(20, 20));
		zona2 = new ZonaDeCobertura("nombreDeEjemplo2", 2000.0, new Ubicacion(20, 20.001));
		zona3 = new ZonaDeCobertura("nombreDeEjemplo3", 0.1, new Ubicacion(89, 120));

		muestraMock1 = mock(Muestra.class);

		observadorMock1 = mock(Observador.class);
		observadorMock2 = mock(Observador.class);
	}

	@Test
	void testDosZonasPuedenSolaparse() {
		assertTrue(zona1.esZonaSolapada(zona2));
	}

	@Test
	void testDosZonasPuedenNoSolaparse() {

		assertFalse(zona1.esZonaSolapada(zona3));

	}

	@Test
	void testNombreDeZona() {

		assertEquals("nombreDeEjemplo", zona1.getNombre());
	}

	@Test
	void testRadioDeZona() {

		assertEquals(2000.0, zona1.getRadio());
	}

	@Test
	void testEpicentroDeZona() throws Exception {

		assertTrue(zona1.getEpicentro().equals(new Ubicacion(20, 20)));

	}

	@Test
	void testSuscripcionAUnAspectoSinSuscriptores() {
		zona1.suscribir(observadorMock1, Aspecto.MUESTRA_ENVIADA);

		assertTrue(zona1.getSuscriptores().containsKey(Aspecto.MUESTRA_ENVIADA));
		assertTrue(zona1.getSuscriptores().get(Aspecto.MUESTRA_ENVIADA).contains(observadorMock1));
		assertEquals(1, zona1.getSuscriptores().get(Aspecto.MUESTRA_ENVIADA).size());
	}

	@Test
	void testSuscripcionAUnAspectoConSuscriptores() {
		zona1.suscribir(observadorMock1, Aspecto.MUESTRA_ENVIADA);

		zona1.suscribir(observadorMock2, Aspecto.MUESTRA_ENVIADA);
		assertTrue(zona1.getSuscriptores().containsKey(Aspecto.MUESTRA_ENVIADA));
		assertTrue(zona1.getSuscriptores().get(Aspecto.MUESTRA_ENVIADA).contains(observadorMock2));
		assertEquals(2, zona1.getSuscriptores().get(Aspecto.MUESTRA_ENVIADA).size());
	}

	@Test
	void testDesuscripcionAUnAspectoAlQueNoEstabaPreviamenteSuscrito() {
		zona1.suscribir(observadorMock1, Aspecto.MUESTRA_ENVIADA);

		zona1.desuscribir(observadorMock2, Aspecto.MUESTRA_ENVIADA);

		assertTrue(zona1.getSuscriptores().containsKey(Aspecto.MUESTRA_ENVIADA));
		assertFalse(zona1.getSuscriptores().get(Aspecto.MUESTRA_ENVIADA).contains(observadorMock2));
		assertEquals(1, zona1.getSuscriptores().get(Aspecto.MUESTRA_ENVIADA).size());
	}

	@Test
	void testNotificacionASuscriptores() {
		zona1.suscribir(observadorMock1, Aspecto.MUESTRA_ENVIADA);
		zona1.suscribir(observadorMock2, Aspecto.MUESTRA_ENVIADA);

		zona1.notificar(muestraMock1, Aspecto.MUESTRA_ENVIADA);

		verify(observadorMock1, times(1)).eventoEnMuestra(zona1, muestraMock1, Aspecto.MUESTRA_ENVIADA);
		verify(observadorMock2, times(1)).eventoEnMuestra(zona1, muestraMock1, Aspecto.MUESTRA_ENVIADA);

	}

	@Test
	void testMuestraContenidaEnLaZona() throws Exception {
		when(muestraMock1.getUbicacion()).thenReturn(new Ubicacion(20, 20.001));
		assertTrue(zona1.contieneMuestra(muestraMock1));

	}

	@Test
	void testMuestraNoContenidaEnLaZona() throws Exception {
		when(muestraMock1.getUbicacion()).thenReturn(new Ubicacion(1, 2));
		assertFalse(zona1.contieneMuestra(muestraMock1));

	}

	@Test
	void testZonasQueSeSolapan() {
		List<ZonaDeCobertura> zonas = new ArrayList<ZonaDeCobertura>();
		zonas.add(zona2);

		assertEquals(zona2, zona1.zonasQueSeSolapan(zonas).get(0));
		assertEquals(1, zona1.zonasQueSeSolapan(zonas).size());
	}

}
