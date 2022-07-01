package ar.unq.tpfinal.organizacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unq.tpfinal.Aspecto;
import ar.unq.tpfinal.FuncionalidadExterna;
import ar.unq.tpfinal.TipoDeOrganizacion;
import ar.unq.tpfinal.muestra.Muestra;
import ar.unq.tpfinal.ubicacion.Ubicacion;
import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;

public class OrganizacionTest {

	private Organizacion organizacion1;
	private ZonaDeCobertura zona1;
	private Muestra muestraMock1;
	private FuncionalidadExterna funcionalidadMock1;

	@BeforeEach
	public void setup() {
		organizacion1 = new Organizacion(TipoDeOrganizacion.EDUCATIVA, 2);
		muestraMock1 = mock(Muestra.class);

		try {
			zona1 = new ZonaDeCobertura("zona1", 2.0, new Ubicacion(20, 20));

		} catch (Exception err) {
			System.out.println(err.getMessage());
		}

		funcionalidadMock1 = mock(FuncionalidadExterna.class);
	}

	@Test
	public void testCreacionDeOrganizacionConCantidadPositivaDePersonal() {
		assertEquals(2, organizacion1.getCantidadDePersonal());
		assertEquals(TipoDeOrganizacion.EDUCATIVA, organizacion1.getTipoDeOrganizacion());
	}

	@Test
	public void testCreacionDeOrganizacionConCantidadNegativaDePersonal() {
		Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
			new Organizacion(TipoDeOrganizacion.EDUCATIVA, -2);
		});

		assertEquals("La cantidad de Personal no puede ser negativa", excepcion.getMessage());
	}

	@Test
	void testSeteoDeFuncionalidadParaAspecto() {
		organizacion1.setFuncionalidadParaAspecto(funcionalidadMock1, Aspecto.MUESTRA_ENVIADA);

		assertEquals(funcionalidadMock1, organizacion1.getFuncionalidadesPorAspecto().get(Aspecto.MUESTRA_ENVIADA));
	}

	@Test
	void testLlamadoAFuncionalidadExternaAnteEventoEnMuestra() {
		organizacion1.setFuncionalidadParaAspecto(funcionalidadMock1, Aspecto.MUESTRA_ENVIADA);

		organizacion1.eventoEnMuestra(zona1, muestraMock1, Aspecto.MUESTRA_ENVIADA);

		verify(funcionalidadMock1, times(1)).nuevoEvento(zona1, organizacion1, muestraMock1);

	}
}
