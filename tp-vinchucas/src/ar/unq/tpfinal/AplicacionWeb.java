package ar.unq.tpfinal;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 	Clase donde se almacenaran las muestras y simulara ser una aplicacion web.
 *  Utilizamos el patrón Singleton, para que no puedan crearse más de 1 instancia por clase.
 * </p>
 * @author juan manuel sanchez diaz
 *
 */

public class AplicacionWeb {
	static private AplicacionWeb app = null;
	private List<Muestra> muestras;
	
	private AplicacionWeb() {
		muestras  = new ArrayList<Muestra>();
	}
	
	public static AplicacionWeb getAplicacionWeb() {
		if(app == null) {
			app = new AplicacionWeb();
		}
		return app;
	}
	
	/**
	 * <p>
	 * Agrega la muestra pasada como párametro, notifica a las zonas de cobertura de su subida y 
	 * chequea si debe actualizar el nivelDeConocimiento del usuario.
	 * </p>
	 * 
	 * @param nuevaMuestra - Muestra para agregar a la applicacion web.
	 * @author juanma
	 * 
	 */
	public void agregarMuestra(Muestra nuevaMuestra) {
		// Falta agregar notify del observer
		this.getMuestras().add(nuevaMuestra);
		// this.verificarActualicacionDe(nuevaMuestra.getUsuario());
	}
	
	private void verificarActualizacionDe(Usuario usuario) {
		// TODO - Algoritmo de nivelDeConocimiento
	}

	public void agregarOpinionA(Muestra muestra, Opiniones opinion) {
		//Por agregar
	}

	public List<Muestra> getMuestras() {
		return muestras;
	}

	public void setMuestras(List<Muestra> muestras) {
		this.muestras = muestras;
	}

}
