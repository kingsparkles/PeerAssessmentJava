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
//  public static final String CHARACTOR_FONT_CH_FILE = "SIMFANG.TTF";  //仿宋常规   
    public static final String CHARACTOR_FONT_CH_FILE = "SIMHEI.TTF";  //黑体常规   
       
    public static final Rectangle PAGE_SIZE = PageSize.A4;   
    public static final float MARGIN_LEFT = 50;   
    public static final float MARGIN_RIGHT = 50;   
    public static final float MARGIN_TOP = 50;   
    public static final float MARGIN_BOTTOM = 50;   
    public static final float SPACING = 20;   
    public static DecimalFormat df = new DecimalFormat("0.0");   
       
    private Document document = null;   
       
    /**   
     * @param fileName 存储文件的临时路径  
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
        // 打开文档准备写入内容   
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
     * @param title 章节标题  
     * @param chapterNum 章节序列号  
     * @param alignment 0表示align=left，1表示align=center  
     * @param numberDepth 章节是否带序号 设值=1 表示带序号 1.章节一；1.1小节一...，设值=0表示不带序号  
     * @param font 字体格式  
     * @return Chapter章节  
     */  
    public static Chapter createChapter(String title, int chapterNum, int alignment, int numberDepth, Font font) {   
        Paragraph chapterTitle = new Paragraph(title, font);   
        chapterTitle.setAlignment(alignment);   
        Chapter chapter = new Chapter(chapterTitle, chapterNum);   
        chapter.setNumberDepth(numberDepth);    
        return chapter;   
    }   
       
    /**   
     * @param chapter 指定章节  
     * @param title 小节标题  
     * @param font 字体格式  
     * @param numberDepth 小节是否带序号 设值=1 表示带序号 1.章节一；1.1小节一...，设值=0表示不带序号  
     * @return section在指定章节后追加小节  
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
     * @param text 内容  
     * @param font 内容对应的字体  
     * @return phrase 指定字体格式的内容  
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
     * 最后关闭PDF文档  
     */  
    public void closeDocument() {   
        if(document != null) {   
            document.close();   
        }   
    }   
        
    /**  
     * 读PDF文件，使用了pdfbox开源项目  
     * @param fileName  
     */  
    public String readPDF(String fileName) {   
        File file = new File(fileName);   
        FileInputStream in = null;   
        try {   
            in = new FileInputStream(fileName);   
            // 新建一个PDF解析器对象   
            PDFParser parser = new PDFParser(in);   
            // 对PDF文件进行解析   
            parser.parse();   
            // 获取解析后得到的PDF文档对象   
            PDDocument pdfdocument = parser.getPDDocument();   
            // 新建一个PDF文本剥离器   
            PDFTextStripper stripper = new PDFTextStripper();   
            // 从PDF文档对象中剥离文本   
            String result = stripper.getText(pdfdocument);   
            return result; 
  
        } catch (Exception e) {   
            System.out.println("读取PDF文件" + file.getAbsolutePath() + "生失败！" + e);   
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
				table.addCell(getPDFCell("1、participated in group discussion",fontArial));
				table.addCell(getPDFCell("2、helped keep the group on task",fontArial));
				table.addCell(getPDFCell("3、contributed useful ideas",fontArial));
				table.addCell(getPDFCell("4、how much work was done",fontArial));
				table.addCell(getPDFCell("5、quality of complete  work",fontArial));
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
		//创建Document对象
				
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
					//文档写入内容
					
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
	//合并行的静态函数
	public static PdfPCell mergeRow(String str,Font font,int i) {
		
		//创建单元格对象，将内容及字体传入
		PdfPCell cell=new PdfPCell(new Paragraph(str,font));
		//设置单元格内容居中
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//将该单元格所在列包括该单元格在内的i行单元格合并为一个单元格
		cell.setRowspan(i);
		
		return cell;
	}

	//合并列的静态函数
	public static PdfPCell mergeCol(String str,Font font,int i) {
		
		PdfPCell cell=new PdfPCell(new Paragraph(str,font));
		cell.setMinimumHeight(25);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//将该单元格所在行包括该单元格在内的i列单元格合并为一个单元格
		cell.setColspan(i);
		
		return cell;
	}

	//获取指定内容与字体的单元格
	public static PdfPCell getPDFCell(String string, Font font) 
	{
		//创建单元格对象，将内容与字体放入段落中作为单元格内容
		PdfPCell cell=new PdfPCell(new Paragraph(string,font));
		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//设置最小单元格高度
		cell.setMinimumHeight(25);
		return cell;
	}
    /**  
     * 测试pdf文件的创建  
     * @param args  
     */  
    public static void main(String[] args) {   
  
        String fileName = "C:\\11.pdf";  //  
        UtilPDF pdfUtil = new UtilPDF();  
        System.out.println(pdfUtil.readPDF(fileName));
         
    } 

}
