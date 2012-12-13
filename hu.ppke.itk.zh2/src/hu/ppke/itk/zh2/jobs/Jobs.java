package hu.ppke.itk.zh2.jobs;


/**
 * Class for testing Eclipse Jobs framework.
 * @author akitta
 * @see http://www.eclipse.org/articles/Article-Concurrency/jobs-api.html 
 */
public class Jobs {

	//TODO
	//initialize a counter to 10. make it thread safe.
	//create a job (main-job) instance which does do followings:
	// - sleeps 100 ms.
	// - randomize an integer between 0 and .10
	// - if the random number equals with 7.
	// - the job starts a brand new job (sub-job) instance.
	//   # the sub-job does nothing but sleeps 150 ms.
	//   # after the 150 ms sleep counts down 1 from the previously initialized counter.
	// - after the main-job finished its running
	// - sleeps 50 ms.
	// - re-schedules itself.
	
	
	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
	}

}
