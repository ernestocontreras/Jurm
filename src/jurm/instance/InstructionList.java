package jurm.instance;

import java.util.HashMap;

import jurm.instance.Instruction;
import jurm.instance.Operation;

final class InstructionList {
	private final HashMap<Integer, Instruction> list;

	public InstructionList() {
		list = new HashMap<Integer, Instruction>();
	}

	public void put(int number, final Instruction instruction) {
		this.list.put(number, instruction);
	}

	public Instruction get(int number) {
		return this.list.get(number);
	}

	public int last() {
		return this.list.size();
	}

	public int first() {
		return 1;//Todo programa comienza con la instrucción 1
	}

	public void print() {
		System.out.print("Instrucciones > ");
		for (Integer i : this.list.keySet()) {
			System.out.print(i + " : ");
			System.out.print(this.get(i).name());
			System.out.print("["+this.get(i).params().length +"]" + " , ");
		}
		System.out.println();
	}

}