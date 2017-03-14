package com.xxd.TxtToExcel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
/**
 * @author xxd
 *
 */
public class Start {
	public static void main(String[] args) throws IOException {
		String filePath = "";
		
		//获取当前运行类路径
		URL url = Start.class.getProtectionDomain().getCodeSource().getLocation();
        filePath = URLDecoder.decode(url.getPath(), "utf-8");// 转化为utf-8编码  
        if (filePath.endsWith(".jar")) {// 可执行jar包运行的结果里包含".jar"  
            // 截取路径中的jar包名  
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);  
        }  
        
		Scanner scanner=new Scanner(System.in);
		
		System.out.println("请输入文件名（不包含txt后缀）");
		String name = scanner.nextLine();
		scanner.close();
		
		long startDate = System.currentTimeMillis();
		File file = new File(filePath+name+".txt");
		FileInputStream stream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(stream, "GBK");
		BufferedReader reader = new BufferedReader(inputStreamReader);
		
		Workbook wb = new SXSSFWorkbook(1000);
	    FileOutputStream fileOut = new FileOutputStream(filePath+name+".xlsx");
	    
	    String str = null;
		int i = 0;
		Sheet sheet = wb.createSheet();
		System.out.println("导出中...");
		while ((str = reader.readLine()) != null) {
			String[] strArr = str.split("\\|");
		    //创建Excel工作表的行     
		    Row row = sheet.createRow(i);
		    for(int j=0;j<strArr.length;j++){
		    	row.createCell(j).setCellValue(strArr[j]);
		    }
			i++;
		}
		reader.close();
		inputStreamReader.close();
		stream.close();
	    wb.write(fileOut);
	    wb.close();
	    fileOut.close();
	    long endDate = System.currentTimeMillis();
	    System.out.println("转换完成："+i+"行\n耗时："+((endDate-startDate)/1000)+"秒");
	}
}
