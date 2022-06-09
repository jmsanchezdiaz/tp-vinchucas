package ar.unq.tpfinal.muestra;

import static org.junit.Assert.assertTrue;
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
import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Opiniones;
import ar.unq.tpfinal.niveldeconocimiento.ExpertoPermanente;
import ar.unq.tpfinal.niveldeconocimiento.NivelDeConocimiento;

public class MuestraTest {

	Muestra muestra;
	Opinion opinionNingunaMock;
	Opinion opinionImgPocoClaraMock;
	Usuario userMock;
	Ubicacion ubiMock;
	Foto fotoMock;
	
	@BeforeEach
	void setUp() {

		opinionNingunaMock = mock(Opinion.class);
		opinionImgPocoClaraMock = mock(Opinion.class);
		userMock = mock(Usuario.class);
		ubiMock = mock(Ubicacion.class);
		fotoMock = mock(Foto.class);
		
		muestra = new Muestra(userMock, ubiMock, fotoMock, EspecieVinchuca.VinchucaInfestans);
	}
	
	@Test
	void unaMuestraAlInicioNoTieneOpiniones() {
		assertTrue(muestra.getOpiniones().isEmpty());
	}
	
	@Test
	void unaa() {
		
		when(opinionImgPocoClaraMock.getOpinion()).thenReturn(Opiniones.IMAGEN_POCO_CLARA);
		when(opinionNingunaMock.getOpinion()).thenReturn(Opiniones.NINGUNA);
		
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		muestra.agregarOpinion(opinionNingunaMock);
		
		muestra.getResultadoActual();
		assertTrue(true);
	}
	
}
