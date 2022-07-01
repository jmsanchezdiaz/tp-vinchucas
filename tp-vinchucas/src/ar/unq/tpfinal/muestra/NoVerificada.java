package ar.unq.tpfinal.muestra;

import java.util.Map;
import java.util.Map.Entry;

import ar.unq.tpfinal.NivelDeVerificacion;
import ar.unq.tpfinal.Opinable;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Resultado;
import ar.unq.tpfinal.ResultadoEmpate;

public class NoVerificada implements EstadoVerificacion {

	public NoVerificada() {}

	@Override
	public void agregarOpinion(Muestra muestra, Opinion opinion) {
		
		if(opinion.esOpinionDeExperto()) {
			muestra.setEstadoDeVerificacion(new VerificadaParcial());
		}
		
		muestra.addOpinion(opinion);
	}

	@Override
	public Resultado resultadoActual(Muestra muestra) {
		Map<Opinable, Long> mapOpiniones = muestra.contarOpiniones(muestra.getOpiniones());

		Resultado resultado = ResultadoEmpate.NO_DEFINIDO;
		long actualMayor = 0;

		for (Entry<Opinable, Long> op : mapOpiniones.entrySet()) {
			if (op.getValue() > actualMayor) {
				resultado = op.getKey();
				actualMayor = op.getValue();
			} else if (op.getValue() == actualMayor) {
				resultado = ResultadoEmpate.NO_DEFINIDO;
			}
		}
		return resultado;
	}

	@Override
	public NivelDeVerificacion valor() {
		return NivelDeVerificacion.NO_VERIFICADA;
	}
	
}
