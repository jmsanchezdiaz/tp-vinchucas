package ar.unq.tpfinal.usuario;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.Vinchuca;
import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.NoVinchuca;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.niveldeconocimiento.Basico;
import ar.unq.tpfinal.niveldeconocimiento.Experto;
import ar.unq.tpfinal.niveldeconocimiento.ExpertoPermanente;
import ar.unq.tpfinal.niveldeconocimiento.NivelDeConocimiento;

public class UsuarioTest {
	Usuario userNormal;
	Usuario userExpertoPermanente;
	AplicacionWeb appMock;
	Foto fotoMock;
	Muestra muestraMock;
	Ubicacion ubiMock;
	NivelDeConocimiento expertoPermanente;
	
	@BeforeEach
	void setUp() {
		appMock = mock(AplicacionWeb.class);
		fotoMock = mock(Foto.class);
		muestraMock = mock(Muestra.class);
		ubiMock = mock(Ubicacion.class);
		expertoPermanente = new ExpertoPermanente();
		
		userNormal = new Usuario("Juan");
		userExpertoPermanente = new Usuario("JuanCruz", expertoPermanente);
	}
	
	@Test
	void unUsuarioTieneNombre() {
		assertEquals(userNormal.getNombre(), "Juan");
	}
	
	@Test
	void todosLoUsuarioMutablesAlInicioSuNivelDeConocimientoEsBasico() {
		assertFalse(userNormal.puedeOpinarEnMuestrapuedeOpinarEnMuestraParcialmenteVerificada());
	}
	
	@Test
	void unUsuarioPuedeSubirDeNivelSiCumpleConCiertasCondiciones() {
		//Exercise
		when(appMock.cantidadDeEnviosDeHace(userNormal, 30)).thenReturn(15);
		when(appMock.cantidadDeOpinionesDeHace(userNormal, 30)).thenReturn(34);

		//Compruebo que el usuario deba subir de nivel.
		Basico estadoUsuario = (Basico) userNormal.getNivelDeConocimiento();
		assertTrue(estadoUsuario.debeSubirDeNivel(appMock, userNormal));

		userNormal.enviarMuestra(appMock, ubiMock, fotoMock, Vinchuca.VinchucaGuayasana);
		
		//Assert
		assertTrue(userNormal.puedeOpinarEnMuestrapuedeOpinarEnMuestraParcialmenteVerificada());
		assertInstanceOf(Experto.class, userNormal.getNivelDeConocimiento());
		verify(appMock).agregarMuestra(any(Muestra.class));
	}
	
	@Test
	void puedoBajarElNivelDeConocimientoDeUnUsuarioComun() {
		//Setup
		when(appMock.cantidadDeEnviosDeHace(userNormal, 30)).thenReturn(7);
		when(appMock.cantidadDeOpinionesDeHace(userNormal, 30)).thenReturn(32);
		userNormal.setNivelDeConocimiento(new Experto());
		
		//Compruebo que el usuario deba bajar de nivel.
		Experto estadoUsuario = (Experto) userNormal.getNivelDeConocimiento();
		assertTrue(estadoUsuario.debeBajarDeNivel(appMock, userNormal));
		
		//Exercise
		userNormal.enviarMuestra(appMock, ubiMock, fotoMock, Vinchuca.VinchucaGuayasana);
		
		//Assert
		assertFalse(userNormal.puedeOpinarEnMuestrapuedeOpinarEnMuestraParcialmenteVerificada());
		assertInstanceOf(Basico.class, userNormal.getNivelDeConocimiento());
		verify(appMock).agregarMuestra(any(Muestra.class));
	}
	

	@Test
	void unUsuarioExpertoPermanenteSiempreLoEs() {
		//Setup
		when(appMock.cantidadDeEnviosDeHace(userExpertoPermanente, 30)).thenReturn(7);
		when(appMock.cantidadDeOpinionesDeHace(userExpertoPermanente, 30)).thenReturn(32);
				
		//Exercise
		userExpertoPermanente.enviarMuestra(appMock, ubiMock, fotoMock, Vinchuca.VinchucaGuayasana);
		
		//Assert
		assertInstanceOf(ExpertoPermanente.class, userExpertoPermanente.getNivelDeConocimiento());
		verify(appMock).agregarMuestra(any(Muestra.class));
	}
		
	
	@Test
	void unUsuarioPuedeEnviarUnaMuestra() {
		//Exercise
		userNormal.enviarMuestra(appMock, ubiMock, fotoMock, Vinchuca.VinchucaInfestans);
		
		//Verify
		verify(appMock).agregarMuestra(any(Muestra.class));
	}
	
	@Test
	void unUsuarioPuedeOpinarSobreUnaMuestra(){
		// Exercise
		userNormal.opinarMuestra(appMock, muestraMock, NoVinchuca.ChicheFoliada);
		//Verify
		verify(appMock).agregarOpinionA(any(Muestra.class), any(Opinion.class));
	}
	
}
