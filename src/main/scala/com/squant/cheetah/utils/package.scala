package com.squant.cheetah

import java.time.{LocalDateTime, LocalTime}
import java.time.format.DateTimeFormatter
import java.util.{Calendar, Date}

package object utils {

  import com.typesafe.config._

  lazy val config = ConfigFactory.load()

  /**
    * 计算两个日期之间相差的天数
    *
    * @param smdate 较小的时间
    * @param bdate  较大的时间
    * @return 相差天数
    */
  def daysBetween(smdate: Date, bdate: Date): Int = {
    val cal: Calendar = Calendar.getInstance
    cal.setTime(smdate)
    val time1: Long = cal.getTimeInMillis
    cal.setTime(bdate)
    val time2: Long = cal.getTimeInMillis
    val between_days: Long = (time2 - time1) / (1000 * 3600 * 24)
    String.valueOf(between_days).toInt
  }

  /**
    * 获取最新的交易日期
    *
    * @param date   date
    * @param format format
    * @return string
    */
  def getRecentWorkingDay(date: LocalDateTime, format: String): String = {
    var tmpDate = date;
    while (date.getDayOfWeek.getValue >= 6) tmpDate = date.plusDays(-1)
    tmpDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
  }

  /**
    * 判断当前时间是否是交易时间段
    * 周一到周五
    * 上午：09:30-11:30
    * 下午：13:00-14:45
    *
    * @return if now is trading time,return true, else return false
    */
  def isTradingTime(dateTime: LocalDateTime = LocalDateTime.now()): Boolean = {
    if (dateTime.getDayOfWeek.getValue >= 1 && dateTime.getDayOfWeek.getValue <= 5) {
      val now: LocalTime = dateTime.toLocalTime
      if ((now.isAfter(LocalTime.of(9, 30, 0, 0)) && now.isBefore(LocalTime.of(11, 30, 0, 0)))
        || (now.isAfter(LocalTime.of(13, 0, 0, 0))) && now.isBefore(LocalTime.of(15, 0, 0, 0))) return true
    }
    false
  }

  /**
    * 检查给定时间是否在时间范围内
    * @param date
    * @param start
    * @param stop
    * @return
    */
  def isInRange(date: LocalDateTime, start: LocalDateTime, stop: LocalDateTime): Boolean = {
    date.isAfter(start) && date.isBefore(stop)
  }
}

object Test extends App {

  import com.squant.cheetah.utils._

  println(isTradingTime(LocalDateTime.of(2016, 12, 16, 11, 50, 0)))
}