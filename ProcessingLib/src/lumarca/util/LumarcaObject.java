package lumarca.util;

import template.library.LumarcaLibrary;

public abstract class LumarcaObject extends ProcessingObject {

	public static LumarcaLibrary lumarca;

	public LumarcaLibrary getLumarca() {
		return lumarca;
	}

	public void setLumarca(LumarcaLibrary lumarca) {
		this.lumarca = lumarca;
	}
}
