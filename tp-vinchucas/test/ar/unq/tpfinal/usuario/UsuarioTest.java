package ar.unq.tpfinal.usuario;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.EspecieVinchuca;
import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Opiniones;
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
		expertoPermanente = mock(ExpertoPermanente.class);
		
		userNormal = new Usuario("Juan");
		userExpertoPermanente = new Usuario("JuanCruz", expertoPermanente);
	}
	
	@Test
	void todosLoUsuarioMutablesAlInicioSuNivelDeConocimientoEsBasico() {
		assertInstanceOf(Basico.class, userNormal.getNivelDeConocimiento());
	}
	
	@Test
	void unUsuarioFijoSiempreEsExperto() {
		assertInstanceOf(ExpertoPermanente.class, userExpertoPermanente.getNivelDeConocimiento());
		
		userExpertoPermanente.bajarDeNivel();
		
		assertInstanceOf(ExpertoPermanente.class, userExpertoPermanente.getNivelDeConocimiento());
	}
	
	@Test
	void puedoSubirElNivelDeConocimientoDeUnUsuarioComun() {
		//Exercise
		userNormal.subirDeNivel();
		
		//Assert
		assertInstanceOf(Experto.class, userNormal.getNivelDeConocimiento());
	}
	
	@Test
	void puedoBajarElNivelDeConocimientoDeUnUsuarioComun() {
		//Exercise
		userNormal.subirDeNivel();
		userNormal.bajarDeNivel();
		
		//Assert
		assertInstanceOf(Basico.class, userNormal.getNivelDeConocimiento());
	}
	
	@Test
	void noPuedoBajarElNivelDeConocimientoDeUnUsuarioFijoYLanzaUnaExcepcion() {
		 doThrow(RuntimeException.class)
	      .when(expertoPermanente)
	      .bajarNivel(any());
	}
	
	@Test
	void noPuedoSubirElNivelDeConocimientoDeUnUsuarioFijoYLanzaUnaExcepcion() {
		doThrow(RuntimeException.class)
	      .when(expertoPermanente)
	      .subirNivel(any());
	}
	
	@Test
	void unUsuarioPuedeEnviarUnaMuestra() {
		//Exercise
		userNormal.enviarMuestra(appMock, ubiMock, fotoMock, EspecieVinchuca.VinchucaInfestans);
		
		//Verify
		verify(appMock).agregarMuestra(any(Muestra.class));
	}
	
	@Test
	void unUsuarioBasicoNoPuedeOpinarEnMuestrasVerificadas() {
		assertFalse(userNormal.puedeOpinarEnMuestrasVerificadasParcialcialmente());
	}
	
	@Test
	void unUsuarioExpertoNoPuedeOpinarEnMuestrasVerificadas() {
		assertFalse(userExpertoPermanente.puedeOpinarEnMuestrasVerificadasParcialcialmente());
	}
	
	@Test
	void unUsuarioPuedeOpinarSobreUnaMuestra(){
		// Exercise
		userNormal.opinarMuestra(appMock, muestraMock, Opiniones.IMAGEN_POCO_CLARA);
		//Verify
		verify(appMock).agregarOpinionA(any(Muestra.class), any(Opinion.class));
	}
	
	
	
}
