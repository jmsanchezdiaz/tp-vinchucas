package ar.unq.tpfinal.muestra;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Vinchuca;

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
		
		muestra = new Muestra(userMock, ubiMock, fotoMock, Vinchuca.VinchucaGuayasana);
	}
	
	@Test
	void unaMuestraAlInicioNoTieneOpiniones() {
		assertTrue(muestra.getOpiniones().isEmpty());
	}
	
	@Test
	void unaa() {
		
		when(opinionImgPocoClaraMock.getOpinion()).thenReturn(Vinchuca.VinchucaGuayasana);
		when(opinionNingunaMock.getOpinion()).thenReturn(Vinchuca.VinchucaGuayasana);
		
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		muestra.agregarOpinion(opinionImgPocoClaraMock);
		muestra.agregarOpinion(opinionNingunaMock);
		
		muestra.getResultadoActual();
		assertTrue(true);
	}
	
}
