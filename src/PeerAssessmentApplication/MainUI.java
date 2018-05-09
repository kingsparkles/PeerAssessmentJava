package PeerAssessmentApplication;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
//import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.itextpdf.text.DocumentException;

import PeerAssessmentApplication.Models.ModeratedMark;
import PeerAssessmentApplication.Models.RawScore;
import PeerAssessmentApplication.Models.StudentFormInput;


public class MainUI {

	protected Shell shell;
	protected Table tableResults;
	protected boolean excludeSelfAwardedMarks = false;
	public List listErrors;
	private Table tableStudentForms;
	private Table tableRawGrades;
	private java.util.List<StudentFormInput> studentInputs = new ArrayList<>();
	protected HashMap<String, ModeratedMark> map = new HashMap<String, ModeratedMark>();
	public java.util.List<RawScore> rawScores = new ArrayList<>();
	protected java.util.List<PeerAssessmentApplication.Models.Error> errorList = new ArrayList<>();

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
		shell.setSize(1181, 700);
		shell.setText("SWT Application");
		
		Label lblPeerAssessmentApplication = new Label(shell, SWT.NONE);
//		lblPeerAssessmentApplication.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblPeerAssessmentApplication.setText("Peer Assessment Application");
		lblPeerAssessmentApplication.setBounds(26, 10, 252, 36);
		
		//Generate PDF
		Group groupGenPDF = new Group(shell, SWT.NONE);
		groupGenPDF.setBounds(10, 52, 331, 80);
		
		Button btnGeneratePdf = new Button(groupGenPDF, SWT.NONE);
		btnGeneratePdf.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterPath("C:\\");
				String[] pdfExtension = {"*.pdf"};
				dialog.setFilterExtensions(pdfExtension);
				String destination = dialog.open();
				
				if(destination!=null) {
					PdfGenerator pdfGenerator= new PdfGenerator();
					try {
						pdfGenerator.Generate(destination);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnGeneratePdf.setText("Generate PDF Form");
		btnGeneratePdf.setBounds(70, 21, 179, 35);
		
		Group group_Import_Calc_export = new Group(shell, SWT.NONE);
		group_Import_Calc_export.setBounds(10, 168, 331, 136);
		
		Button btnImport = new Button(group_Import_Calc_export, SWT.NONE);
		btnImport.setBounds(33, 60, 105, 35);
		btnImport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//https://stackoverflow.com/questions/12140884/swt-filedialog-selecting-directories-instead-of-files
				DirectoryDialog dialog = new DirectoryDialog(shell);
				dialog.setFilterPath("C:\\");
				String folderLocation = dialog.open();
				if(folderLocation!=null) {
					studentInputs = new ArrayList<>();
					PdfParser parser = new PdfParser();
					studentInputs = parser.parsePDFs(folderLocation);
					
					ValidateStudentInput validator = new ValidateStudentInput();
					errorList = validator.listErrors(studentInputs);
					
					listErrors.removeAll();
					
					for(PeerAssessmentApplication.Models.Error error : errorList) {
						listErrors.add(error.getErrorMessage() + " - located in " + error.getFileName());
					}
				}
			}
		});
		btnImport.setText("Import Forms");
		
		Button btnExport = new Button(group_Import_Calc_export, SWT.NONE);
		btnExport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(tableResults.getItemCount()>0) {
					FileDialog dialog = new FileDialog(shell, SWT.SAVE);
					dialog.setFilterPath("C:\\");
					String[] xlsxExtension = {"*.xlsx"};
					dialog.setFilterExtensions(xlsxExtension);
					String destination = dialog.open();
					
					if(destination!= null) {
						SpreadsheetExporter exporter = new SpreadsheetExporter();
						exporter.Export(map, destination);
					}					
					
				} else {
					MessageBox dialog = new MessageBox(shell, SWT.OK);
					dialog.setText("Error");
					dialog.setMessage("There are no results to export");
					dialog.open();
				}
			}
		});
		btnExport.setBounds(192, 19, 105, 35);
		btnExport.setText("Export Results");
		
		Button btnCalculate = new Button(group_Import_Calc_export, SWT.NONE);
		btnCalculate.setBounds(192, 60, 105, 35);
		btnCalculate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(listErrors.getItemCount()!=0) {
					MessageBox dialog = new MessageBox(shell, SWT.OK);
					dialog.setText("Error");
					dialog.setMessage("There are still some errors that need to be addressed");
					dialog.open();
				} else if(studentInputs.size()== 0) {
					MessageBox dialog = new MessageBox(shell, SWT.OK);
					dialog.setText("Error");
					dialog.setMessage("There are no students imported to calculate");
					dialog.open();
				} else if(tableRawGrades.getItemCount()==0){
					MessageBox dialog = new MessageBox(shell, SWT.OK);
					dialog.setText("Error");
					dialog.setMessage("Please import a spreadsheet with the raw marks first");
					dialog.open();
				}else {
					map = new HashMap<String, ModeratedMark>();
					Calculator calculator = new Calculator();
					map = calculator.mapAndCalculateInput(studentInputs, rawScores, excludeSelfAwardedMarks);
					
					if(rawScores.size()!=map.size()) {
						MessageBox dialog = new MessageBox(shell, SWT.OK);
						dialog.setText("Error");
						dialog.setMessage("Uneven amount of raw student marks and student feedback forms");
						dialog.open();
					}else {
						
						tableResults.removeAll();
						tableStudentForms.removeAll();
						for(Entry<String, ModeratedMark> entry : map.entrySet()) {
							ModeratedMark mark = entry.getValue();
							TableItem resultItem = new TableItem(tableResults, SWT.NULL);
							resultItem.setText(0, entry.getKey());
							resultItem.setText(1, Float.toString(mark.getModeratedGrade()));
							System.out.println(Float.toString(mark.getModeratedGrade()));
							
							TableItem formItem = new TableItem(tableStudentForms, SWT.NULL);
							formItem.setText(0, entry.getKey());
							formItem.setText(1, Float.toString(mark.getAverageMark()));
							formItem.setText(2, Float.toString(mark.getSelfAwarded()));
							java.util.List<Float> peerMarks = mark.getPeerMarks();
							if(peerMarks.size()>0)
							formItem.setText(3, Float.toString(peerMarks.get(0)));
							if(peerMarks.size()>1)
							formItem.setText(4, Float.toString(peerMarks.get(1)));
							if(peerMarks.size()>2)
							formItem.setText(5, Float.toString(peerMarks.get(2)));
						}
					}					
					
				}				
			}
		});
		btnCalculate.setText("Calculate");
		
		Button btnExcludeSelfAwarded = new Button(group_Import_Calc_export, SWT.CHECK);
		btnExcludeSelfAwarded.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				excludeSelfAwardedMarks = !excludeSelfAwardedMarks;
			}
		});
		btnExcludeSelfAwarded.setBounds(152, 101, 169, 25);
		btnExcludeSelfAwarded.setText("exclude self awarded marks");
		
		Button btnImportGrade = new Button(group_Import_Calc_export, SWT.NONE);
		btnImportGrade.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				tableRawGrades.removeAll();
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setFilterPath("C:\\");
				String[] pdfExtension = {"*.xlsx"};
				dialog.setFilterExtensions(pdfExtension);
				String destination = dialog.open();
				
				if(destination!=null) {
					GradeImporter importer = new GradeImporter();
					rawScores= importer.importGrades(destination);
					for(int i = 0; i < rawScores.size(); i++) {
						TableItem item = new TableItem(tableRawGrades, SWT.NULL);
						item.setText(0, rawScores.get(i).getID());
						item.setText(1, Float.toString(rawScores.get(i).getGrade()));
					}
				}
			}
		});
		btnImportGrade.setBounds(33, 19, 105, 35);
		btnImportGrade.setText("Import Grades");
		
		Group grpErrors = new Group(shell, SWT.NONE);
		grpErrors.setText("Errors");
		grpErrors.setBounds(10, 327, 331, 311);
		
		listErrors = new List(grpErrors, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		listErrors.setBounds(10, 25, 311, 276);
		
		Group grpResult = new Group(shell, SWT.NONE);
		grpResult.setText("Moderated Result");
		grpResult.setBounds(851, 10, 302, 628);
		
		tableResults = new Table(grpResult, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tableResults.setBounds(10, 25, 282, 593);
		tableResults.setHeaderVisible(true);
		tableResults.setLinesVisible(true);
		
		TableColumn tblclmnStudentIdModerated = new TableColumn(tableResults, SWT.NONE);
		tblclmnStudentIdModerated.setWidth(146);
		tblclmnStudentIdModerated.setText("Student ID");
		
		TableColumn tblclmnModeratedGrade = new TableColumn(tableResults, SWT.NONE);
		tblclmnModeratedGrade.setWidth(118);
		tblclmnModeratedGrade.setText("Moderated Grade");
		
		Group grpImportResults = new Group(shell, SWT.NONE);
		grpImportResults.setText("Import Results");
		grpImportResults.setBounds(347, 10, 498, 628);
		
		tableStudentForms = new Table(grpImportResults, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tableStudentForms.setBounds(10, 290, 478, 328);
		tableStudentForms.setHeaderVisible(true);
		tableStudentForms.setLinesVisible(true);
		
		TableColumn tblclmnStudentIdForm = new TableColumn(tableStudentForms, SWT.NONE);
		tblclmnStudentIdForm.setWidth(123);
		tblclmnStudentIdForm.setText("Student ID");
		
		TableColumn tblclmnAverage = new TableColumn(tableStudentForms, SWT.NONE);
		tblclmnAverage.setWidth(100);
		tblclmnAverage.setText("Average");
		
		TableColumn tblclmnSelfAwardedMark = new TableColumn(tableStudentForms, SWT.NONE);
		tblclmnSelfAwardedMark.setWidth(66);
		tblclmnSelfAwardedMark.setText("Self Awarded Mark");
		
		TableColumn tblclmnPeer = new TableColumn(tableStudentForms, SWT.NONE);
		tblclmnPeer.setWidth(64);
		tblclmnPeer.setText("Peer 1");
		
		TableColumn tblclmnPeer_1 = new TableColumn(tableStudentForms, SWT.NONE);
		tblclmnPeer_1.setWidth(56);
		tblclmnPeer_1.setText("Peer 2");
		
		TableColumn tblclmnPeer_2 = new TableColumn(tableStudentForms, SWT.NONE);
		tblclmnPeer_2.setWidth(53);
		tblclmnPeer_2.setText("Peer 3");
		
		tableRawGrades = new Table(grpImportResults, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tableRawGrades.setBounds(10, 22, 324, 262);
		tableRawGrades.setHeaderVisible(true);
		tableRawGrades.setLinesVisible(true);
		TableColumn tblclmnStudentIdRaw = new TableColumn(tableRawGrades, SWT.NULL);
		tblclmnStudentIdRaw.setWidth(151);
		tblclmnStudentIdRaw.setText("Student ID");
		TableColumn tblclmnStudentGradeRaw = new TableColumn(tableRawGrades, SWT.NULL);
		tblclmnStudentGradeRaw.setWidth(151);
		tblclmnStudentGradeRaw.setText("Raw Grade");

	}
}
