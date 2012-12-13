package hu.ppke.itk.zh2.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Test class to modify SWT UI asynchronously.
 * @author akitta
 * @see SWT 3.7.2 binary is available at: http://download.eclipse.org/eclipse/downloads/drops/R-3.7.2-201202080800/index.php#SWT
 */
public class AsyncSwtUpdate {

	//TODO
	//asynchronously modify the label on the UI after pushing the button.
	//after pushing the button on the UI, the text of the label should be replaced/updated with a 3000 millisecond delay.
	//UI should *NOT* freeze for the process. implement it in asynchronous way.

	//XXX platform specifc SWT lib is available at:
	//SWT 3.7.2 binary is available at: http://download.eclipse.org/eclipse/downloads/drops/R-3.7.2-201202080800/index.php#SWT
	
	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		final Display display = new Display();
		final Shell shell = new Shell(display);
		
		shell.setLayout(new GridLayout(1, false));
		shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		final Label label = new Label(shell, SWT.NONE);
		label.setText("Modify me asynchronously!");
		
		final Button button = new Button(shell, SWT.TOGGLE);
		button.setText("Update UI");

		shell.open();
		
		while (!shell.isDisposed()) { //read and dispatch while shell is not closed
			
			if (display.readAndDispatch()) { //read event from OS
				display.sleep(); //sleep for a while
			}
			
		}
		
		display.dispose(); //disposes the OS resource
	}

}
