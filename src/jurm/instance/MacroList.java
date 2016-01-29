package jurm.instance;

import java.util.ArrayList;

import jurm.instance.Macro;
import jurm.instance.GlobalMacros;

import jurm.URMException;

final class MacroList {
	private ArrayList<Macro> list;

	public MacroList() {
		this.list = new ArrayList<Macro>();
	}

	public final void put(Macro macro) {
		this.list.add(macro);
	}

	/**
	 * Busca primero en las macros globales y después en las
	 * definidas por el usuario.
	 * @return la macro con ese nombre
	 * @throws URMException si no hay ninguna macro con ese nombre 
	 */
	public final Macro get(String name) throws URMException {
		Macro globalMacro = GlobalMacros.get(name);
		if (globalMacro != null) return globalMacro;

		// No es macro global
		for (Macro macroT : this.list) {
			if (macroT.name().equals(name))
				return macroT;
		}
		// No existe esta macro
		throw new URMException(this,"No existe la macro "+name);
	}

	public final int size() {
		return this.list.size();
	}

	public void print()
	{
		System.out.print("Macros > \n");
		for (Macro macroT : this.list) {
			System.out.print("\t");
			macroT.print();
		}
		System.out.println();
	}
}