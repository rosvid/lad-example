package com.ladexample.app.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LadUtil{
   public static List<String> getJsonListFromFile(File jsonFile) throws FileNotFoundException{
      List<String> jsonList = new ArrayList<>();

      StringBuilder jsonBuilder = new StringBuilder();
      Scanner scanner = new Scanner(jsonFile);
      while(scanner.hasNextLine()){
         String line = scanner.nextLine();
         if(line.startsWith("{")){
            jsonBuilder = new StringBuilder();
            jsonBuilder.append(line);
         }
         else if(line.startsWith("}")){
            jsonBuilder.append(line);
            jsonList.add(jsonBuilder.toString());
         }
         else{
            line = line.replace("\\n", "");
            jsonBuilder.append(StringUtils.trim(line));
         }
      }
      scanner.close();

      return jsonList;
   }

   public static LocalDate convertDateToLocalDate(Date date){
      return Instant.ofEpochMilli(date.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
   }
}