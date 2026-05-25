package com.playground.rnd.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TodayTest {

    private Today buildToday(LocalDateTime now, Map<LocalDate, String> holidays) {
        Today today = new Today(holidays);
        today.setNow(now);
        return today;
    }

    @Test
    void isWeekend_onSaturday_returnsTrue() {
        Today today = buildToday(LocalDateTime.of(2026, 5, 23, 10, 0), new HashMap<>());
        assertThat(today.isWeekend()).isTrue();
    }

    @Test
    void isWeekend_onSunday_returnsTrue() {
        Today today = buildToday(LocalDateTime.of(2026, 5, 24, 10, 0), new HashMap<>());
        assertThat(today.isWeekend()).isTrue();
    }

    @Test
    void isWeekend_onWeekday_returnsFalse() {
        Today today = buildToday(LocalDateTime.of(2026, 5, 25, 10, 0), new HashMap<>());
        assertThat(today.isWeekend()).isFalse();
    }

    @Test
    void isPublicHoliday_whenDateIsHoliday_returnsTrue() {
        LocalDate holiday = LocalDate.of(2026, 5, 25);
        Today today = buildToday(holiday.atTime(9, 0), Map.of(holiday, holiday.toString()));
        assertThat(today.isPublicHoliday()).isTrue();
    }

    @Test
    void isPublicHoliday_whenDateIsNotHoliday_returnsFalse() {
        Map<LocalDate, String> holidays = Map.of(LocalDate.of(2026, 1, 30), "holiday");
        Today today = buildToday(LocalDateTime.of(2026, 5, 25, 9, 0), holidays);
        assertThat(today.isPublicHoliday()).isFalse();
    }

    @Test
    void isPublicHoliday_emptyHolidayMap_returnsFalse() {
        Today today = buildToday(LocalDateTime.of(2026, 5, 25, 9, 0), new HashMap<>());
        assertThat(today.isPublicHoliday()).isFalse();
    }

    @Test
    void date_returnsLocalDatePartOfNow() {
        LocalDateTime now = LocalDateTime.of(2026, 5, 24, 15, 30, 45);
        Today today = buildToday(now, new HashMap<>());
        assertThat(today.date()).isEqualTo(LocalDate.of(2026, 5, 24));
    }
}
