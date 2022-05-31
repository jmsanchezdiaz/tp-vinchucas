package ar.unq.tpfinal;

public enum NivelDeConocimiento {
	BASICO, EXPERTO;

	public NivelDeConocimiento siguienteNivel() {
		if(this.ordinal() == (NivelDeConocimiento.values().length - 1)) return this;
		return NivelDeConocimiento.values()[this.ordinal() + 1];
	}

	public NivelDeConocimiento anteriorNivel() {
		if(this.ordinal() == 0) return this;
		return NivelDeConocimiento.values()[this.ordinal() - 1];
	}
}
