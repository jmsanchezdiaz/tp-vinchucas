package ar.unq.tpfinal.organizacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.unq.tpfinal.Aspecto;
import ar.unq.tpfinal.FuncionalidadExterna;
import ar.unq.tpfinal.Muestra;
import ar.unq.tpfinal.Observador;
import ar.unq.tpfinal.TipoDeOrganizacion;
import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;

public class Organizacion implements Observador {

	public TipoDeOrganizacion tipo;
	public int cantidadDePersonal;

	public Map<Aspecto, List<ZonaDeCobertura>> suscripcionesAZonasPorAspecto = new HashMap<Aspecto, List<ZonaDeCobertura>>();
	public Map<Aspecto, FuncionalidadExterna> funcionalidadesPorAspecto = new HashMap<Aspecto, FuncionalidadExterna>();

	/**
	 * Crea una instancia de Organizacion
	 * 
	 * @param {TipoDeOrganizacion} tipoDeOrganizacion.
	 * @param {int}                cantidadDePersonal - numero de personas que
	 *                             trabajan en la organización.
	 * @throws {IllegalArgumentException} en caso de instanciar pasando
	 *                                    cantidadDePersonal menor a cero.
	 *
	 */

	public Organizacion(TipoDeOrganizacion tipoDeOrganizacion, int cantidadDePersonal) throws IllegalArgumentException {
		this.setTipo(tipoDeOrganizacion);
		this.setCantidadDePersonal(cantidadDePersonal);
	}

	private void setCantidadDePersonal(int cantidadDePersonal) throws IllegalArgumentException {

		if (cantidadDePersonal >= 0) {
			this.cantidadDePersonal = cantidadDePersonal;
		} else {
			throw new IllegalArgumentException("La cantidad de Personal no puede ser negativa");
		}

	}

	private void setTipo(TipoDeOrganizacion tipoDeOrganizacion) {
		this.tipo = tipoDeOrganizacion;
	}

	public TipoDeOrganizacion getTipoDeOrganizacion() {
		return this.tipo;
	}

	public Map<Aspecto, List<ZonaDeCobertura>> getSuscripcionesAZonasPorAspecto() {

		return this.suscripcionesAZonasPorAspecto;
	}

	public Map<Aspecto, FuncionalidadExterna> getFuncionalidadesPorAspecto() {
		return funcionalidadesPorAspecto;
	}

	public void suscribirseAZona(ZonaDeCobertura zona, Aspecto aspecto) {

		if (this.getSuscripcionesAZonasPorAspecto().containsKey(aspecto)) {
			this.getSuscripcionesAZonasPorAspecto().get(aspecto).add(zona);

		} else {
			List<ZonaDeCobertura> observadoresDelAspecto = new ArrayList<ZonaDeCobertura>();
			observadoresDelAspecto.add(zona);
			this.getSuscripcionesAZonasPorAspecto().put(aspecto, observadoresDelAspecto);
		}
	}

	public void desuscribirseDeZona(ZonaDeCobertura zona, Aspecto aspecto) {
		// si la zona no estaba registrada en las suscripciones según el aspecto dado,
		// no se hace nada
		// lo mismo sucede si el aspecto no está definido como clave
		if (this.getSuscripcionesAZonasPorAspecto().containsKey(aspecto)) {

			List<ZonaDeCobertura> observadoresDelAspecto = this.getSuscripcionesAZonasPorAspecto().get(aspecto);
			observadoresDelAspecto.remove(zona);

		}
	}

	@Override
	public void eventoEnMuestra(ZonaDeCobertura zona, Muestra muestra, Aspecto aspecto) {
		if (this.getFuncionalidadesPorAspecto().containsKey(aspecto)) {
			this.getFuncionalidadesPorAspecto().get(aspecto).nuevoEvento(zona, this, muestra);
		}

	}

}
