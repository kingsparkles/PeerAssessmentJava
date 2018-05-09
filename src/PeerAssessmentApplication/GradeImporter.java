package PeerAssessmentApplication;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import PeerAssessmentApplication.Models.RawScore;

public class GradeImporter {

	public List<RawScore> importGrades(String filePath) {
		//https://www.mkyong.com/java/apache-poi-reading-and-writing-excel-file-in-java/
		try {
			FileInputStream excelFile = new FileInputStream(new File(filePath));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			List<RawScore> rawScores = new ArrayList<>();
			while(rowIterator.hasNext()) {
				Row currentRow = rowIterator.next();
				rawScores.add(new RawScore(currentRow.getCell(0).getStringCellValue(), currentRow.getCell(1).getNumericCellValue()));
			}
			workbook.close();
			return rawScores;
			
		} catch(Exception e) {
			return null;
		}
	}
}
