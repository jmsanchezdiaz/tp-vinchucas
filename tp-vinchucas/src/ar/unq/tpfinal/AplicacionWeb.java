package ar.unq.tpfinal;

public class AplicacionWeb {
	static private AplicacionWeb app = null;
	
	private AplicacionWeb() {}
	
	public static AplicacionWeb getAplicacionWeb() {
		if(app == null) {
			app = new AplicacionWeb();
		}
		return app;
	}
	
}
