package ar.unq.tpfinal.zonaDeCobertura;

import ar.unq.tpfinal.ubicacion.Ubicacion;

public class ZonaDeCobertura {
	
	private String nombre;
	private Double radio;
	private Ubicacion epicentro;
	
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
	
	//@TODO: opciones: Pasar distanciaEntreEpicentros a kilometros || crear metodo distanciaEnKilometrosCon en ubicacion || crear una clase medida
	public Boolean esZonaSolapada(ZonaDeCobertura unaZonaDeCobertura) {
		
		Double sumaDeAmbosRadios = getRadio() + unaZonaDeCobertura.getRadio();
		Double distanciaEntreEpicentros = epicentro.distanciaEnMetrosCon(unaZonaDeCobertura.getEpicentro());
		
		return distanciaEntreEpicentros < sumaDeAmbosRadios;
	}

}

