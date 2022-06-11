package ar.unq.tpfinal.usuario;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.Vinchuca;
import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.NoVinchuca;
import ar.unq.tpfinal.Opinion;
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
	void cadaUsuarioTieneUnIdGeneradoUnicoAleatoriamente() {
		assertFalse(userNormal.getId().equals(userExpertoPermanente.getId()));
	}
	
	@Test
	void losUsuarioTienenNombreYId() {
		userNormal.setId("id testeado");
		
		assertEquals(userNormal.getNombre(), "Juan");
		assertEquals(userNormal.getId(), "id testeado");
	}
	
	@Test
	void todosLoUsuarioMutablesAlInicioSuNivelDeConocimientoEsBasico() {
		assertTrue(userNormal.esBasico());
	}
	
	@Test
	void puedoSubirElNivelDeConocimientoDeUnUsuarioComun() {
		//Exercise
		userNormal.subirDeNivel();
		
		//Assert
		assertTrue(userNormal.esExperto());
		assertFalse(userNormal.esBasico());
	}
	
	@Test
	void puedoBajarElNivelDeConocimientoDeUnUsuarioComun() {
		//Exercise
		userNormal.subirDeNivel();
		userNormal.bajarDeNivel();
		
		//Assert
		assertTrue(userNormal.esBasico());
		assertFalse(userNormal.esExperto());
	}
	
	@Test
	void noPuedoBajarElNivelDeConocimientoDeUnUsuarioBasicoYLanzaUnaExcepcion() {
		RuntimeException exp = assertThrows(RuntimeException.class, () -> userNormal.bajarDeNivel());
		
		assertEquals(exp.getMessage(), "No se puede bajar más de nivel");
	}
	
	@Test
	void noPuedoSubirElNivelDeConocimientoDeUnUsuarioExpertoYLanzaUnaExcepcion() {
		userNormal.subirDeNivel();
		
		RuntimeException exp = assertThrows(RuntimeException.class, () -> userNormal.subirDeNivel());
		
		assertEquals(exp.getMessage(),"No se puede subir más de nivel");
		assertInstanceOf(RuntimeException.class, exp);
	}
	
	@Test
	void noPuedoBajarElNivelDeConocimientoDeUnUsuarioFijoYLanzaUnaExcepcion() {
		
		RuntimeException exp = assertThrows(RuntimeException.class, () -> userExpertoPermanente.bajarDeNivel());
		assertEquals(exp.getMessage(), "No se puede bajar de nivel a un usuario fijo");
		assertInstanceOf(RuntimeException.class, exp);
	}
	
	@Test
	void noPuedoSubirElNivelDeConocimientoDeUnUsuarioFijoYLanzaUnaExcepcion() {
		RuntimeException exp = assertThrows(RuntimeException.class, () -> userExpertoPermanente.subirDeNivel());
		
		assertEquals(exp.getMessage(),"No se puede subir de nivel a un usuario fijo");
		assertInstanceOf(RuntimeException.class, exp);
	}
	
	@Test
	void unUsuarioExpertoPermanenteSiempreLoEs() {
		assertFalse(userExpertoPermanente.esBasico());
		assertTrue(userExpertoPermanente.esExperto());
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
