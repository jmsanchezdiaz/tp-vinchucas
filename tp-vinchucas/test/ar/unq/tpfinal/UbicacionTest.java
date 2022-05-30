package ar.unq.tpfinal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UbicacionTest {
	private Ubicacion ubicacion;
	private Ubicacion ubicacion2;

	@BeforeEach
	public void setup() {
		this.ubicacion = new Ubicacion(12, 13);
		this.ubicacion2 = new Ubicacion(12.0001, 13.0001);
	}

	@Test
	public void testGetLatitud() {
		assertEquals(ubicacion.getLatitud(), 12);
	}

	@Test
	public void testSetLatitud() {
		this.ubicacion.setLatitud(16.3);
		assertEquals(ubicacion.getLatitud(), 16.3);
	}

	@Test
	public void testGetLongitud() {
		assertEquals(ubicacion.getLongitud(), 13);
	}

	@Test
	public void testSetLongitud() {
		this.ubicacion.setLongitud(16.3);
		assertEquals(ubicacion.getLongitud(), 16.3);
	}

	@Test
	public void testLimiteInferiorDeLongitud() {
		// no se modifica la longitud si se supera el limite inferior

		IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
			ubicacion.setLongitud(-190);
		});

		assertEquals("La longitud tiene que encontrarse entre -180 y 180", excepcion.getMessage());

		assertEquals(13, ubicacion.getLongitud());
	}

	@Test
	public void testLimiteSuperiorDeLongitud() {
		// no se modifica la longitud si se supera el limite superior

		IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
			ubicacion.setLongitud(190);
		});

		assertEquals("La longitud tiene que encontrarse entre -180 y 180", excepcion.getMessage());

		assertEquals(13, ubicacion.getLongitud());
	}

	@Test
	public void testLimiteInferiorDeLatitud() {
		// no se modifica la latitud si se supera el limite inferior

		IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
			ubicacion.setLatitud(-91);
		});

		assertEquals("La latitud tiene que encontrarse entre -90 y 90", excepcion.getMessage());

		assertEquals(12, ubicacion.getLatitud());
	}

	@Test
	public void testLimiteSuperiorDeLatitud() {
		// no se modifica la latitud si se supera el limite superior

		IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
			ubicacion.setLatitud(91);
		});

		assertEquals("La latitud tiene que encontrarse entre -90 y 90", excepcion.getMessage());

		assertEquals(12, ubicacion.getLatitud());
	}

	@Test
	public void testDistanciaEnMetros() {
		assertEquals(15.55, ubicacion.distanciaEnMetrosCon(ubicacion2));
	}

	@Test
	public void testUbicacionesAMenosDeUnaCantidadDeMetrosPositiva() {
		List<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();

		Ubicacion ubicacionA = new Ubicacion(12.0001, 13);
		Ubicacion ubicacionB = new Ubicacion(11.9999, 13);
		Ubicacion ubicacionC = new Ubicacion(-12.0002, -13);
		Ubicacion ubicacionD = new Ubicacion(-12.0001, 12);

		ubicaciones.add(ubicacionA);
		ubicaciones.add(ubicacionB);
		ubicaciones.add(ubicacionC);
		ubicaciones.add(ubicacionD);

		List<Ubicacion> resultado = ubicacion.ubicacionesAMenosDe(ubicaciones, 10000);

		assertTrue(resultado.contains(ubicacionA));
		assertTrue(resultado.contains(ubicacionB));
		assertEquals(2, resultado.size());
	}

}
