package ar.unq.tpfinal.opinion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.Opinable;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.usuario.Usuario;

public class OpinionTest {
	
	Opinion opinion;
	Usuario userMock;
	Usuario user2Mock;
	Opinable opinableMock;
	Opinable opinableMock2;
	
	@BeforeEach
	void setUp() {

		userMock = mock(Usuario.class);
		user2Mock = mock(Usuario.class);
		opinableMock = mock(Opinable.class);

		opinion = new Opinion(userMock, opinableMock);
	}
	
	@Test
	void unaOpinionConoceYSeteaSuEstado() {
		opinion.setEstadoAlOpinar(user2Mock.getNivelDeConocimiento());
		assertEquals(opinion.getEstadoAlOpinar(), user2Mock.getNivelDeConocimiento());
	}
	
	@Test
	void unaOpinionConoceYSeteaSusOpiniones() {
		opinion.setOpinion(opinableMock2);
		assertEquals(opinion.getOpinion(), opinableMock2);
	}
	
	@Test
	void unaOpinionConoceYSeteaSusFechas() {
		opinion.setFechaCreacion(LocalDate.of(2021, 5, 11));
		assertEquals(opinion.getFechaCreacion(), LocalDate.of(2021, 5, 11));
	}
	
	@Test
	void unaOpinionSabeSiFueRealizadaPorCiertoUsuario() {
		assertTrue(opinion.esOpinionDe(userMock));
	}
	
	@Test
	void unaOpinionSabeSiNOFueRealizadaPorCiertoUsuario() {
		assertFalse(opinion.esOpinionDe(user2Mock));
	}

	@Test
	void unaOpinionPuedeSaberSiEsUnaOpinionDeUnExperto() {
		when(userMock.puedeOpinarEnMuestraParcialmenteVerificada()).thenReturn(true);
		assertTrue(opinion.esOpinionDeExperto());
	}
}
