package ar.unq.tpfinal.muestra;

import ar.unq.tpfinal.AplicacionWeb;
import ar.unq.tpfinal.NivelDeVerificacion;
import ar.unq.tpfinal.Opinion;
import ar.unq.tpfinal.Resultado;

public interface EstadoVerificacion {

	public void agregarOpinion(AplicacionWeb app, Muestra muestra, Opinion opinion);

	public Resultado resultadoActual(Muestra muestra);

	public NivelDeVerificacion valor();
}
