package PeerAssessmentApplication;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import PeerAssessmentApplication.Models.ModeratedMark;

public class SpreadsheetExporter {
	public void Export(HashMap<String, ModeratedMark> map, String destination) {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Moderated Grades");
        
        int rowIterator = 0;
        
        for(Entry<String, ModeratedMark> entry : map.entrySet()) {
        	Row row = sheet.createRow(rowIterator++);
        	Cell idCell = row.createCell(0);
        	idCell.setCellValue(entry.getKey());
        	Cell gradeCell = row.createCell(1);
        	gradeCell.setCellValue((double) entry.getValue().getModeratedGrade());
        }
        
        try {
        	FileOutputStream outputStream = new FileOutputStream(destination);
        	workbook.write(outputStream);
        	workbook.close();
        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
}
