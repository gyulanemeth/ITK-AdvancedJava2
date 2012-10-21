/**
 * 
 */
package hu.ppke.itk.guava;

import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

/**
 * @author akitta
 *
 */
public class GuavaMain {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		//convert primitive varargs into boxed objects
		final List<Integer> ints = Ints.asList(0, 1, 2, 3, 4, 5, 6, 7, 8 ,9 ,10);
		
		//predicate for filtering evens
		final Predicate<Integer> evenPredicate = new Predicate<Integer>() {
			@Override public boolean apply(final Integer integer) {
				return 0 == integer.intValue() % 2;
			}
		};
		
		//predicate for filtering number modulo 5
		final Predicate<Integer> mod5Predicate = new Predicate<Integer>() {
			@Override public boolean apply(final Integer integer) {
				return 0 == integer.intValue() % 5;
			}
		};
		
		//filter evens
		final Iterable<Integer> evens = Iterables.filter(ints, evenPredicate);
		
		//print evens
		for (final int i : evens) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		//aggregated predicate
		final Iterable<Integer> mod10 = 
				Iterables.filter(ints, Predicates.and(evenPredicate, mod5Predicate));
		
		//initialize set with iterable
		final Set<Integer> mod10Set = Sets.newHashSet(mod10);
		
		//joiner example
		System.out.println(Joiner.on(" | ").join(mod10Set));
		
		//transform integer into string
		final Iterable<String> strings = Iterables.transform(ints, new Function<Integer, String>() {
			@Override public String apply(final Integer integer) {
				return String.valueOf(integer);
			}
		});
		
		//print strings using joiner
		System.out.println(Joiner.on(" * ").join(strings));
		
		//string to split
		final String s = "   1,   ,  , 2, 3 ,4,5,6   ,7 ,8";
		//split on ',' omit empties and trim results
		final Iterable<String> split = Splitter.on(",").omitEmptyStrings().trimResults().split(s);
		
		//print result
		for (final String ss : split) {
			System.out.print(ss + "|");
		}
		
	}

}
