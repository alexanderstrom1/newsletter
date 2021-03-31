package com.example.labb.demo.controllers;

import com.example.labb.demo.repostiories.SubscriberRowMapper;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcellController {
    @GetMapping("/saveuser")
        public String saveuser(){
            FileInputStream fstream;
            XSSFWorkbook workbook;
            List<SubscriberRowMapper> mySubscriber =new ArrayList<>();
            try {
                fstream = new FileInputStream(new File("newsletter.xlsx"));
                workbook = new XSSFWorkbook(fstream);
                XSSFSheet worksheet = workbook.getSheetAt(0);
                XSSFRow newRow=worksheet.createRow(worksheet.getLastRowNum()+1);
                XSSFCell id=newRow.createCell(0);
                XSSFCell name=newRow.createCell(1);
                XSSFCell email=newRow.createCell(2);
                id.setCellValue(1);
                name.setCellValue("Erik");
                email.setCellValue("h19axsta@du.se");
                fstream.close();
                FileInputStream foStream = new FileInputStream(new File("newsletter.xlsx"));
                workbook.write(foStream);
                foStream.close();






            }//Edn try
        catch (FileNotFoundException fnfe){
                fnfe.fillInStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
            }

    }


}//END CLASS

