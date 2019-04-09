package accounting;
import agency.*;

class BankAccount {
	   private double balance;
	   public BankAccount(double bal) { balance = bal; }
	   public BankAccount() { this(0); }
	   public synchronized double getBalance() { return balance; }
	   public synchronized void deposit(double amt) {
		  double temp = balance;
		  temp = temp + amt;
		  try {
			 Thread.sleep(300); // simulate production time
		  } catch (InterruptedException ie) {
			 System.err.println(ie.getMessage());
		  }
		  System.out.println("after deposit balance = $" + temp);
		  balance = temp;
		  notify();
	   }
	   public synchronized void withdraw(double amt) {
		  while (balance < amt) {
			 try {
				wait(); // wait for funds
			 } catch (InterruptedException ie) {
				System.err.println(ie.getMessage());
			 }
		  }
		  double temp = balance;
		  temp = temp - amt;
		  try {
			 Thread.sleep(200); // simulate consumption time
		  } catch (InterruptedException ie) {
			 System.err.println(ie.getMessage());
		  }
		  System.out.println("after withdrawl balance = $" + temp);
		  balance = temp;
	   }
	}


	class Consumer extends Agent {
	   private BankAccount account;
	   private int numWithdraws;
	   public Consumer(BankAccount acct) {
		  super();
		  numWithdraws = 0;
		  account = acct;
	   }
	   public void update() {
		  if (numWithdraws++ < 10) {
			  System.out.println("Withdrawing $10");
			  account.withdraw(10);
		  } else {
			  dead = true;
		  }
	   }
	}


	class Producer extends Agent {
	   private BankAccount account;
	   private int numDeposits;
	   public Producer(BankAccount acct) {
		  super();
		  account = acct;
		  numDeposits = 0;
	   }
	   public void update() {
		   if (numDeposits++ < 10) {
			   System.out.println("Depositing $5");
				  account.deposit(5);
			  } else {
				  dead = true;
			  }
	   }
	}

	public class Bank extends Facilitator {

		public static void main(String[] args) {
			Bank bf = new Bank();
			bf.multiThreadMode = true;
			BankAccount account = new BankAccount(100);
			bf.add(new Producer(account));
			bf.add(new Consumer(account));
			bf.start();
		}
	}