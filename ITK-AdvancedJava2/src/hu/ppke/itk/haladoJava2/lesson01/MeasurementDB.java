package hu.ppke.itk.haladoJava2.lesson01;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

public class MeasurementDB {
	public static <T extends Collection<Measurement>> void readFromCsv(String path, T container) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));

			String line = br.readLine(); // skipping the header line
			while ((line = br.readLine()) != null) {
				Measurement measurement = new Measurement(line);
				container.add(measurement);
			}

			br.close();
		} catch (IOException e) {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e1) {
					// intentionally not handled. otherwise previous exception
					// can be swallowed
				}
			}
		}
	}
	
	public static <T extends Collection<Measurement>> void serializeDB(T container, String fileName) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeInt(container.size());
		Object[] array = container.toArray();
		for (int i = 0; i < container.size(); i++) {
			out.writeObject(array[i]);
		}
        out.flush();
        out.close();
	}
	
	public static Measurement[] deserializeDB (String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		Measurement[] measurementArray = new Measurement[in.readInt()];
		for (int i = 0; i < measurementArray.length; i++) {
			measurementArray[i] = (Measurement) in.readObject();
		}
        in.close();
        return measurementArray;
	}
	
	public static <T extends Collection<Measurement>> void deserializeDB (String fileName, T container) throws FileNotFoundException, IOException, ClassNotFoundException {
		Measurement[] measurementArray = deserializeDB (fileName);
        
        for (Measurement act : measurementArray) {
        	container.add(act);
        }
	}
}
