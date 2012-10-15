package hu.ppke.itk.swt.demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * Class for demonstrating SWT.
 * @author akitta <akitta@b2international.com>
 * @see http://www.eclipse.org/swt/
 * @see http://www.eclipse.org/articles/article.php?file=Article-Understanding-Layouts/index.html
 * @see http://wiki.eclipse.org/SWT_Widget_Style_Bits
 */
public class SwtMain {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		final Display display = new Display(); //create a display instance
		
		final Shell shell = new Shell(display); //create shell
		shell.setLayout(new GridLayout(4, true)); //add layout for the composite
		shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true)); //use maximum available size
		
		final TableViewer viewer = new TableViewer(shell, SWT.NONE | SWT.VIRTUAL | SWT.FULL_SELECTION);
//		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setContentProvider(new LazyTableContentProvider());
		viewer.setLabelProvider(new LabelProvider());
		
		final Set<Object> input =  new HashSet<Object>();
		
		for (int i = 0; i < 2000; i++) {
			input.add(i % 2 == 0 ? new Pojo(i) : new Pojo(String.valueOf(i)));
		}

		viewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setInput(input);
		
		
		final Map<Gender, Set<Person>> persons = new HashMap<SwtMain.Gender, Set<Person>>();
		persons.put(Gender.MALE, new HashSet<SwtMain.Person>());
		persons.put(Gender.FEMALE, new HashSet<SwtMain.Person>());
		
		for (int i = 0; i < 2000; i++) {
			final Gender gender = i % 2 == 0 ? Gender.MALE : Gender.FEMALE; 
			persons.get(gender).add(new Person("First name #" + i, "Last name #" + i, gender));
		}
		
		final TreeViewer treeViewer = new TreeViewer(shell);
		treeViewer.setLabelProvider(new LabelProvider());
		treeViewer.setContentProvider(new PersonTreeContentProvider(persons));
		treeViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		treeViewer.setInput(persons);
		
		createTable(shell);
		createTableViewer(shell);
		
		shell.open(); //open shell
		
		while (!shell.isDisposed()) { //read and dispatch while shell is not closed
			
			if (display.readAndDispatch()) { //read event from OS
				display.sleep(); //sleep for a while
			}
			
		}
		
		display.dispose(); //disposes the OS resource
		
	}
	
	private static void createTable(final Composite parent) {
		
		if (true)
			return;
		
		final Table table = new Table(parent, SWT.FULL_SELECTION | SWT.BORDER);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		final String[] columnHeaders = { "a", "b", "c" };
		for (final String columnHeader : columnHeaders) {
			new TableColumn(table, SWT.NONE).setText(columnHeader);
		}
		for (int i = 0; i < 100; i++) {
			final TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, "A " + i);
			item.setText(1, "B " + i);
			item.setText(2, "C " + i);
		}
		for (int i = 0; i < columnHeaders.length; i++) {
			table.getColumn(i).pack();
		}
	}
	
	private static void createTableViewer(final Composite parent) {
		
		if (true)
			return;
		
		final TableViewer viewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.BORDER);
		final TableColumnLayout layout = new TableColumnLayout();
		
		final String[] columnHeaders = { "a", "b", "c" };
		for (final String columnHeader : columnHeaders) {
			final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
			final TableColumn column = viewerColumn.getColumn();
			column.setText(columnHeader);
			column.setResizable(true);
			column.setMoveable(true);
			layout.setColumnData(column, new ColumnWeightData(1));
			viewer.getControl().getParent().setLayout(layout);
		}
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setLabelProvider(new LabelProvider() {
			
			@Override
			public String getColumnText(final Object element, final int columnIndex) {
				return getText(element);
			}
			
			@Override
			public Image getColumnImage(final Object element, final int columnIndex) {
				return null;
			}
		});
		viewer.setInput(new String[] { "a1", "a2", "a3", "b1", "b2", "b3" } );
	}
	
	static class LabelProvider extends ColumnLabelProvider implements ITableLabelProvider {
		@Override public String getText(final Object element) {
			if (element instanceof Pojo) {
				return String.valueOf(((Pojo) element).object);
			} else if (element instanceof Person) {
				final StringBuilder sb = new StringBuilder();
				sb.append(((Person) element).firstName);
				sb.append(" ");
				sb.append(((Person) element).lastName);
				sb.append(" [");
				sb.append(((Person) element).gender);
				sb.append("]");
				return sb.toString();
			} else if (element instanceof Gender) {
				return String.valueOf(element);
			}
			return null;
		}		
		@Override public String getColumnText(final Object element, final int columnIndex) {
			return 0 == columnIndex ? getText(element) : null;
		}
		@Override public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}
		
	}
	
	private static final class Pojo {
		private final Object object;
		private Pojo(final Object object) {
			this.object = object;
		}
	}
	
	static class PersonTreeContentProvider implements ITreeContentProvider {

		private Map<Gender, Set<Person>> input;

		PersonTreeContentProvider(final Map<Gender, Set<Person>> input) {
			this.input = input;
		} 
		
		@Override
		public void dispose() {
			if (null != input) {
				input.clear();
				input = null;
			}
		}

		@Override
		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		}

		@Override
		public Object[] getElements(final Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof Gender) {
				return Gender.MALE.equals(parentElement) ? input.get(Gender.MALE).toArray() : input.get(Gender.FEMALE).toArray();
			} else if (parentElement instanceof Map<?, ?>) {
				final Set<?> keySet = ((Map<?, ?>) parentElement).keySet();
				if (null != keySet && keySet.size() > 0) {
					if (keySet.toArray()[0] instanceof Gender) {
						return Gender.values();
					}
 				}
			} else {
				System.out.println(parentElement);
			}
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {
			if (element instanceof Person) {
				return Gender.MALE.equals(((Person) element).gender) ? Gender.MALE : Gender.FEMALE;
			} else {
				System.out.println(element);
			}
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			return element instanceof Gender;
		}
		
	}
	
	private static enum Gender {
		MALE,
		FEMALE;
	}
	
	private static final class Person {
		private final String firstName;
		private final String lastName;
		private final Gender gender;
		private Person(final String firstName, final String lastName, final Gender gender) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.gender = gender;
		}
	} 

}
