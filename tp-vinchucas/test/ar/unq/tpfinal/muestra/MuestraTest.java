package ar.unq.tpfinal.muestra;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Console;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;
import ar.unq.tpfinal.Aspecto;
import ar.unq.tpfinal.Comentario;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.NivelDeVerificacion;
import ar.unq.tpfinal.Opinable;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.ResultadoEmpate;
import ar.unq.tpfinal.Vinchuca;

public class MuestraTest {

	Muestra muestra;
	Muestra muestraMock;
	
	Opinion opinionNingunaMock;
	Opinion opinionNingunaMock2;
	Opinion opinionImgPocoClaraMock;
	Opinion opinionVinchucaInfestans;
	
	Usuario userMock;
	Usuario userNormalMock;
	Usuario userExpertoMock;
	Usuario userExpertoMock2;
	
	Ubicacion ubiMock;
	Foto fotoMock;
	ZonaDeCobertura zonaMock;
	
	@BeforeEach
	void setUp() {

		opinionNingunaMock = mock(Opinion.class);
		opinionNingunaMock2 = mock(Opinion.class);
		opinionImgPocoClaraMock = mock(Opinion.class);
		opinionVinchucaInfestans = mock(Opinion.class);
		userMock = mock(Usuario.class);
		userNormalMock = mock(Usuario.class);
		userExpertoMock = mock(Usuario.class);
		userExpertoMock2 = mock(Usuario.class);
		ubiMock = mock(Ubicacion.class);
		fotoMock = mock(Foto.class);
		muestraMock = mock(Muestra.class);
		zonaMock = mock(ZonaDeCobertura.class);
		
		when(userNormalMock.esExperto()).thenReturn(false);
		when(userExpertoMock.esExperto()).thenReturn(true);
		when(userExpertoMock2.esExperto()).thenReturn(true);
		
		when(opinionVinchucaInfestans.getOpinion()).thenReturn(Vinchuca.VinchucaInfestans);
		when(opinionNingunaMock.getOpinion()).thenReturn(Comentario.NINGUNA);
		when(opinionNingunaMock2.getOpinion()).thenReturn(Comentario.NINGUNA);
		when(opinionImgPocoClaraMock.getOpinion()).thenReturn(Comentario.IMAGEN_POCO_CLARA);
		
		muestra = new Muestra(userMock, ubiMock, fotoMock, Vinchuca.VinchucaGuayasana);
	}
	
	@Test
	void unaMuestraAlInicioNoTieneOpiniones() {
		assertTrue(muestra.getOpiniones().isEmpty());
	}
	
	@Test
	void unUsuarioPuedeOpinarSiNadieOpino() {
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userMock);
		when(userMock.esExperto()).thenReturn(false);
		
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		assertTrue(!muestra.getOpiniones().isEmpty());
	}
	
	
	@Test
	void unUsuarioQueYaOpinoNoPuedeVolverOpinar() {
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userNormalMock);
		when(opinionNingunaMock.getUsuario()).thenReturn(userNormalMock);
		
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		muestra.agregarOpinion(opinionNingunaMock);
		assertTrue(muestra.getOpiniones().size() == 1);
	}
	
	@Test
		void unaMuestraVerificadaNoPuedeSerOpinada() {
			when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);
			when(opinionNingunaMock2.getUsuario()).thenReturn(userExpertoMock2);
			when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userMock);
			
			muestra.agregarOpinion(opinionNingunaMock);
			muestra.agregarOpinion(opinionNingunaMock2);
			muestra.agregarOpinion(opinionImgPocoClaraMock);
			
			assertTrue(muestra.getOpiniones().size() == 2);
		}
	
	@Test
	void siOpinoUnExpertoNoPuedeOpinarUnUsuarioNormal() {
		
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userExpertoMock);
		when(opinionNingunaMock.getUsuario()).thenReturn(userNormalMock);
		
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		muestra.agregarOpinion(opinionNingunaMock);

		assertTrue(muestra.getOpiniones().size() == 1);
	}
	
	@Test
	void elResultadoDeUnaMuestraCuandoEstaVaciaEsNoDefinido() {
		assertTrue(muestra.getResultadoActual() == ResultadoEmpate.NO_DEFINIDO);
	}
	
	@Test
	void elResultadoDeUnaMuestraEsLaOpinionMasVotada() {
		
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userExpertoMock);		
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		
		assertTrue(muestra.getResultadoActual() == Comentario.IMAGEN_POCO_CLARA);
	}
	
	@Test
	void elResultadoDeUnaMuestraEsNoDefinidoCuandoHayEmpate() {
		
		when(opinionVinchucaInfestans.getUsuario()).thenReturn(userMock);	
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userNormalMock);		
		when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);	
		
		muestra.agregarOpinion(opinionVinchucaInfestans);
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		muestra.agregarOpinion(opinionNingunaMock);
		
		assertTrue(muestra.getResultadoActual() == ResultadoEmpate.NO_DEFINIDO);
	}
	
	@Test
	void unaMuestraSabeCuandoEsVerificada() {
		
		when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);		
		when(opinionNingunaMock2.getUsuario()).thenReturn(userExpertoMock2);	
		
		muestra.agregarOpinion(opinionNingunaMock);
		muestra.agregarOpinion(opinionNingunaMock2);
		
		assertTrue(muestra.esMuestraVerificada());
	}
	
	@Test
	void unaMuestraSabeCuandoEsVerificadaParcial() {
		
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userExpertoMock);		
		
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		
		assertTrue(muestra.getVerificacionActual() == NivelDeVerificacion.VERIFICADA_PARCIAL);
	}
	
	@Test
	void unaMuestraSabeCuandoEsNoVerificada() {
		
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userNormalMock);		
		
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		
		assertTrue(muestra.getVerificacionActual() == NivelDeVerificacion.NO_VERIFICADA);
	}
	
	@Test
	void unaMuestraPuedeHacerUnMapYcontarOpiniones() {
		List<Opinion> listaDeOpiniones = new ArrayList<>();
		listaDeOpiniones.add(opinionImgPocoClaraMock);
		listaDeOpiniones.add(opinionImgPocoClaraMock);
		
		assertEquals(2, muestra.contarOpiniones(listaDeOpiniones).get(Comentario.IMAGEN_POCO_CLARA));
	}
	
	@Test
	void unaMuestraSabeSiElResultadoEsUnInsecto() {
		
		when(opinionVinchucaInfestans.getUsuario()).thenReturn(userNormalMock);	
		muestra.agregarOpinion(opinionVinchucaInfestans);
		
		assertTrue(muestra.esInsecto(Vinchuca.VinchucaInfestans));
	}
	
	@Test
	void unaMuestraSabeSiNoElResultadoEsUnInsecto() {
		
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userNormalMock);	
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		
		assertFalse(muestra.esInsecto(Vinchuca.VinchucaInfestans));
	}
	
	
	@Test
	void unaMuestraSabeSiFueVotadaEnCiertaFecha() {
		
		LocalDate fecha = LocalDate.of(2021, 5, 11);
		
		when(opinionVinchucaInfestans.getUsuario()).thenReturn(userNormalMock);
		when(opinionVinchucaInfestans.getFechaCreacion()).thenReturn(fecha);	
		muestra.agregarOpinion(opinionVinchucaInfestans);
		
		assertTrue(muestra.fueVotadaEn(fecha));
	}
	
	@Test
	void unaMuestraSabeSiFueVotadaEnCiertoRango() {
		
		LocalDate fecha1 = LocalDate.of(2030, 5, 11);
		LocalDate fecha2 = LocalDate.of(2000, 10, 11);
		
		muestra.setFechaCreacion(LocalDate.of(2019, 7, 11));

		assertTrue(muestra.fuePublicadaDentroDeEsteRango(fecha1, fecha2));
	}
	
	@Test
	void unaMuestraSabeSiNoFueVotadaEnCiertoRango() {
		
		LocalDate fecha1 = LocalDate.of(2030, 5, 11);
		LocalDate fecha2 = LocalDate.of(2000, 10, 11);
		
		muestra.setFechaCreacion(LocalDate.of(2031, 7, 11));

		assertFalse(muestra.fuePublicadaDentroDeEsteRango(fecha1, fecha2));
	}
	
	@Test
	void unaMuestraSabeSiUnaMuestraEsDelUsuarioDado() {
		
		assertTrue(muestra.fueEnviadaPor(userMock));
	}
	
	@Test
	void unaMuestraConoceSuEspecie() {
		assertTrue(muestra.getEspecie() == Vinchuca.VinchucaGuayasana);;
	}
	
	@Test
	void unaMuestraConoceSuFoto() {
		assertTrue(muestra.getFoto() == fotoMock);;
	}
	
	@Test
	void unaMuestraConoceSuUsuario() {
		assertTrue(muestra.getUsuario() == userMock);;
	}
	
	@Test
	void unaMuestraConoceSuUbicacion() {
		assertTrue(muestra.getUbicacion() == ubiMock);;
	}
	
	@Test
	void unaMuestraConoceSuFechaDeCreacion() {
		assertTrue(muestra.getFechaCreacion().equals(LocalDate.now()));;
	}
	
	@Test
	void unaMuestraSabeNotificarValidaciones() {
		when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);		
		when(opinionNingunaMock2.getUsuario()).thenReturn(userExpertoMock2);	
		
		muestra.agregarOpinion(opinionNingunaMock);
		muestra.agregarOpinion(opinionNingunaMock2);

		List<ZonaDeCobertura> zonasDeCobertura = new ArrayList<>();
		zonasDeCobertura.add(zonaMock);
		
		muestra.notificarValidacionSiCorresponde(zonasDeCobertura);
		
		verify(zonaMock).notificar(muestra, Aspecto.MUESTRA_VERIFICADA);
	}

}
