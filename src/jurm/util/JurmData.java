package jurm.util;

import java.io.File;

/**
 * Provee una forma de comunicarse con los recursos externos
 */
public final class JurmData {
	public static File getDirectoryMacros(){
		return new File(
			"data/macros"
		);
	}

	public static File getDirectoryExamples(){
		return new File(
			"data/examples"		
		);
	}
}