/**
 * 
 */
package hu.ppke.itk.swt.demo;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

/**
 * @author magdi
 *
 */
public class JobMain {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		CountDownLatch latch = new CountDownLatch(100);
		Set<Job> set = new HashSet<Job>();
		for (int i = 0; i < 100; i++) {
			set.add(createJob(latch, UUID.randomUUID()));
		}

		for (Job job : set) {
			job.schedule();
		}
		
		latch.await();
		System.out.println("\n\nDone!");
		
//		Job job = new Job("asdasd") {
//			
//			@Override
//			protected IStatus run(IProgressMonitor monitor) {
//				System.out.println("run" + monitor);
////				return Status.OK_STATUS;
//				return Status.CANCEL_STATUS;
//			}
//		};
//		
//		job.addJobChangeListener(new JobChangeAdapter() {
//			@Override public void done(IJobChangeEvent event) {
//				System.out.println(event.getResult());
//				super.done(event);
//			}
//		});
//		
////		job.schedule();
		
		
	}
	
	
	private static Job createJob(final CountDownLatch latch, final Object object) {
		Job job = new Job(String.valueOf(object)) {
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
				try {
					Thread.sleep(new Random(object.hashCode()).nextInt(5000));
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				}
				
				return String.valueOf(object).contains("2") ? Status.CANCEL_STATUS : Status.OK_STATUS;
				} finally {
					schedule(5000L);
				}
			}
		};
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				System.out.println(
						event.getJob().getName() + " " + event.getResult());
				latch.countDown();
			}
		});
		return job;
	}

}
