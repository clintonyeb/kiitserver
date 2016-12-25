package com.clinton.kiitsocial

import grails.transaction.Transactional
import org.joda.time.Duration
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder

@Transactional
class UtilityService {

    def prettyTime(date, request){
        if (!date) return
        long millTime
        /* if ('org.joda.time.DateTime' == date.class.name) {
             millTime = date.millis
         } else millTime = date
 */
        Duration duration = new Duration(6000)// in milliseconds
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendDays()
                .appendSuffix("d")
                .appendHours()
                .appendSuffix("h")
                .appendMinutes()
                .appendSuffix("m")
                .appendSeconds()
                .appendSuffix("s")
                .toFormatter();
        String formatted = formatter.print(duration.toPeriod());
        System.out.println(formatted);
        formatted
    }
}
