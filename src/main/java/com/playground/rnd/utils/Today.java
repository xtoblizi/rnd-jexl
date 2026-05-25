package com.playground.rnd.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class Today {

    private final Map<LocalDate, String> holidays;
    private LocalDateTime now = LocalDateTime.now();

    public boolean isPublicHoliday(){
        var todayDate = now.toLocalDate();
        return this.holidays.containsKey(todayDate);
    }

    public LocalDate date(){
        return now.toLocalDate();
    }

    public boolean isWeekend(){
        var dayOfTheWeek = now.getDayOfWeek();
        return dayOfTheWeek.equals(DayOfWeek.SATURDAY) || dayOfTheWeek.equals(DayOfWeek.SUNDAY);
    }
}
