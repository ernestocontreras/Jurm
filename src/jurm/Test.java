package jurm;

import jurm.URM;
import java.util.Scanner;
import java.io.File;
public class Test {
	public static void main(String[] args) throws Exception{
		URM urm = new URM();
		System.out.println("Preprocesamiento...");
		urm.init(
			new Scanner(
				new File("data/examples/test.urm")
			).useDelimiter("\\Z").next()
		);

		System.out.println("Ejecición...");
		urm.run();

		System.out.println("Resultados...");
		System.out.println(urm.represent());
		
	}
}