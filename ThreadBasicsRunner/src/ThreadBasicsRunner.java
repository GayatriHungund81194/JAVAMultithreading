import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Task1 extends Thread{
	
	public void run () {
		System.out.println("Task 1 stasrted");
		System.out.println("State of task 1:"+this.getState().toString());
		for(int i=101;i<199;i++) {
			System.out.print(i+" ");
			//Thread yield method is used to tell the cpu to stop the threwad or it is willing to give up the CPu to some other thread for execution
			Thread.yield();
		}
		
		System.out.println("Task1 Done");
		System.out.println("State of task 1:"+this.getState().toString());
	}
	
}


class Task2 implements Runnable{
	
	public void run () {
		System.out.println("Task 2 started");
		for(int i=101;i<199;i++) {
			System.out.print(i+" ");
		}
		
		System.out.println("Task2 Done");
	}
	
}



public class ThreadBasicsRunner {
	
	
	public static void main(String[] args) {
		
		//By making use of threads we make used of cpu more efficiently even if some tasks wait for I/o or something
		//Threads have diff state: new,runnable, running, blocked/waiting, terminated/dead.
		//new- ready to run but no one has told to start it yet
		//terminated- task is done and thread is dead
		//running- when a particular thread prints output then that is called running
		//runnable- at a time cpu will have only one thread to be executed so the other thread know swhat is to be done but cannot do anything and that is 
		//runnable
		//blocked- it is the state when the thread waits for some input or resources from other thread. 
		
		Task1 t1 = new Task1();
		Task2 t2 = new Task2();
		Thread t = new Thread(t2);
		System.out.println("State of task 1:"+t1.getState().toString());
		//sets priority to the thread. The priority should be in between the min priority and max priority.
		//by default normal priority is assigned. assign anything between 1-10
		//priority is just a request, it does not mean that the task will always be executed if it has more priority,
		//it depends on CPU
		t1.setPriority(2);
		t.setPriority(10);
		t1.start();
		t.start();
		
		try {
			
			//join because it shiuld waitt to task 1 for finish
			t1.join();
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//synchronized- when we add synchronized keyword, we say that only one thread is allowed to access the shared data at a time
		//puts a lot of overhead coz others will wait. perf gets affected
		System.out.println("Task 3 started");
		for(int i=101;i<199;i++) {
			System.out.print(i+" ");
		}
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("New executor!!!1");
			}
		});
		
		Future fu = es.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("From executor service");
			}
		});
//		try {
//		//	System.out.println("Future Object:"+fu.get().toString());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		// the above code gives null pointer exception, hence to avoid that use callable, when we use callable interface
		Future call = es.submit(new Callable() {

			@Override
			public Object call() throws Exception {
				// TODO Auto-generated method stub
				return "RESULT";
			}
			
		});
		
		try {
			System.out.println(""+call.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//we can have synchronized code or methods but we can have asynchronized block to give access to one thread at a time
//		synchronized(this) {
//			
//		}
		//static method can also be synced
		
		//if one thread is waiting for obejct then notify(); should be used
		
		es.shutdown();
		
		//If we use the thread executor then we can check if the thread executed successfully or not. This can be done 
		//by using future object to chk the return val;
		System.out.println("Task3 Done");
		
		System.out.println("Main Done");
		
		
		
	}
}
