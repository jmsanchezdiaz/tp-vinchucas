package Muestra;

import ar.unq.tpfinal.NivelDeVerificacion;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Resultado;

public class Verificada implements EstadoVerificacion {
	
	private Resultado resultado;
	
	public Verificada(Resultado resultado) {
		this.resultado = resultado;
	}

	@Override
	public void agregarOpinion(Muestra muestra, Opinion opinion) {}

	@Override
	public Resultado resultadoActual(Muestra muestra) {
		return this.resultado;
	}

	@Override
	public NivelDeVerificacion valor() {
		return NivelDeVerificacion.VERIFICADA;
	}

}
