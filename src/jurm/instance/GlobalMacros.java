package jurm.instance;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

import jurm.instance.Macro;
import jurm.URMException;
import jurm.util.JurmData;

/**
 * Guarda una lista de todas las macros
 * disponebles para cada programa a nivel global.
 *
 * Para que las macros puedan ser enlazadas es necesario que 
 * su nombre sea la primer linea del archivo
 */
public final class GlobalMacros {
	private static  ArrayList<Macro> list = new ArrayList<Macro>();

	public static void init()
	{
		Macro macro;
		Scanner scanner;
		File directory = JurmData.getDirectoryMacros();

		if (!directory.exists()) {
			directory.mkdirs();
		}

		for (File file : directory.listFiles()) {
			try{
				scanner = new Scanner(file);
				macro = new Macro(
					scanner.nextLine().replaceAll(" ","")
				);
				macro.preprocess(scanner);
				list.add(macro);
			}
			catch(URMException e){
				System.out.print(file.getName()+e);
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * @return La macro global de nombre name
	 * @return null si no existe en las macros globales
	 */
	public static Macro get(String name)
	{
		for (Macro macroT : list) {
			if (macroT.name().equals(name))
				return macroT;
		}
		return null;
	}
}