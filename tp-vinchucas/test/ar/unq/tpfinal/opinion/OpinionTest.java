package ar.unq.tpfinal.opinion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.Foto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.Opinable;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Vinchuca;
import ar.unq.tpfinal.niveldeconocimiento.Basico;
import ar.unq.tpfinal.niveldeconocimiento.Experto;
import ar.unq.tpfinal.niveldeconocimiento.NivelDeConocimiento;
import ar.unq.tpfinal.ubicacion.Ubicacion;
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
		assertTrue(opinion.getEstadoAlOpinar() == user2Mock.getNivelDeConocimiento());
	}
	
	@Test
	void unaOpinionConoceYSeteaSusOpiniones() {
		opinion.setOpinion(opinableMock2);
		assertTrue(opinion.getOpinion() == opinableMock2);
	}
	
	@Test
	void unaOpinionConoceYSeteaSusFechas() {
		opinion.setFechaCreacion(LocalDate.of(2021, 5, 11));
		assertTrue(opinion.getFechaCreacion().equals(LocalDate.of(2021, 5, 11)));
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
