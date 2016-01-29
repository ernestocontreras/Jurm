package main.controller;

import main.controller.URMController;

/**
 * Para lograr que la ejecución paso a paso
 * con tiempo de espera pueda ser mostrada al usuario, 
 * es necesario crear un hilo de ejecución para dicho proceso.
 */
final class StepByStepThread extends Thread{
	private final int MAXTIME = 1300;
	private URMController urmController;
	private long speed;
	private boolean paused;

	public StepByStepThread(URMController urmController){
		super();
		this.urmController = urmController;
		this.speed = MAXTIME/2;
		this.paused = true;
	}

	/**
	 * @param speed la prpoporción de rapidez del
	 * hilo, valores de 1,10
	 */
	public void setSpeed(int speed){
		this.speed = MAXTIME - speed;
	}

	@Override
	public synchronized void run(){
		try {
			paused = false;
			urmController.runStep();
			while(!urmController.isFinished())
			{
				Thread.sleep(speed);
				if (paused)break;
				urmController.runStep();
			}
			paused = true;
		}
		catch(InterruptedException e){
			System.out.println(e.toString());
		}
	}

	public void pause(){
		this.paused = true;
	}

	public boolean isPaused()
	{
		return paused;
	}
}