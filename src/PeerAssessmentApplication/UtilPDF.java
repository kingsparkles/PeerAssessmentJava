package PeerAssessmentApplication;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class UtilPDF {
//  public static final String CHARACTOR_FONT_CH_FILE = "SIMFANG.TTF";  //���γ���   
    public static final String CHARACTOR_FONT_CH_FILE = "SIMHEI.TTF";  //���峣��   
       
    public static final Rectangle PAGE_SIZE = PageSize.A4;   
    public static final float MARGIN_LEFT = 50;   
    public static final float MARGIN_RIGHT = 50;   
    public static final float MARGIN_TOP = 50;   
    public static final float MARGIN_BOTTOM = 50;   
    public static final float SPACING = 20;   
    public static DecimalFormat df = new DecimalFormat("0.0");   
       
    private Document document = null;   
       
    /**   
     * @param fileName �洢�ļ�����ʱ·��  
     * @return   
     */  
    public void createDocument(String fileName) {   
        File file = new File(fileName);   
        FileOutputStream out = null;   
        document = new Document(PAGE_SIZE, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM);   
        try {   
            out = new FileOutputStream(file);   
//          PdfWriter writer =    
            PdfWriter.getInstance(document, out);   
        } catch (FileNotFoundException e) {   
            e.printStackTrace();   
        } catch (DocumentException e) {   
            e.printStackTrace();   
        }   
        // ���ĵ�׼��д������   
        document.open();   
    }   
       
    /**   
     * @param chapter  
     * @return   
     */  
    public void writeChapterToDoc(Chapter chapter) {   
        try {   
            if(document != null) {   
                if(!document.isOpen()) document.open();   
                document.add(chapter);   
            }   
        } catch (DocumentException e) {   
            e.printStackTrace();   
        }   
    }   
       
    /**   
     * @param title �½ڱ���  
     * @param chapterNum �½����к�  
     * @param alignment 0��ʾalign=left��1��ʾalign=center  
     * @param numberDepth �½��Ƿ����� ��ֵ=1 ��ʾ����� 1.�½�һ��1.1С��һ...����ֵ=0��ʾ�������  
     * @param font �����ʽ  
     * @return Chapter�½�  
     */  
    public static Chapter createChapter(String title, int chapterNum, int alignment, int numberDepth, Font font) {   
        Paragraph chapterTitle = new Paragraph(title, font);   
        chapterTitle.setAlignment(alignment);   
        Chapter chapter = new Chapter(chapterTitle, chapterNum);   
        chapter.setNumberDepth(numberDepth);    
        return chapter;   
    }   
       
    /**   
     * @param chapter ָ���½�  
     * @param title С�ڱ���  
     * @param font �����ʽ  
     * @param numberDepth С���Ƿ����� ��ֵ=1 ��ʾ����� 1.�½�һ��1.1С��һ...����ֵ=0��ʾ�������  
     * @return section��ָ���½ں�׷��С��  
     */  
    public static Section createSection(Chapter chapter, String title, Font font, int numberDepth) {   
        Section section = null;   
        if(chapter != null) {   
            Paragraph sectionTitle = new Paragraph(title, font);   
            sectionTitle.setSpacingBefore(SPACING);   
            section = chapter.addSection(sectionTitle);   
            section.setNumberDepth(numberDepth);   
        }   
        return section;   
    }   
       
    /**   
     * @param text ����  
     * @param font ���ݶ�Ӧ������  
     * @return phrase ָ�������ʽ������  
     */  
    public static Phrase createPhrase(String text,Font font) {   
        Phrase phrase = new Paragraph(text,font);   
        return phrase;   
    }   
       
    /**  
     * @param numbered    
     * @param lettered  
     * @param symbolIndent  
     * @return list  
     */  
    public static List createList(boolean numbered, boolean lettered, float symbolIndent) {   
        List list = new List(numbered, lettered, symbolIndent);   
        return list;   
    }   
       
    /**   
     * @param content 
     * @param font  
     * @return listItem  
     */  
    public static ListItem createListItem(String content, Font font) {   
        ListItem listItem = new ListItem(content, font);   
        return listItem;   
    }   
  
    /**  
     * @param fontname   
     * @param size  
     * @param style   
     * @param color   
     * @return Font  
     */  
    public static Font createFont(String fontname, float size, int style, BaseColor color) {   
        Font font =  FontFactory.getFont(fontname, size, style, color);   
        return font;   
    }   
       
    /**  
     * ���ر�PDF�ĵ�  
     */  
    public void closeDocument() {   
        if(document != null) {   
            document.close();   
        }   
    }   
        
    /**  
     * ��PDF�ļ���ʹ����pdfbox��Դ��Ŀ  
     * @param fileName  
     */  
    public String readPDF(String fileName) {   
        File file = new File(fileName);   
        FileInputStream in = null;   
        try {   
            in = new FileInputStream(fileName);   
            // �½�һ��PDF����������   
            PDFParser parser = new PDFParser(in);   
            // ��PDF�ļ����н���   
            parser.parse();   
            // ��ȡ������õ���PDF�ĵ�����   
            PDDocument pdfdocument = parser.getPDDocument();   
            // �½�һ��PDF�ı�������   
            PDFTextStripper stripper = new PDFTextStripper();   
            // ��PDF�ĵ������а����ı�   
            String result = stripper.getText(pdfdocument);   
            return result; 
  
        } catch (Exception e) {   
            System.out.println("��ȡPDF�ļ�" + file.getAbsolutePath() + "��ʧ�ܣ�" + e);   
            e.printStackTrace();   
        } finally {   
            if (in != null) {   
                try {   
                    in.close();   
                } catch (IOException e1) {   
                }   
            }   
        }
		return "";   
    }   
    public static void createPdf(String filename,Integer num) throws Exception{
		
				Document doc=new Document(PageSize.A4.rotate(),0,0,50,0);
				PdfWriter.getInstance(doc, new FileOutputStream(filename));
				BaseFont bf=BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, false);
				Font fontArial=new Font(bf,13,Font.NORMAL);
				Font fontArialBold=new Font(bf,15,Font.BOLD);
		//first table		
				PdfPTable tablestudent=new PdfPTable(2);//no. off columns is 2
				tablestudent.setTotalWidth(new float[]{100,320});
				
				tablestudent.addCell(getPDFCell("Student ID:",fontArialBold));
				tablestudent.addCell(getPDFCell("",fontArialBold));
				tablestudent.addCell(getPDFCell("Student Name:",fontArialBold));
				tablestudent.addCell(getPDFCell("",fontArialBold));
		//second table		
			/**	PdfPTable table=new PdfPTable(7); //no. off columns is 7
				table.setTotalWidth(new float[]{80,80,80,80,80,80,80});
				
				table.addCell(getPDFCell("Student ID:",fontArial));
				table.addCell(getPDFCell("Student name:",fontArial));
				table.addCell(getPDFCell("1��participated in group discussion",fontArial));
				table.addCell(getPDFCell("2��helped keep the group on task",fontArial));
				table.addCell(getPDFCell("3��contributed useful ideas",fontArial));
				table.addCell(getPDFCell("4��how much work was done",fontArial));
				table.addCell(getPDFCell("5��quality of complete  work",fontArial));
				table.addCell(getPDFCell("Comments",fontArial));
			*/	
				PdfPTable table=new PdfPTable(4); //no. off columns is 4
				table.setTotalWidth(new float[]{100,100,100,100});
				
				table.addCell(getPDFCell("Student ID:",fontArial));
				table.addCell(getPDFCell("Student name:",fontArial));
				table.addCell(getPDFCell("coin allocation",fontArial));
				table.addCell(getPDFCell("Comments",fontArial));
				
				for (int i = 0; i < num; i++) {
					table.addCell(getPDFCell("",fontArial));table.addCell(getPDFCell("",fontArial));
					table.addCell(getPDFCell("",fontArial));table.addCell(getPDFCell("",fontArial));
					table.addCell(getPDFCell("",fontArial));table.addCell(getPDFCell("",fontArial));
					table.addCell(getPDFCell("",fontArial));
				}
				table.addCell(getPDFCell("notes",fontArial));
				table.addCell(mergeCol("must be over three words in comments", fontArial, 6));
				table.setLockedWidth(true);
				
				//pdf form title
				doc.open();
				Paragraph title=new Paragraph("Peer Assessment Form\n\n",fontArialBold);
				title.setAlignment(Paragraph.ALIGN_CENTER);
				doc.add(title);
				doc.add(tablestudent);
				doc.add(new Paragraph("\n\n",fontArialBold));
				doc.add(table);
				doc.close();
				
				
						
	}
    /**
    public static void createResultPdf(String filepath,
    		Map<String, ScoreStudent> rmap) throws Exception{
		//����Document����
				
				int i=1;
				for(String sc:rmap.keySet()){
					Document doc=new Document(PageSize.A4,0,0,50,0);
					PdfWriter.getInstance(doc, new FileOutputStream(filepath+"\\"+i+".pdf"));
					BaseFont bf=BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, false); //simkai
					Font font=new Font(bf,13,Font.NORMAL); //Font font=new Font(bf,13,Font.NORMAL);
					Font font1=new Font(bf,15,Font.BOLD);  //Font font1=new Font(bf,15,Font.BOLD);
					PdfPTable table=new PdfPTable(3);
					table.setTotalWidth(new float[]{100,100,100});
					
					table.addCell(getPDFCell("Student ID",font));
					table.addCell(getPDFCell("Score",font));
					table.addCell(getPDFCell("Comments",font));
					table.addCell(getPDFCell(sc,font));
					table.addCell(getPDFCell(df.format(rmap.get(sc).getScore()),font));
					table.addCell(getPDFCell(rmap.get(sc).getComments(),font));
					//�ĵ�д������
					
					doc.open();
					Paragraph title=new Paragraph("score\n\n",font1);
					title.setAlignment(Paragraph.ALIGN_CENTER);
					doc.add(title);
					doc.add(new Paragraph("\n\n",font1));
					doc.add(table);
					doc.close();
					i++;
				}
				
				
	}
    */
	//�ϲ��еľ�̬����
	public static PdfPCell mergeRow(String str,Font font,int i) {
		
		//������Ԫ����󣬽����ݼ����崫��
		PdfPCell cell=new PdfPCell(new Paragraph(str,font));
		//���õ�Ԫ�����ݾ���
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//���õ�Ԫ�������а����õ�Ԫ�����ڵ�i�е�Ԫ��ϲ�Ϊһ����Ԫ��
		cell.setRowspan(i);
		
		return cell;
	}

	//�ϲ��еľ�̬����
	public static PdfPCell mergeCol(String str,Font font,int i) {
		
		PdfPCell cell=new PdfPCell(new Paragraph(str,font));
		cell.setMinimumHeight(25);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//���õ�Ԫ�������а����õ�Ԫ�����ڵ�i�е�Ԫ��ϲ�Ϊһ����Ԫ��
		cell.setColspan(i);
		
		return cell;
	}

	//��ȡָ������������ĵ�Ԫ��
	public static PdfPCell getPDFCell(String string, Font font) 
	{
		//������Ԫ����󣬽���������������������Ϊ��Ԫ������
		PdfPCell cell=new PdfPCell(new Paragraph(string,font));
		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//������С��Ԫ��߶�
		cell.setMinimumHeight(25);
		return cell;
	}
    /**  
     * ����pdf�ļ��Ĵ���  
     * @param args  
     */  
    public static void main(String[] args) {   
  
        String fileName = "C:\\11.pdf";  //  
        UtilPDF pdfUtil = new UtilPDF();  
        System.out.println(pdfUtil.readPDF(fileName));
         
    } 

}
