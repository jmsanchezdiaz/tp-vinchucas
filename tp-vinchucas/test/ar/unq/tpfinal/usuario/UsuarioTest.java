package ar.unq.tpfinal.usuario;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.NivelDeConocimiento;

public class UsuarioTest {
	Usuario userBasico;
	Usuario userExpertoFijo;
	
	@BeforeEach
	void setUp() {
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
		
	}
	
	@Test
	void unUsuarioPuedeOpinarSobreUnaMuestra() {
		
	}
	
	@Test
	void unUsuarioNoPuedeOpinarSobreUnaMuestraQueNoExisteYSeLanzaUnaExcepcion() throws Exception {

	}
}
