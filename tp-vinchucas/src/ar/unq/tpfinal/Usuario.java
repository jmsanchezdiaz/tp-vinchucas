package ar.unq.tpfinal;

public abstract class Usuario {
	private String nombre;
	private String id;
	private EstadoUsuario estado;
	
	public void enviarMuestra(AplicacionWeb app, Ubicacion ubi, Foto foto, EspecieVinchuca especie) {
		// enviar
	}
	
	public void opiniarMuestra(AplicacionWeb app, Muestra muestra, Opiniones opinion) {
		// opinar
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public EstadoUsuario getEstado() {
		return estado;
	}

	public void setEstado(EstadoUsuario estado) {
		this.estado = estado;
	}
	
	
}
