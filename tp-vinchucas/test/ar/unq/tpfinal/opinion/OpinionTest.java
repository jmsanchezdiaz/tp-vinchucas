package ar.unq.tpfinal.opinion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.Opinable;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Vinchuca;
import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.usuario.Usuario;

public class OpinionTest {
	
	Opinion opinion;
	Usuario userMock;
	Usuario user2Mock;
	Opinable opinableMock;
	
	@BeforeEach
	void setUp() {

		userMock = mock(Usuario.class);
		user2Mock = mock(Usuario.class);
		opinableMock = mock(Opinable.class);

		opinion = new Opinion(userMock, opinableMock);
	}
	
	@Test
	void unaOpinionSabeSiFueRealizadaPorCiertoUsuario() {
		assertTrue(opinion.esOpinionDe(userMock));
	}
	
	@Test
	void unaOpinionSabeSiNOFueRealizadaPorCiertoUsuario() {
		assertFalse(opinion.esOpinionDe(user2Mock));
	}
	
}
