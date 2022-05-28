package ar.unq.tpfinal;

import java.util.ArrayList;
import java.util.List;

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

	public List<Muestra> getMuestras() {
		return muestras;
	}

	public void setMuestras(List<Muestra> muestras) {
		this.muestras = muestras;
	}
	
}
