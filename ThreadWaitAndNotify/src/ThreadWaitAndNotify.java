class Calculator extends Thread{
	
	int sum;
	
	public void run() {
		synchronized (this) {
			for (int i=0;i<10;i++) {
				sum=sum+i;
			}
			notify();
		}
		
		
	}
}


public class ThreadWaitAndNotify {

	public static void main(String[] args) {
		Calculator c = new Calculator();
		c.start();
		//here the main method will start to wait on the thread.
		//as soon as it gets the notify signal, 
		synchronized (c) {
			try {
				c.wait();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//if we commnent the sync block what ahappens is that the  main thread completes the execution before the other thread does
		//hence the thread should weait to complete the execution
		System.out.println("Sum:"+c.sum);
	}
}
