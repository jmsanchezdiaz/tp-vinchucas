package ar.unq.tpfinal.zonaDeCobertura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.unq.tpfinal.Aspecto;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.Observador;
import ar.unq.tpfinal.ubicacion.Ubicacion;

public class ZonaDeCobertura {

	private String nombre;
	private Double radio; // En kilometros
	private Ubicacion epicentro;
	private Map<Aspecto, List<Observador>> suscriptores = new HashMap<Aspecto, List<Observador>>();

	/**
	 * Crea una Zona de cobertura
	 * 
	 * @param {String}    nombre - nombre de la zona.
	 * @param {Double}    radio - radio en kilómetros.
	 * @param {Ubicacion} epicentro - ubicacion del centro de la zona.
	 *
	 */

	public ZonaDeCobertura(String nombre, Double radio, Ubicacion epicentro) {

		this.nombre = nombre;
		this.radio = radio;
		this.epicentro = epicentro;

	}

	public String getNombre() {
		return nombre;
	}

	public Double getRadio() {
		return radio;
	}

	public Ubicacion getEpicentro() {
		return epicentro;
	}

	public Boolean esZonaSolapada(ZonaDeCobertura unaZonaDeCobertura) {

		Double sumaDeAmbosRadios = (getRadio() * 1000) + (unaZonaDeCobertura.getRadio() * 1000);
		Double distanciaEntreEpicentros = epicentro.distanciaEnMetrosCon(unaZonaDeCobertura.getEpicentro());

		return distanciaEntreEpicentros < sumaDeAmbosRadios;
	}

	public Map<Aspecto, List<Observador>> getSuscriptores() {
		return this.suscriptores;
	}

	public void suscribir(Observador observador, Aspecto aspecto) {

		if (this.getSuscriptores().containsKey(aspecto)) {

			this.getSuscriptores().get(aspecto).add(observador);

		} else {
			List<Observador> observadoresDelAspecto = new ArrayList<Observador>();
			observadoresDelAspecto.add(observador);
			this.suscriptores.put(aspecto, observadoresDelAspecto);
		}
	}

	public void desuscribir(Observador observador, Aspecto aspecto) {
		// si el suscriptor no estaba en la lista de observadores del aspecto dado
		// entonces no hace nada
		// lo mismo si el aspecto no está definido como clave en los suscriptores
		if (this.getSuscriptores().containsKey(aspecto)) {

			List<Observador> observadoresDelAspecto = this.suscriptores.get(aspecto);

			observadoresDelAspecto.remove(observador);
		}

	}

	public void notificar(Muestra muestra, Aspecto aspecto) {
		this.getSuscriptores().get(aspecto).forEach(observador -> observador.eventoEnMuestra(this, muestra, aspecto));
	}

	public Boolean contieneMuestra(Muestra muestra) {

		return muestra.getUbicacion().distanciaEnMetrosCon(this.getEpicentro()) <= (this.getRadio() * 1000);
	}

}
