package jurm.instance;

import java.util.TreeMap;

/**
 * Representa los registros de la máquina
 */
public class RegisterList 
{
	private TreeMap<String, Integer> list;
	private TreeMap<String, Integer> initialList;

	private boolean isInitialized;

	public RegisterList() {
		this.list = new TreeMap<String, Integer>();
		this.initialList = new TreeMap<String, Integer>();

		this.isInitialized = false;
	}

	/**
	 * Colóca registros de inicio
	 */

	public void putInitial(String index, int value){
		initialList.put(index,value);
		list.put(index,value);
	}

	public void put(String index, int value) {
		list.put(index,value);
	}

	public int get(String index) {
		if (list.containsKey(index))
			return list.get(index);
		else 
			return 0;
	}

	public int size() {
		return this.list.size();
	}

	/**
	 * Repone los registros de la definición inicial
	 */
	@SuppressWarnings("unchecked")
	public void reset(){
		list = (TreeMap<String, Integer>)initialList.clone();
	}

	/**
	 * La primera vez que se haga una copia
	 * de registros , la lista almacena los registros iniciales
	 * y sus valores para reponerlos en un reseteo.
	 */
	public void copy(RegisterList registers , String [] args){
		int j = 0;
		for (String reg : list.keySet()) {
			list.put(reg, registers.get(args[j]));
			j++;
			if (j == args.length)break;
		}
	}

	public String represent(){
		StringBuilder sb = new StringBuilder();
		for(String i: this.list.keySet()){
			sb.append(i);
			sb.append(" = ");
			sb.append(this.get(i));
			sb.append("\n");
		}
		return sb.toString();
	}
}