package ar.unq.tpfinal.ubicacion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class Ubicacion {
	/**
	 * Crea una ubicacion en la superficie terrestre
	 * 
	 * @param {double} latitud - Latitud en grados.
	 * @param {double} longitud - Longitud en grados.
	 * @throws {IllegalArgumentException} latitud o longitud inválida.
	 *
	 */

	private double latitud;
	private double longitud;

	public Ubicacion(double latitud, double longitud) {

		this.setLatitud(latitud);
		this.setLongitud(longitud);
	}

	public double getLatitud() {

		return latitud;
	}

	void setLatitud(double latitud) {
		if (latitud > -90 & latitud < 90) {
			this.latitud = latitud;
		} else {
			throw new IllegalArgumentException("La latitud tiene que encontrarse entre -90 y 90");
		}

	}

	public double getLongitud() {
		return longitud;
	}

	void setLongitud(double longitud) {
		if (longitud > -180 & longitud < 180) {
			this.longitud = longitud;
		} else {
			throw new IllegalArgumentException("La longitud tiene que encontrarse entre -180 y 180");
		}
	}

	public double distanciaEnMetrosCon(Ubicacion otraUbicacion) {
		// basado en la fórmula Haversine, retorna la distancia en metros (con 2
		// decimales) respecto a otra ubicación

		double radioTierra = 6371000; // en Metros
		// lat1, lat2, distanciaLat, distanciaLong en radianes
		double lat1 = this.getLatitud() * (Math.PI / 180);
		double lat2 = otraUbicacion.getLatitud() * (Math.PI / 180);
		double distanciaLat = (otraUbicacion.getLatitud() - this.getLatitud()) * Math.PI / 180;
		double distanciaLong = (otraUbicacion.getLongitud() - this.getLongitud()) * Math.PI / 180;

		double a = Math.sin(distanciaLat / 2) * Math.sin(distanciaLat / 2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.sin(distanciaLong / 2) * Math.sin(distanciaLong / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return BigDecimal.valueOf(radioTierra * c).setScale(2, RoundingMode.HALF_UP).doubleValue();

	}

	public List<Ubicacion> ubicacionesAMenosDe(List<Ubicacion> ubicaciones, double distanciaEnMetros) {
		return ubicaciones.stream().filter(ubicacion -> this.distanciaEnMetrosCon(ubicacion) <= distanciaEnMetros)
				.collect(Collectors.toList());
	}

}
