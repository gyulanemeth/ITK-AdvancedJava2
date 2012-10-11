package hu.ppke.itk.swt.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Class for demonstrating SWT.
 * @author akitta
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
		
//		final TableViewer viewer = new TableViewer(shell, SWT.NONE);
//		viewer.setContentProvider(ArrayContentProvider.getInstance());
//		viewer.setInput(new String[] { "a", "b", "c" } );
		
		shell.open(); //open shell
		
		while (!shell.isDisposed()) { //read and dispatch while shell is not closed
			
			if (display.readAndDispatch()) { //read event from OS
				display.sleep(); //sleep for a while
			}
			
		}
		
		display.dispose(); //disposes the OS resource
		
	}

}
