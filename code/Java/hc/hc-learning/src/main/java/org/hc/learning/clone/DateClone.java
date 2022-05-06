package org.hc.learning.clone;

import java.util.Calendar;
import java.util.Date;

public class DateClone {

    public static void main(String[] args) {
        Date time = Calendar.getInstance().getTime();
        Date clone = (Date) time.clone();
        System.out.println(clone);
        System.out.println(clone == time);
    }

}
