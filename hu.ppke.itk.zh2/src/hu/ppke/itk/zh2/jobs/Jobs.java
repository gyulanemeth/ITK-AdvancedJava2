package hu.ppke.itk.zh2.jobs;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;


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
	 * @throws InterruptedException 
	 */
	public static void main(final String[] args) throws InterruptedException {
	
		final CountDownLatch latch = new CountDownLatch(10);
		
		final Job mainJob = new Job("Main job") {
			
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				
				final Random random = new Random(UUID.randomUUID().hashCode());
				final int nextInt = random.nextInt(10);
				
				
				if (7 == nextInt) {
					
					System.out.println("starting new job...");
					final Job subJob = new Job("sub job") {
						
						@Override
						protected IStatus run(final IProgressMonitor monitor) {
							try {
								Thread.sleep(150L);
								latch.countDown();
								return Status.OK_STATUS;
							} catch (final InterruptedException e) {
								return Status.CANCEL_STATUS;
							}
						}
					};
					subJob.schedule();
					
					
				} else {
					System.out.println("not starting new job. random int was: " + nextInt);
				}
				
				try {
					Thread.sleep(100L);
					return Status.OK_STATUS;
				} catch (final InterruptedException e) {
					return Status.CANCEL_STATUS;
				}
			}
		};
		
		mainJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(final IJobChangeEvent event) {
				event.getJob().schedule(50L);
			}
		});
		
		mainJob.schedule();
		
		latch.await();
		
		System.out.println("done");
		
	}

}
