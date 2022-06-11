package ar.unq.tpfinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.unq.tpfinal.filtro.IFiltro;
import ar.unq.tpfinal.usuario.Usuario;
import ar.unq.tpfinal.zonaDeCobertura.ZonaDeCobertura;

/**
 * <p>
 * Clase donde se almacenaran las muestras y simulara ser una aplicacion web.
 * Utilizamos el patrón Singleton, para que no puedan crearse más de 1
 * instancia por clase.
 * </p>
 * 
 * @author juan manuel sanchez diaz
 *
 */

public class AplicacionWeb {
	static private AplicacionWeb app;
	private List<Muestra> muestras;
	private List<ZonaDeCobertura> zonasDeCobertura;

	/**
	 * Crea una instancia de AplicacionWeb, es privado para que solo pueda utilizado
	 * dentro de la clase.
	 * 
	 * @return AplicacionWeb
	 */
	private AplicacionWeb() {
		muestras = new ArrayList<Muestra>();
		zonasDeCobertura = new ArrayList<ZonaDeCobertura>();
	}

	/**
	 * Crea una unica instancia de AplicacionWeb, si se intenta crear otra devuelve
	 * la existente.
	 * 
	 * @return AplicacionWeb
	 */
	public static AplicacionWeb getAplicacionWeb() {
		if (app == null) {
			app = new AplicacionWeb();
		}
		return app;
	}

	/**
	 * <p>
	 * Agrega la muestra pasada como párametro, notifica a las zonas de cobertura
	 * de su subida y chequea si debe actualizar el nivelDeConocimiento del usuario.
	 * </p>
	 * 
	 * @param nuevaMuestra - Muestra para agregar a la applicacion web.
	 * @author juanma
	 * 
	 */
	public void agregarMuestra(Muestra nuevaMuestra) {
		this.actualizarSiCorrespondeUsuario(nuevaMuestra.getUsuario());

		if (!this.contieneMuestra(nuevaMuestra)) {
			this.getMuestras().add(nuevaMuestra);
			this.zonasDeLaMuestra(nuevaMuestra).forEach(zona -> zona.notificar(nuevaMuestra, Aspecto.MUESTRA_ENVIADA));

		}
	}

	private List<ZonaDeCobertura> zonasDeLaMuestra(Muestra nuevaMuestra) {
		return this.getZonasDeCobertura().stream().filter(zona -> zona.contieneMuestra(nuevaMuestra))
				.collect(Collectors.toList());
	}

	public boolean contieneMuestra(Muestra muestra) {
		return this.getMuestras().contains(muestra);
	}

	public boolean contieneZona(ZonaDeCobertura zona) {
		return this.getZonasDeCobertura().contains(zona);
	}

	/**
	 * <p>
	 * Agrega la opinion a la muestra si esta existe, sino lanza un error, notifica
	 * a las zonas de cobertura si su opinion valida la muestra y chequea si debe
	 * actualizar el nivelDeConocimiento del usuario.
	 * </p>
	 * 
	 * @param Muestra muestra donde se agregara la opinion
	 * @param Opinion - opinion de la muestra
	 * @author juanma
	 * 
	 */
	public void agregarOpinionA(Muestra muestra, Opinion opinion) {

		this.actualizarSiCorrespondeUsuario(opinion.getUsuario());
		muestra.agregarOpinion(opinion);

//		if (muestra.esMuestraVerificada()) {
//			this.zonasDeLaMuestra(muestra).forEach(zona -> zona.notificar(muestra, Aspecto.MUESTRA_VERIFICADA));
//		}

	}

	/**
	 * Actualiza el nivel de conocimiento del usuario provisto si cumple con los
	 * requisitos.
	 * 
	 * @param {Usuario} - un usuario.
	 */
	private void actualizarSiCorrespondeUsuario(Usuario usuario) {
		// Obtengo las muestras hace 30 dias desde el dia actual.
		List<Muestra> muestrasHace30Dias = this.obtenerMuestrasHace(30);

		// Obtengo la cantidad de esos envios que son del usuario
		int cantidadDeEnviosDelUsuario = this.cantidadDeEnviosDe(usuario, muestrasHace30Dias);

		// Obtengo la cantidad de opiniones mandadas del usuario
		int cantidadDeOpinionesDelUsuario = this.cantidadDeOpinionesDe(usuario, muestrasHace30Dias);

		// Si es apto para subir de nivel, lo subo sino no.
		if (cantidadDeEnviosDelUsuario >= 10 && cantidadDeOpinionesDelUsuario >= 30)
			usuario.subirDeNivel();
		else
			usuario.bajarDeNivel();

	}

	/**
	 * Devuelve la cantidad de envios de un usuario en una lista de muestras
	 * pasadas.
	 * 
	 * @param {Usuario}       - usuario
	 * @param {List<Muestra>} - listaDeMuestras
	 * @return int
	 */
	public int cantidadDeEnviosDe(Usuario usuario, List<Muestra> listaDeMuestras) {
		return listaDeMuestras.stream().filter(muestra -> muestra.fueEnviadaPor(usuario)).collect(Collectors.toList())
				.size();
	}

	/**
	 * Devuelve la cantidad de opiniones de un usuario en una lista de muestras
	 * pasadas.
	 * 
	 * @param {Usuario}       - usuario
	 * @param {List<Muestra>} - listaDeMuestras
	 * @return int
	 */
	public int cantidadDeOpinionesDe(Usuario usuario, List<Muestra> listaDeMuestras) {
		return listaDeMuestras.stream().filter(muestra -> muestra.elUsuarioYaOpino(usuario))
				.collect(Collectors.toList()).size();
	}

	/**
	 * Devuelve una Lista de muestras con las muestras de los ultimos dias pasados
	 * como parametros. Por ejemplo si queremos las muestras de hace 30 dias,
	 * cantidadDeDias seria 30.
	 * 
	 * @param int - cantidadDeDias - cantidad de dias anteriores entre las muestras
	 *            y la fecha actual.
	 * @return List<Muestra>
	 */
	public List<Muestra> obtenerMuestrasHace(int cantidadDeDias) {
		LocalDate fechaDeHoy = LocalDate.now();
		LocalDate fechaInicio = fechaDeHoy.minusDays(cantidadDeDias);

		return this.getMuestras().stream()
				.filter(muestra -> muestra.fuePublicadaDentroDeEsteRango(fechaInicio, fechaDeHoy))
				.collect(Collectors.toList());
	}

	public List<Muestra> getMuestras() {
		return muestras;
	}

	protected void setMuestras(List<Muestra> muestras) {
		this.muestras = muestras;
	}

	public List<ZonaDeCobertura> getZonasDeCobertura() {
		return this.zonasDeCobertura;
	}

	public void agregarZona(ZonaDeCobertura zona) {
		if (!this.contieneZona(zona)) {
			this.getZonasDeCobertura().add(zona);
		}
	}

	public void eliminarZona(ZonaDeCobertura zona) {

		if (this.contieneZona(zona)) {
			this.getZonasDeCobertura().remove(zona);
		}
	}

	public void eliminarMuestra(Muestra muestra) {

		if (this.contieneMuestra(muestra)) {
			this.getMuestras().remove(muestra);
		}
	}

	protected void setZonasDeCobertura(List<ZonaDeCobertura> zonas) {
		this.zonasDeCobertura = zonas;
	}

	/**
	 * Pasado un filtro, devuelve todas las zonas que cumplan con el.
	 * 
	 * @param {IFiltro} - un filtro.
	 * @return List<Muestra> - Muestras encontradas.
	 */
	public List<Muestra> buscar(IFiltro filtroMock) {
		return filtroMock.filter(this.getMuestras());
	}
}
