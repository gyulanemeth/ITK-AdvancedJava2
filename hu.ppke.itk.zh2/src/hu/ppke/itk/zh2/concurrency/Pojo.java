package hu.ppke.itk.zh2.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Simple POJO.
 * <b>Immutable:</b><p>
 * Instances of this class appear constant. No external synchronization is necessary.
 * @author akitta
 * 
 */
public class Pojo {

	//TODO implement constructor as described in the javadoc.
	//make this class immutable.
	//consider defensive copies.
	//consider fail-fast object behavior.
	//you may modify the API of the class.
	
	private Date startDate;
	private Date endDate;
	private Collection<?> collection;
	private int[] ints;

	/**
	 * Creates a new POJO instance.
	 * @param startDate the start date. Must be earlier than <b>endDate</b>.
	 * @param endDate the end date. Must be later than <b>startDate</b>. Should *NOT* be {@code null}. 
	 * @param collection the collection. Should *NOT* be {@code null}. Should *NOT* be {@code null}.
	 * @param ints arbitrary number of primitive integer.
	 * <p>
	 * <ul>
	 * <li>This constructor should throw {@link NullPointerException} if any of the specified arguments are {@code null}.</li>
	 * <li>This should throw {@link IllegalArgumentException} if there is a date mismatch.</li>
	 * </ul>
	 */
	public Pojo(final Date startDate, final Date endDate, final Collection<?> collection, final int... ints) {
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the collection
	 */
	public Collection<?> getCollection() {
		return collection;
	}

	/**
	 * @param collection the collection to set
	 */
	public void setCollection(final Collection<?> collection) {
		this.collection = collection;
	}

	/**
	 * @return the ints
	 */
	public int[] getInts() {
		return ints;
	}

	/**
	 * @param ints the ints to set
	 */
	public void setInts(final int[] ints) {
		this.ints = ints;
	}

	//XXX this main method is for testing
	public static void main(final String[] args) {
		
		final List<? super Object> list = new ArrayList<Object>();
		list.add("test string");
		list.add(123);
		list.add(new Object());
		
		new Pojo(new Date(5000L), new Date(10000L), new ArrayList<Object>(), new int[] { 1, 2, 3});
		new Pojo(new Date(10000L), new Date(5000L), list, 1, 2, 3);
		new Pojo(new Date(5000L), new Date(10000L), null, new int[] { /*empty array*/ });
		new Pojo(new Date(5000L), new Date(10000L), list);
		new Pojo(null, new Date(10000L), new ArrayList<Object>(), new int[] { 1, 2, 3});
	}
	
}
