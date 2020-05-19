package com.cn.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateTimeDemo {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        //自定义格式化
        DateTimeFormatter dateTimeFormatter =   DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String s3 = localDate.format(dateTimeFormatter);
        System.out.println(s3);
        System.out.println(LocalTime.now());
        java.time.LocalDateTime localDateTime = java.time.LocalDateTime.now();
        dateTimeFormatter =   DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime.format(dateTimeFormatter));



        LocalDateTime start = LocalDateTime.of(2018, 7, 24, 0, 0);
        LocalDateTime end = LocalDateTime.of(2018, 7, 24, 23, 59);

        Duration duration = Duration.between(start,end);
        System.out.println(duration.toMinutes());

        //Date转LocalDateTime
        Instant instant =new Date().toInstant();
        ZoneId zoneId =ZoneId.systemDefault();
        LocalDateTime dateTime =LocalDateTime.ofInstant(instant,zoneId);
    }
}
