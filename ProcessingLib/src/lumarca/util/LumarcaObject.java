package lumarca.util;

import lumarca.lineMap.LineMap;
import template.library.LumarcaLibrary;

public abstract class LumarcaObject extends ProcessingObject {

	public static LumarcaLibrary lumarca;
	public static LineMap lineMap;

	public LumarcaLibrary getLumarca() {
		return lumarca;
	}

	public void setLumarca(LumarcaLibrary lumarca) {
		this.lumarca = lumarca;
	}
}
