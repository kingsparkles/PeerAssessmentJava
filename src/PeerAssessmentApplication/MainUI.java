package PeerAssessmentApplication;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
//import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class MainUI {

	protected Shell shell;
	private Text text;
	private Text text_4;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_5;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainUI window = new MainUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1214, 700);
		shell.setText("SWT Application");
		
		Label lblPeerAssessmentApplication = new Label(shell, SWT.NONE);
//		lblPeerAssessmentApplication.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblPeerAssessmentApplication.setText("Peer Assessment Application");
		lblPeerAssessmentApplication.setBounds(26, 10, 252, 36);
		
		//Generate PDF
		Group groupGenPDF = new Group(shell, SWT.NONE);
		groupGenPDF.setBounds(10, 52, 457, 80);
		
		Button btnGeneratePdf = new Button(groupGenPDF, SWT.NONE);
		btnGeneratePdf.setText("Generate PDF");
		btnGeneratePdf.setBounds(243, 24, 179, 35);
		
		text = new Text(groupGenPDF, SWT.BORDER);
		text.setBounds(13, 26, 224, 31);
		
		//Criteria weights
		Group groupCriteria_weight = new Group(shell, SWT.NONE);
		groupCriteria_weight.setText("Criteria weight");
		groupCriteria_weight.setBounds(10, 158, 773, 476);
		
		Label label = new Label(groupCriteria_weight, SWT.NONE);
		label.setText("total:");
		label.setBounds(415, 335, 49, 25);
		
		//Criteria 1
		Group group_1 = new Group(groupCriteria_weight, SWT.NONE);
		group_1.setBounds(23, 27, 354, 133);
		
		text_5 = new Text(group_1, SWT.BORDER);
		text_5.setText("1");
		text_5.setBounds(10, 25, 315, 31);
		
		Scale scale_4 = new Scale(group_1, SWT.NONE);
		scale_4.setIncrement(5);
		scale_4.setBounds(10, 69, 240, 54);
		
		Spinner spinner_4 = new Spinner(group_1, SWT.BORDER);
		spinner_4.setSelection(20);
		spinner_4.setBounds(256, 79, 72, 31);
		
		//Criteria 2
		Group group_2 = new Group(groupCriteria_weight, SWT.NONE);
		group_2.setBounds(23, 166, 354, 133);
		
		text_4 = new Text(group_2, SWT.BORDER);
		text_4.setBounds(10, 25, 315, 31);
		text_4.setText("2");
		
		Scale scale_3 = new Scale(group_2, SWT.NONE);
		scale_3.setIncrement(5);
		scale_3.setBounds(10, 69, 240, 54);
		
		Spinner spinner_3 = new Spinner(group_2, SWT.BORDER);
		spinner_3.setSelection(20);
		spinner_3.setBounds(256, 79, 72, 31);
		
		//Criteria 3
		Group group_3 = new Group(groupCriteria_weight, SWT.NONE);
		group_3.setBounds(23, 305, 354, 133);
		
		text_1 = new Text(group_3, SWT.BORDER);
		text_1.setText("3");
		text_1.setBounds(10, 25, 315, 31);
		
		Scale scale = new Scale(group_3, SWT.NONE);
		scale.setIncrement(5);
		scale.setBounds(10, 69, 240, 54);
		
		Spinner spinner = new Spinner(group_3, SWT.BORDER);
		spinner.setSelection(20);
		spinner.setBounds(256, 79, 72, 31);
		
		//Criteria 4
		Group group_4 = new Group(groupCriteria_weight, SWT.NONE);
		group_4.setBounds(394, 27, 354, 133);
		
		text_2 = new Text(group_4, SWT.BORDER);
		text_2.setText("4");
		text_2.setBounds(10, 25, 315, 31);
		
		Scale scale_1 = new Scale(group_4, SWT.NONE);
		scale_1.setIncrement(5);
		scale_1.setBounds(10, 69, 240, 54);
		
		Spinner spinner_1 = new Spinner(group_4, SWT.BORDER);
		spinner_1.setSelection(20);
		spinner_1.setBounds(256, 79, 72, 31);
		
		//Criteria 5
		Group group_5 = new Group(groupCriteria_weight, SWT.NONE);
		group_5.setBounds(394, 166, 354, 133);
		
		text_3 = new Text(group_5, SWT.BORDER);
		text_3.setText("5");
		text_3.setBounds(10, 25, 315, 31);
		
		Scale scale_2 = new Scale(group_5, SWT.NONE);
		scale_2.setIncrement(5);
		scale_2.setBounds(10, 69, 240, 54);
		
		Spinner spinner_2 = new Spinner(group_5, SWT.BORDER);
		spinner_2.setSelection(20);
		spinner_2.setBounds(256, 79, 72, 31);
		
		//Self mark weight
		Group group = new Group(shell, SWT.NONE);
		group.setText("Self mark weight");
		group.setBounds(810, 158, 358, 213);
		
		Button button = new Button(group, SWT.CHECK);
		button.setText("include self awarded marks");
		button.setBounds(21, 52, 249, 25);
		
		Scale scale_6 = new Scale(group, SWT.NONE);
		scale_6.setIncrement(5);
		scale_6.setBounds(10, 118, 260, 54);
		
		Spinner spinner_6 = new Spinner(group, SWT.BORDER);
		spinner_6.setBounds(276, 130, 72, 31);
		
		Group group_Import_Calc_export = new Group(shell, SWT.NONE);
		group_Import_Calc_export.setBounds(810, 390, 358, 136);
		
		Button btnImport = new Button(group_Import_Calc_export, SWT.NONE);
		btnImport.setBounds(10, 54, 105, 35);
		btnImport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnImport.setText("Import");
		
		Button btnExport = new Button(group_Import_Calc_export, SWT.NONE);
		btnExport.setBounds(232, 54, 105, 35);
		btnExport.setText("Export");
		
		Button btnCalculate = new Button(group_Import_Calc_export, SWT.NONE);
		btnCalculate.setBounds(121, 54, 105, 35);
		btnCalculate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnCalculate.setText("Calculate");
		
		Button btnSimpleMode = new Button(shell, SWT.CHECK);
		btnSimpleMode.setBounds(601, 82, 134, 25);
		btnSimpleMode.setText("Simple mode");

	}
}
