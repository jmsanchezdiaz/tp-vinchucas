package ar.unq.tpfinal.muestra;

import java.util.Map;
import java.util.Map.Entry;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.Aspecto;
import ar.unq.tpfinal.NivelDeVerificacion;
import ar.unq.tpfinal.Opinable;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Resultado;
import ar.unq.tpfinal.ResultadoEmpate;

public class VerificadaParcial implements EstadoVerificacion {

	@Override
	public void agregarOpinion(AplicacionWeb app, Muestra muestra, Opinion opinion) {

		if (opinion.esOpinionDeExperto()) {
			muestra.addOpinion(opinion);

			if (muestra.opinoIgualQueOtroExperto(opinion)) {
				muestra.setEstadoDeVerificacion(new Verificada(resultadoActual(muestra)));
				app.zonasDeLaMuestra(muestra).forEach(zona -> zona.notificar(muestra, Aspecto.MUESTRA_VERIFICADA));
			}
		}

	}

	@Override
	public Resultado resultadoActual(Muestra muestra) {
		Map<Opinable, Long> mapOpiniones = muestra.mapOpinionesDeExperto();

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
		return NivelDeVerificacion.VERIFICADA_PARCIAL;
	}

}
