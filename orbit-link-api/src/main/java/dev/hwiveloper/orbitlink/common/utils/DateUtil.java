package dev.hwiveloper.orbitlink.common.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {
    public static String getTodayString() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return today.format(formatter);
    }

    public static boolean isValidYearMonth(String yearMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            LocalDate.parse(yearMonth + "01", formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidBaseDay(String baseDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            LocalDate.parse(baseDay, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String getStartDateByYearMonth(String yearMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        YearMonth ym = YearMonth.parse(yearMonth, formatter);
        DateTimeFormatter ymdFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return ym.atDay(1).format(ymdFormatter);
    }

    public static String getEndDateByYearMonth(String yearMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        YearMonth ym = YearMonth.parse(yearMonth, formatter);
        DateTimeFormatter ymdFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return ym.atEndOfMonth().format(ymdFormatter);
    }

    public static String[] calculateStartAndEndDate(String startDayStr, String yearMonthStr) {
        int startDay = Integer.parseInt(startDayStr);
        YearMonth currentYm = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yyyyMM"));

        // 유효한 시작일 보정
        int actualStartDay = Math.min(startDay, currentYm.lengthOfMonth());
        LocalDate startDate = currentYm.atDay(actualStartDay);

        // 다음 기간의 startDate 계산
        YearMonth nextYm = currentYm.plusMonths(1);
        int actualNextStartDay = Math.min(startDay, nextYm.lengthOfMonth());
        LocalDate nextStartDate = nextYm.atDay(actualNextStartDay);

        // 현재 구간의 endDate = 다음 startDate - 1일
        LocalDate endDate = nextStartDate.minusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return new String[]{
                startDate.format(formatter),
                endDate.format(formatter)
        };
    }

    public static String addMonth(String yearMonth, int month) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        YearMonth ym = YearMonth.parse(yearMonth, formatter);
        YearMonth addedYm = ym.plusMonths(month);
        return addedYm.format(formatter);
    }

    public static String minusMonth(String yearMonth, int month) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        YearMonth ym = YearMonth.parse(yearMonth, formatter);
        YearMonth addedYm = ym.minusMonths(month);
        return addedYm.format(formatter);
    }

    public static String addMonthWithStartDate(String yearMonth, int month) {
        return getStartDateByYearMonth(addMonth(yearMonth, month));
    }

    public static String minusMonthWithStartDate(String yearMonth, int month) {
        return getStartDateByYearMonth(minusMonth(yearMonth, month));
    }

    public static String addMonthWithEndDate(String yearMonth, int month) {
        return getEndDateByYearMonth(addMonth(yearMonth, month));
    }

    public static String minusMonthWithEndDate(String yearMonth, int month) {
        return getEndDateByYearMonth(minusMonth(yearMonth, month));
    }

    public static int getDayOfWeek(String ymd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(ymd, formatter);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // DayOfWeek는 MONDAY=1 ~ SUNDAY=7 이므로 순환해서 SUNDAY=1로 맞춤
        return dayOfWeek.getValue();
    }

    // 일(day)만 추출
    public static int getDayOfMonth(String ymd) {
        LocalDate date = LocalDate.parse(ymd, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return date.getDayOfMonth();
    }

    // 다음 달 동일 일자 (없으면 그 달의 마지막 날로 보정)
    public static String getNextMonthSameOrLastDay(String ymd) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(ymd, dtf);
        int day = date.getDayOfMonth();
        LocalDate nextMonth = date.plusMonths(1).withDayOfMonth(1); // 다음 달 1일
        int lastDayOfNextMonth = nextMonth.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

        int adjustedDay = Math.min(day, lastDayOfNextMonth);
        LocalDate result = nextMonth.withDayOfMonth(adjustedDay);
        return result.format(dtf);
    }

    public static String getYearMonth(String ymd) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(ymd, dtf);
        return date.format(DateTimeFormatter.ofPattern("yyyyMM"));
    }

    public static String getYearMonthAddMonths(String ymd, Integer a) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(ymd, dtf);
        LocalDate adjustedDate = date.plusMonths(a);
        return adjustedDate.format(DateTimeFormatter.ofPattern("yyyyMM"));
    }

    public static String getDayAddMonths(String ymd, Integer a) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(ymd, dtf);
        LocalDate adjustedDate = date.plusMonths(a);
        return adjustedDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static void main(String[] args) {
        System.out.println(isValidYearMonth("202410"));
        System.out.println(isValidYearMonth("202413"));
        System.out.println(isValidYearMonth("20241a"));
    }

    public static List<String> getAllDaysBetween(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        return start.datesUntil(end.plusDays(1))
                .map(date -> date.format(formatter))
                .toList();
    }

    public static String getNowYearMonth() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return now.format(formatter);
    }

    public static List<String> getNext12Months(String baseYearMonth) {
        List<String> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        // yyyyMM → YearMonth 변환
        YearMonth start = YearMonth.parse(baseYearMonth, formatter);

        // 현재 월부터 12개월 뒤까지 반복
        for (int i = 0; i < 12; i++) {
            YearMonth ym = start.plusMonths(i);
            result.add(ym.format(formatter));
        }

        return result;
    }
}
