package hu.ppke.itk.haladoJava2.lesson01;

import java.io.Serializable;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Measurement implements Serializable {
	private static final long serialVersionUID = 1L;

	// MID NODE SEQUENCE VOLT TEMP LIGHT MAGX MAGY ACCELX ACCELY NOISE?
	// TIMESTAMP
	private final byte mid; // nem tudjuk mi.
	private final byte node;
	private final int sequence; // csomagsorrend visszaállításához, kicsi az
								// esély, hogy a sorrend elromoljon
	private final double volt;
	private final double temp;
	private final double light;
	private final double magX; // mágneses érték
	private final double magY;
	private final double accelX;
	private final double accelY;
	private final double noise;
	private final long timestamp; // elvileg nincs ket azonos timestamp

	public static final Comparator<Measurement> TIMESTAMP_COMPARATOR = new Comparator<Measurement>() {
		@Override
		public int compare(final Measurement o1, final Measurement o2) {
			return (int) (o1.timestamp - o2.timestamp);
		}
	};
	

	public Measurement(final byte mid, final byte node, final int sequence, final double volt,
			final double temp, final double light, final double magX, final double magY, final double accelX,
			final double accelY, final double noise, final long timestamp) {

		this.mid = mid;
		this.node = node;
		this.sequence = sequence;
		this.volt = volt;
		this.temp = temp;
		this.light = light;
		this.magX = magX;
		this.magY = magY;
		this.accelX = accelX;
		this.accelY = accelY;
		this.noise = noise;
		this.timestamp = timestamp;
	}

	public Measurement(final String csvLine) {
		final StringTokenizer st = new StringTokenizer(csvLine, ";");

		String temp = st.nextToken();
		this.mid = Byte.parseByte(temp);
		temp = st.nextToken();
		this.node = Byte.parseByte(temp);
		temp = st.nextToken();
		this.sequence = Integer.parseInt(temp);
		temp = st.nextToken();
		this.volt = Double.parseDouble(temp); // numberformat exceptionnel mit
												// kezdjünk?
		temp = st.nextToken();
		this.temp = Double.parseDouble(temp);
		temp = st.nextToken();
		this.light = Double.parseDouble(temp);
		temp = st.nextToken();
		this.magX = Double.parseDouble(temp);
		temp = st.nextToken();
		this.magY = Double.parseDouble(temp);
		temp = st.nextToken();
		this.accelX = Double.parseDouble(temp);
		temp = st.nextToken();
		this.accelY = Double.parseDouble(temp);
		temp = st.nextToken();
		this.noise = Double.parseDouble(temp);
		temp = st.nextToken();
		this.timestamp = Long.parseLong(temp);
	}

	public int getMid() {
		return mid;
	}

	public int getNode() {
		return node;
	}

	public int getSequence() {
		return sequence;
	}

	public double getVolt() {
		return volt;
	}

	public double getTemp() {
		return temp;
	}

	public double getLight() {
		return light;
	}

	public double getMagX() {
		return magX;
	}

	public double getMagY() {
		return magY;
	}

	public double getAccelX() {
		return accelX;
	}

	public double getAccelY() {
		return accelY;
	}

	public double getNoise() {
		return noise;
	}

	public long getTimestamp() {
		return timestamp;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + node;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Measurement other = (Measurement) obj;
		if (node != other.node)
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}

}
