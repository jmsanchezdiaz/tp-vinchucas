package ar.unq.tpfinal.muestra;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.Aspecto;
import ar.unq.tpfinal.Comentario;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.NivelDeVerificacion;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.ResultadoEmpate;
import ar.unq.tpfinal.Vinchuca;
import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;

public class MuestraTest {

	Muestra muestra;
	Muestra muestraMock;

	Opinion opinionNingunaMock;
	Opinion opinionNingunaMock2;
	Opinion opinionImgPocoClaraMock;
	Opinion opinionVinchucaInfestans;
	Opinion opinionVinchucaInfestans2;

	Usuario userMock;
	Usuario userNormalMock;
	Usuario userExpertoMock;
	Usuario userExpertoMock2;

	Ubicacion ubiMock;
	Foto fotoMock;
	ZonaDeCobertura zonaMock;
	AplicacionWeb appMock;

	@BeforeEach
	void setUp() {

		opinionNingunaMock = mock(Opinion.class);
		opinionNingunaMock2 = mock(Opinion.class);
		opinionImgPocoClaraMock = mock(Opinion.class);
		opinionVinchucaInfestans = mock(Opinion.class);
		opinionVinchucaInfestans2 = mock(Opinion.class);
		userMock = mock(Usuario.class);
		userNormalMock = mock(Usuario.class);
		userExpertoMock = mock(Usuario.class);
		userExpertoMock2 = mock(Usuario.class);
		ubiMock = mock(Ubicacion.class);
		fotoMock = mock(Foto.class);
		zonaMock = mock(ZonaDeCobertura.class);
		appMock = mock(AplicacionWeb.class);

		when(userMock.puedeOpinarEnMuestraParcialmenteVerificada()).thenReturn(false);
		when(userNormalMock.puedeOpinarEnMuestraParcialmenteVerificada()).thenReturn(false);
		when(userExpertoMock.puedeOpinarEnMuestraParcialmenteVerificada()).thenReturn(true);
		when(userExpertoMock2.puedeOpinarEnMuestraParcialmenteVerificada()).thenReturn(true);

		when(opinionVinchucaInfestans.getOpinion()).thenReturn(Vinchuca.VinchucaInfestans);
		when(opinionVinchucaInfestans2.getOpinion()).thenReturn(Vinchuca.VinchucaInfestans);
		when(opinionNingunaMock.getOpinion()).thenReturn(Comentario.NINGUNA);
		when(opinionNingunaMock2.getOpinion()).thenReturn(Comentario.NINGUNA);
		when(opinionImgPocoClaraMock.getOpinion()).thenReturn(Comentario.IMAGEN_POCO_CLARA);

		muestra = new Muestra(userMock, ubiMock, fotoMock, Vinchuca.VinchucaGuayasana);
	}

	@Test
	void unUsuarioPuedeOpinarSiNadieOpino() {
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userMock);
		when(userMock.puedeOpinarEnMuestraParcialmenteVerificada()).thenReturn(false);

		muestra.agregarOpinion(appMock,opinionImgPocoClaraMock);
		assertFalse(muestra.getOpiniones().isEmpty());
	}

	@Test
	void unUsuarioQueYaOpinoNoPuedeVolverOpinar() {
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userNormalMock);
		when(opinionNingunaMock.getUsuario()).thenReturn(userNormalMock);

		muestra.agregarOpinion(appMock,opinionImgPocoClaraMock);
		muestra.agregarOpinion(appMock,opinionNingunaMock); // No se agrega porque ya opino.

		// son dos opiniones mas la del usuario creador de la muestra.
		assertEquals(muestra.getOpiniones().size(),2,0);
	}

	@Test
	void unaMuestraVerificadaNoPuedeSerOpinada() {
		when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);
		when(opinionNingunaMock2.getUsuario()).thenReturn(userExpertoMock2);
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userMock);

		muestra.agregarOpinion(appMock,opinionNingunaMock);
		muestra.agregarOpinion(appMock,opinionNingunaMock2);
		muestra.agregarOpinion(appMock,opinionImgPocoClaraMock); // No se agrega porque ya esta verificada.

		// son dos opiniones mas la del usuario creador de la muestra.
		assertEquals(muestra.getOpiniones().size(),3,0);
	}

	@Test
	void siOpinoUnExpertoNoPuedeOpinarUnUsuarioNormal() {

		when(opinionImgPocoClaraMock.esOpinionDeExperto()).thenReturn(true);
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userExpertoMock);
		when(opinionNingunaMock.esOpinionDeExperto()).thenReturn(false);
		when(opinionNingunaMock.getUsuario()).thenReturn(userNormalMock);

		muestra.agregarOpinion(appMock,opinionImgPocoClaraMock);
		muestra.agregarOpinion(appMock,opinionNingunaMock); // No se agrega porque ya opino un experto.

		// es una opinion mas la del usuario creador de la muestra.
		assertEquals(muestra.getOpiniones().size(),2,0);
	}

	@Test
	void elResultadoDeUnaMuestraCuandoAlInicioEsLaOpinionDelUsuarioQueSubioLaMuestra() {
		assertEquals(muestra.getResultadoActual() ,Vinchuca.VinchucaGuayasana);
	}

	@Test
	void elResultadoDeUnaMuestraEsLaOpinionMasVotada() {

		when(opinionVinchucaInfestans.getUsuario()).thenReturn(userExpertoMock);
		when(opinionVinchucaInfestans2.getUsuario()).thenReturn(userExpertoMock2);

		muestra.agregarOpinion(appMock,opinionVinchucaInfestans);
		muestra.agregarOpinion(appMock,opinionVinchucaInfestans2);

		assertEquals(muestra.getResultadoActual(), Vinchuca.VinchucaInfestans);
	}

	@Test
	void elResultadoDeUnaMuestraEsNoDefinidoCuandoHayEmpate() {

		when(opinionVinchucaInfestans.getUsuario()).thenReturn(userMock);
		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userNormalMock);
		when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);

		muestra.agregarOpinion(appMock,opinionVinchucaInfestans);
		muestra.agregarOpinion(appMock,opinionImgPocoClaraMock);
		muestra.agregarOpinion(appMock,opinionNingunaMock);

		assertEquals(muestra.getResultadoActual(), ResultadoEmpate.NO_DEFINIDO);
	}

	@Test
	void unaMuestraParcialSabeCuandoEsEmpate() {
		
		when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);
		when(opinionVinchucaInfestans.getUsuario()).thenReturn(userExpertoMock2);	
		
		when(opinionNingunaMock.esOpinionDeExperto()).thenReturn(true);
		when(opinionVinchucaInfestans.esOpinionDeExperto()).thenReturn(true);	
		
		muestra.agregarOpinion(appMock,opinionNingunaMock);
		muestra.agregarOpinion(appMock,opinionVinchucaInfestans);
		
		assertEquals(muestra.getResultadoActual(), ResultadoEmpate.NO_DEFINIDO);
	}
	
	@Test
	void unaMuestraSabeCuandoEsVerificada() {
		when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);
		when(opinionNingunaMock2.getUsuario()).thenReturn(userExpertoMock2);
		when(opinionNingunaMock.esOpinionDeExperto()).thenReturn(true);
		when(opinionNingunaMock2.esOpinionDeExperto()).thenReturn(true);

		muestra.agregarOpinion(appMock,opinionNingunaMock);
		muestra.agregarOpinion(appMock,opinionNingunaMock2);

		assertInstanceOf(Verificada.class, muestra.getEstadoDeVerificacion());
		assertEquals(muestra.getEstadoDeVerificacion().valor(),  NivelDeVerificacion.VERIFICADA);
	}

	@Test
	void unaMuestraSabeCuandoEsVerificadaParcial() {

		when(opinionImgPocoClaraMock.esOpinionDeExperto()).thenReturn(true);

		muestra.agregarOpinion(appMock,opinionImgPocoClaraMock);

		assertInstanceOf(VerificadaParcial.class, muestra.getEstadoDeVerificacion());
		assertTrue(muestra.seEncuentraEnEstado(NivelDeVerificacion.VERIFICADA_PARCIAL));
		assertFalse(muestra.seEncuentraEnEstado(NivelDeVerificacion.NO_VERIFICADA));
	}

	@Test
	void unaMuestraSabeCuandoEsNoVerificada() {

		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userNormalMock);

		muestra.agregarOpinion(appMock,opinionImgPocoClaraMock);

		assertInstanceOf(NoVerificada.class, muestra.getEstadoDeVerificacion());
		assertTrue(muestra.seEncuentraEnEstado(NivelDeVerificacion.NO_VERIFICADA));
	}

	@Test
	void unaMuestraPuedeHacerUnMapYcontarOpiniones() {
		List<Opinion> listaDeOpiniones = new ArrayList<>();
		listaDeOpiniones.add(opinionImgPocoClaraMock);
		listaDeOpiniones.add(opinionImgPocoClaraMock);

		assertEquals(2, muestra.contarOpiniones(listaDeOpiniones).get(Comentario.IMAGEN_POCO_CLARA));
	}

	@Test
    void unaMuestraPuedeNotificarValidaciónDeOpiniónSobreEllaMisma() {
       
        // setup
		List<ZonaDeCobertura> zonasDeCobertura = new ArrayList<ZonaDeCobertura>();
        zonasDeCobertura.add(zonaMock);
		
        when(opinionNingunaMock.esOpinionDeExperto()).thenReturn(true);
        when(opinionNingunaMock2.esOpinionDeExperto()).thenReturn(true);
        when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);
        when(opinionNingunaMock2.getUsuario()).thenReturn(userExpertoMock2);
        when(appMock.zonasDeLaMuestra(muestra)).thenReturn(zonasDeCobertura);

        muestra.agregarOpinion(appMock ,opinionNingunaMock);

        // exercise
        muestra.agregarOpinion(appMock, opinionNingunaMock2);

        // verify
        verify(zonaMock).notificar(muestra, Aspecto.MUESTRA_VERIFICADA);
    }
	
	@Test
	void unaMuestraSabeSiElResultadoEsUnInsecto() {

		when(opinionVinchucaInfestans.getUsuario()).thenReturn(userNormalMock);
		when(opinionVinchucaInfestans2.getUsuario()).thenReturn(userExpertoMock);
		muestra.agregarOpinion(appMock, opinionVinchucaInfestans);
		muestra.agregarOpinion(appMock, opinionVinchucaInfestans2);

		assertTrue(muestra.esInsecto(Vinchuca.VinchucaInfestans));
	}

	@Test
	void unaMuestraSabeSiNoElResultadoEsUnInsecto() {

		when(opinionImgPocoClaraMock.getUsuario()).thenReturn(userNormalMock);
		muestra.agregarOpinion(appMock,opinionImgPocoClaraMock);

		assertFalse(muestra.esInsecto(Vinchuca.VinchucaInfestans));
	}

	@Test
	void unaMuestraSabeSiFueVotadaEnCiertaFecha() {

		LocalDate fecha = LocalDate.of(2021, 5, 11);

		when(opinionVinchucaInfestans.getUsuario()).thenReturn(userNormalMock);
		when(opinionVinchucaInfestans.getFechaCreacion()).thenReturn(fecha);
		muestra.agregarOpinion(appMock,opinionVinchucaInfestans);

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
	void unaMuestraConoceLaOpinionDelUsuario() {
		assertEquals(muestra.getOpinionDeUsuario().getOpinion(), Vinchuca.VinchucaGuayasana);
		;
	}

	@Test
	void unaMuestraConoceSuFoto() {
		assertEquals(muestra.getFoto(),fotoMock);
		;
	}

	@Test
	void unaMuestraConoceSuUsuario() {
		assertEquals(muestra.getUsuario(), userMock);
		;
	}

	@Test
	void unaMuestraConoceSuUbicacion() {
		assertEquals(muestra.getUbicacion() ,ubiMock);
		;
	}

	@Test
	void unaMuestraConoceSuFechaDeCreacion() {
		assertEquals(muestra.getFechaCreacion(), LocalDate.now());
		;
	}

	@Test
	void unaMuestraInicialmenteEsNoVerificada() {
		assertEquals(muestra.getEstadoDeVerificacion().valor(), NivelDeVerificacion.NO_VERIFICADA);
		assertTrue(muestra.seEncuentraEnEstado(NivelDeVerificacion.NO_VERIFICADA));
	}
	
	@Test
	void unaMuestraVerificadaNoPuedeAgregarOpiniones() {
		when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);		
		when(opinionNingunaMock2.getUsuario()).thenReturn(userExpertoMock2);
		when(opinionNingunaMock.esOpinionDeExperto()).thenReturn(true);		
		when(opinionNingunaMock2.esOpinionDeExperto()).thenReturn(true);	
		
		muestra.agregarOpinion(appMock,opinionNingunaMock);
		muestra.agregarOpinion(appMock,opinionNingunaMock2);
		
		muestra.agregarOpinion(appMock,opinionVinchucaInfestans);
		
		assertEquals(muestra.getOpiniones().size(), 3);
	}
	
	@Test
	void unaMuestraVerificadaConoceSuResultado() {
		when(opinionNingunaMock.getUsuario()).thenReturn(userExpertoMock);		
		when(opinionNingunaMock2.getUsuario()).thenReturn(userExpertoMock2);
		when(opinionNingunaMock.esOpinionDeExperto()).thenReturn(true);		
		when(opinionNingunaMock2.esOpinionDeExperto()).thenReturn(true);	
		
		muestra.agregarOpinion(appMock,opinionNingunaMock);
		muestra.agregarOpinion(appMock,opinionNingunaMock2);
		
		assertEquals(muestra.getEstadoDeVerificacion().resultadoActual(muestra), Comentario.NINGUNA);
	}

}
