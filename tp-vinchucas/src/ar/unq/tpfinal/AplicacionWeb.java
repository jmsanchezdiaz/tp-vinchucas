package ar.unq.tpfinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.unq.tpfinal.usuario.Usuario;

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
		this.actualizarSiCorrespondeUsuario(nuevaMuestra.getUsuario());
		this.getMuestras().add(nuevaMuestra);
	}
	
	/**
	 * <p>
	 * Agrega la opinion a la muestra si esta existe, sino lanza un error, 
	 * notifica a las zonas de cobertura si su opinion valida la muestra y 
	 * chequea si debe actualizar el nivelDeConocimiento del usuario.
	 * </p>
	 * 
	 * @param Muestra muestra donde se agregara la opinion
	 * @param Opinion - opinion de la muestra
	 * @author juanma
	 * 
	 */
	public void agregarOpinionA(Muestra muestra, Opinion opinion) throws IllegalArgumentException {
		// notify si valida una muestra
		if(!this.getMuestras().contains(muestra)) throw new IllegalArgumentException("La muestra pasada no esta en la lista");
		
		this.actualizarSiCorrespondeUsuario(opinion.getUsuario());
		muestra.agregarOpinion(opinion);
		
	}
	
	private void actualizarSiCorrespondeUsuario(Usuario usuario) {
		//Obtengo las muestras hace 30 dias desde el dia actual.
		Stream<Muestra> muestrasHace30Dias = this.obtenerMuestrasHace(30);
		
		// Obtengo la cantidad de esos envios que son del usuario
		int cantidadDeEnviosDelUsuario = this.cantidadDeEnviosDe(usuario, muestrasHace30Dias);
		
		// Obtengo la cantidad de opiniones mandadas del usuario
		int cantidadDeOpinionesDelUsuario = this.cantidadDeOpinionesDe(usuario, muestrasHace30Dias);
		
		// Si es apto para subir de nivel, lo subo sino no.
		if(cantidadDeEnviosDelUsuario >= 10 && cantidadDeOpinionesDelUsuario >= 30)	usuario.subirDeNivel();
		else usuario.bajarDeNivel();

	}

	private int cantidadDeEnviosDe(Usuario usuario, Stream<Muestra> muestrasHace30Dias) {
		return muestrasHace30Dias
				.filter(muestra -> usuario.equals(muestra.getUsuario()))
				.collect(Collectors.toList()).size();
	}

	private int cantidadDeOpinionesDe(Usuario usuario, Stream<Muestra> muestras) {
		return muestras
				.mapToInt(muestra -> muestra.cantidadDeOpinionesDe(usuario))
				.sum();
	}

	/**
	 * Devuelve un Stream de muestras con las muestras de los ultimos dias pasados como parametros.
	 * Por ejemplo si queremos las muestras de hace 30 dias, cantidadDeDias seria 30.
	 * 
	 * @param int - cantidadDeDias - cantidad de dias anteriores entre las muestras y la fecha actual.
	 * @return Stream<Muestra>
	 */
	private Stream<Muestra> obtenerMuestrasHace(int cantidadDeDias) {
		LocalDate fechaDeHoy = LocalDate.now();
		return this.getMuestras()
		.stream()
		.filter(muestra -> muestra.getFechaCreacion().isEqual(fechaDeHoy.minusDays(cantidadDeDias)));
	}

	public List<Muestra> getMuestras() {
		return muestras;
	}

	public void setMuestras(List<Muestra> muestras) {
		this.muestras = muestras;
	}

}
