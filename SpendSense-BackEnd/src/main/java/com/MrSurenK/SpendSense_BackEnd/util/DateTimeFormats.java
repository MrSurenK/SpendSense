package com.MrSurenK.SpendSense_BackEnd.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateTimeFormats {

    //To format date String with this format only: dd-MM-yyyy
    public static LocalDate formatStringDate(String date){
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateTime = null;
        try{
            dateTime = LocalDate.parse(date,pattern);
        } catch(DateTimeParseException e){
            log.error("e :" , e );
        }
        return dateTime;
    }
}
