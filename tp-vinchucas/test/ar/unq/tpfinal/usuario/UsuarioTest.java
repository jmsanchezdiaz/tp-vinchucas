package ar.unq.tpfinal.usuario;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.EspecieVinchuca;
import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.NivelDeConocimiento;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Opiniones;

public class UsuarioTest {
	Usuario userBasico;
	Usuario userExpertoFijo;
	AplicacionWeb appMock;
	Foto fotoMock;
	Muestra muestraMock;
	Ubicacion ubiMock;
	
	
	@BeforeEach
	void setUp() {
		appMock = mock(AplicacionWeb.class);
		fotoMock = mock(Foto.class);
		muestraMock = mock(Muestra.class);
		ubiMock = mock(Ubicacion.class);
		
		
		userBasico = new UsuarioMutable("Juan");
		userExpertoFijo = new UsuarioFijo("Eze");
	}
	
	@Test
	void todosLoUsuarioMutablesAlInicioSuNivelDeConocimientoEsBasico() {
		assertEquals(userBasico.getNivelDeConocimiento(), NivelDeConocimiento.BASICO);
	}
	
	@Test
	void unUsuarioFijoSiempreEsExperto() {
		assertEquals(userExpertoFijo.getNivelDeConocimiento(), NivelDeConocimiento.EXPERTO);
	}
	
	@Test
	void puedoSubirElNivelDeConocimientoDeUnUsuarioMutable() {
		//Exercise
		userBasico.subirDeNivel();
		
		//Assert
		assertEquals(userBasico.getNivelDeConocimiento(), userBasico.getNivelDeConocimiento().siguienteNivel());
	}
	
	@Test
	void puedoBajarElNivelDeConocimientoDeUnUsuarioMutable() {
		//Exercise
		userBasico.subirDeNivel();
		userBasico.bajarDeNivel();
		
		//Assert
		assertEquals(userBasico.getNivelDeConocimiento(), userBasico.getNivelDeConocimiento().anteriorNivel());
	}
	
	@Test
	void noPuedoBajarElNivelDeConocimientoDeUnUsuarioFijoYLanzaUnaExcepcion() {
		assertThrows(RuntimeException.class, 
				() -> userExpertoFijo.bajarDeNivel(), 
				"No se puede bajar de nivel a un usuario fijo");
	}
	
	@Test
	void noPuedoSubirElNivelDeConocimientoDeUnUsuarioFijoYLanzaUnaExcepcion() {
		assertThrows(RuntimeException.class,
				() -> userExpertoFijo.subirDeNivel(),
				"No se puede subir de nivel a un usuario fijo");
	}
	
	@Test
	void unUsuarioPuedeEnviarUnaMuestra() {
		//Exercise
		userBasico.enviarMuestra(appMock, ubiMock, fotoMock, EspecieVinchuca.VinchucaInfestans);
		
		//Verify
		verify(appMock).agregarMuestra(any(Muestra.class));
	}
	
	@Test
	void unUsuarioPuedeOpinarSobreUnaMuestra() throws Exception {
		//Exercise
		userBasico.opinarMuestra(appMock, muestraMock, Opiniones.IMAGEN_POCO_CLARA);
		
		//Verify
		verify(appMock).agregarOpinionA(any(Muestra.class), any(Opinion.class));
	}
	
	@Test
	void unUsuarioNoPuedeOpinarSobreUnaMuestraQueNoExisteYSeLanzaUnaExcepcion() throws Exception {
		
		//Mockeo que cuando la appMock llame al mensaje agregarOpinion lance una excepcion
		doThrow(Exception.class)
	      .when(appMock)
	      .agregarOpinionA(muestraMock, mock(Opinion.class));

		//Exercise
		userBasico.opinarMuestra(appMock, muestraMock, Opiniones.CHINCHE_FOLIADA);
	}
}
