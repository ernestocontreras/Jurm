package jurm.instance;

import jurm.token.Token;

import jurm.instance.Instance;
import jurm.instance.RegisterList;
import jurm.instance.InstructionList;

import jurm.ExecutionStack;

/**
 * Representa cada uno de las macros de un programa.
 * Cada macro tiene instrucciones y registros independientes,
 * el resultado de la ejecución de una macro siempre termina en el
 * registro 1 de su lista de registros.
 */
public final class Macro extends Instance{
	private RegisterList parentRegisters;

	public Macro(String name) {
		this.name = name;
		this.parentRegisters = this.registerList;
	}

	/**
	 * Inicializa los registros propios de esta macro
	 * con los valores de los registros pasados como argumentos.
	 * Esto con el fin de emular un paso por valor.
	 * @param parentRegisters los registros de la macro que hizo la llamada
	 * @param args posiciones del registro a ser copiadas.
	 */
	public void initRegister(RegisterList parentRegisters , String [] args) {
		this.parentRegisters = parentRegisters;
		this.registerList.copy(parentRegisters, args);
	}

	/**
	 * @return el numero de argumentos que necesita esta
	 * macro para ser llamada.
	 */
	public int arguments() {
		return this.registerList.size();
	}

	public String name() {
		return this.name;
	}

	/**
	 * Copia el resultado de la ejecución de esta macro
	 * a la macro que la llamó y se autoelimina de la pila de ejecución.
	 */
	@Override
	protected void endSignal() {
		if (!this.isFinished){
			this.parentRegisters.put("1", this.result());
			ExecutionStack.pop();
		}
	};
}