package com.cplanet.toring.utils;

import humanize.Humanize;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static java.time.LocalTime.now;

public class DateUtils {

    static public String toStringLocalDateTimeWithFormat(LocalDateTime time, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return Optional.ofNullable(time).map(formatter::format).orElse("");
    }

    static public String toStringYYYYMMDDHHMMSS(LocalDateTime time) {

        return toStringLocalDateTimeWithFormat(time, "yyyyMMddHHmmss");
    }

    /**
     * 경과 기간에 따른 일시 표시
     * ~ 1H : 방금 전
     * ~ 24H : OO시간 전
     * ~ 1Days : YYYY.MM.DD
     * @param time
     * @return
     */
    static public String toHumanizeDateTime(LocalDateTime time) {

        if(Period.between(time.toLocalDate(), LocalDateTime.now().toLocalDate()).getDays() >= 1){
            return time.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        } else if (Duration.between(LocalTime.of(time.getHour(), time.getMinute()), LocalTime.of(now().getHour(), now().getMinute())).toHours() >= 1 ) {
            return Humanize.naturalTime(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            return "방금 전";
        }


    }


}
