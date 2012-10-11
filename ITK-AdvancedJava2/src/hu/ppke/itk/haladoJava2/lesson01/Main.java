package hu.ppke.itk.haladoJava2.lesson01;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;


/*
 * http://docs.oracle.com/javase/tutorial/collections/index.html
 */
public class Main {
	private static Measurement[] measurements;
	private static List<Measurement> elementsToFind;
	private static List<Measurement> elementsToRemove;
	
	static interface Test {
		public void applyTest ();
	}
	
	public static void test (Test test) {
		Timer.start();
		test.applyTest();
		Timer.printElapsedTime();
	}


	static {
		System.out.println("init...");
		
		
		
		try {
//			Collection<Measurement> container = new ArrayList<Measurement>();
//			MeasurementDB.readFromCsv("meresek.csv", container);
//			MeasurementDB.serializeDB(container, "measurements.ser");
//			
//			System.exit(0);
			
			measurements = MeasurementDB.deserializeDB("measurements.ser");
			
			elementsToFind = new LinkedList<Measurement>();
			elementsToRemove = new LinkedList<Measurement>();
			for (int i = 0; i < measurements.length; i++) {
				if (i % 333 == 0) {
					elementsToFind.add(measurements[i]);
				}
				if (i % 33 == 0) {
					elementsToRemove.add(measurements[i]);
				}
			}
			Collections.shuffle(elementsToFind);
			Collections.shuffle(elementsToRemove);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		List<Measurement> container = new ArrayList<Measurement>();
//		Collections.shuffle(container);
//		measurements = (Measurement[]) container.toArray();
		System.out.println("init finished...");

	}
	
	public static void main(String[] args) {
		Collection<Collection<Measurement>> containers = new ArrayList<Collection<Measurement>>();
		containers.add(new LinkedList<Measurement>());
		containers.add(new ArrayList<Measurement>());
		containers.add(new Vector<Measurement>());
		containers.add(new HashSet<Measurement>());
		containers.add(new TreeSet<Measurement>(Measurement.TIMESTAMP_COMPARATOR));
		containers.add(new LinkedHashSet<Measurement>());
		
		

		for (final Collection<Measurement> collection : containers) {
			System.out.println(collection.getClass());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			
			System.out.println("add");
			test(new Test() {
				@Override
				public void applyTest() {
					for (int i = 0; i < measurements.length; i++) {
						collection.add(measurements[i]);
					}
				}
			});
			
			System.out.println("contains");
			test(new Test() {
				@Override
				public void applyTest() {
					for (Measurement measurement : elementsToFind) {
						collection.contains(measurement);
					}
				}
			});
			
			System.out.println("remove");
			test(new Test() {
				@Override
				public void applyTest() {
					for (Measurement measurement : elementsToRemove) {
						collection.remove(measurement);
					}
				}
			});
		}
		
		System.out.println("finished");
	}

	
	//Implementations
	
	//Algorithms
	//Collections class static metódusai, főként List-eken operálnak, de néhány Collection-ökön

}
