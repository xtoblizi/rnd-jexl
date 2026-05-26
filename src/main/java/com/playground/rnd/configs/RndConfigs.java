package com.playground.rnd.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RndConfigs {
    @Bean
    public Map<LocalDate, String> holidays(){
        var vacations = List.of(
                LocalDate.of(2026, 1,30),
                LocalDate.of(2026, 5,25));

        Map<LocalDate, String> holidays = new HashMap<>();
        for (LocalDate holiday: vacations){
            holidays.put(holiday, holiday.toString());
        }

        return holidays;
    }

    @Bean
    public FlagTransactionConfig flagTransactionConfig(){
        return FlagTransactionConfig.getDefault();
    }
}
