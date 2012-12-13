package hu.ppke.itk.zh2.singleton;

/**
 * Singleton implementation.
 * @author akitta
 */
public class Singleton {

	//TODO
	//implement a singleton with lazy initialization.
	//also consider thread safety.
	//use the above constructor. 
	//you may modify the visibility of the constructor.

	
	/**
	 * Constructor for the singleton.
	 * <b>NOTE:&nbsp;</b>Do not modify the business logic inside the constructor.<br>
	 * You may alter the visibility if you want.
	 */
	Singleton() {
		try {
			Thread.sleep(2000L);
		} catch (final InterruptedException e) {
			throw new RuntimeException("Error while creating singleton instance", e);
		}
	}
	
	
	/**
	 * This class is just for testing the singleton.
	 * <p>
	 * <b>NOTE:&nbsp;</b>Do not modify this private static class.
	 * @author akitta
	 */
	private static final class SingletonTest {
		
		public static void main(final String[] args) {
			
		}
		
	}
	
}
