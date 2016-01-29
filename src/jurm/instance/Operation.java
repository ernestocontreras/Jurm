package jurm.instance;

import jurm.instance.RegisterList;

/**
 * Representa las operaciónes básicas de URM y las 
 * llamadas a macros.
 */
enum Operation {
	Z(1) { // Z(N)
		@Override
		public boolean operate(RegisterList register,String [] args) {
			register.put(args[0], 0);
			return true;		
		}
	},

	S(1) { // S(N)
		@Override
		public boolean operate(RegisterList register,String [] args) {
			register.put(args[0], register.get(args[0]) + 1);
			return true;
		}
	},

	T(2) {//T (N, M)
		@Override
		public boolean operate(RegisterList register,String [] args) {
			register.put(
				args[1],
				register.get(args[0])
			);
			return true;
		}
	},
	
	J(3) {// J ( N, M, Q)
		@Override
		public boolean operate(RegisterList register,String [] args) {
			return register.get(args[0]) == register.get(args[1]);
		}
	},

	MACRO(){};

	// Cantidad de argumentos requerida por la operación
	private int arguments;

	Operation() {
		this(0);
	}

	Operation(int arguments) {
		this.arguments = arguments;
	}

	public int arguments() {
		return this.arguments;
	}

	public String getName() {
		return this.name();
	}
	public void setName(String name) {	}


	public void setArguments(int arguments) {
		this.arguments = arguments;
	}

	/**
	 * Realiza la operación correspondiente sobre los registros indicados
	 * @param register lista de registros que la operación modifica
	 * @param args los registros que serán afectados
	 */
	public boolean operate(RegisterList register,String [] args){
		return true;
	}
	
	/**
	 * @return La operacion básica si se reconoce la cadena
	 * @return Macro si no se reconoce como operación básica
	 */
	public static Operation get(String name)
	{
		for (Operation op: Operation.values() ) {
			if (name.equals(op.toString()) ){
				return op;
			}
		}
		return Operation.MACRO;
	}
}
