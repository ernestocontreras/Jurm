package jurm.util;

/**
 * Almacena los datos de la posición inicial y final
 * de los caracteres de cada linea ingresada
 */
public final class LineRange{
	public final int start;
	public final int end;

	public LineRange(int start, int end) {
		this.start = start;
		this.end = end;
	}
}