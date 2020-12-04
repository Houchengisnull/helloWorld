package org.hc.learning.datetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Calendar打印日历 {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format = new SimpleDateFormat("dd");
        int months = calendar.getActualMaximum(Calendar.MONTH);
        int january = calendar.getActualMinimum(Calendar.MONTH);
        for (int month = january; month <= months ; month++) {
            int firstDateOfMonth = calendar.getActualMinimum(Calendar.DATE);
            calendar.set(calendar.get(Calendar.YEAR), month , firstDateOfMonth);
            System.out.println("-------- " + formatYear.format(calendar.getTime()) + " --------");
            int dates = calendar.getActualMaximum(Calendar.DATE);
            for (int i = firstDateOfMonth - 1 ; i < dates ; i++) {
                System.out.print(format.format(calendar.getTime()) + "\t");
                calendar.add(Calendar.DATE, 1);
                if ((i+1) % 7 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
        }
    }

}
