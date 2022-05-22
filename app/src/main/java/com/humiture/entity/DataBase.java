package com.humiture.entity;

import static com.humiture.entity.TcpClient.sharedCenter;

import java.text.SimpleDateFormat;
import java.util.Locale;

import io.objectbox.Box;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.query.Query;

@Entity
public class DataBase {

    @Id
    public long id;
    public String Year, Month, Day, Hour, Minute;
    public float Second, CD, RH;

    public DataBase() {

    }

    public static void addData() {
        Thread thread = new Thread(() -> {
            Box<DataBase> dataBox = ObjectBox.boxStore.boxFor(DataBase.class);

            SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.CHINA);
            SimpleDateFormat month = new SimpleDateFormat("MM", Locale.CHINA);
            SimpleDateFormat day = new SimpleDateFormat("dd", Locale.CHINA);
            SimpleDateFormat hour = new SimpleDateFormat("HH", Locale.CHINA);
            SimpleDateFormat minute = new SimpleDateFormat("mm", Locale.CHINA);
            SimpleDateFormat second = new SimpleDateFormat("ss", Locale.CHINA);

            sharedCenter().setReceiveCallbackBlockBox(receivedMessage -> {
                DataBase dataBase = new DataBase();

                dataBase.setYear(year.format(System.currentTimeMillis()));
                dataBase.setMonth(month.format(System.currentTimeMillis()));
                dataBase.setDay(day.format(System.currentTimeMillis()));
                dataBase.setHour(hour.format(System.currentTimeMillis()));
                dataBase.setMinute(minute.format(System.currentTimeMillis()));
                dataBase.setSecond(Float.parseFloat(second.format(System.currentTimeMillis())));
                dataBase.setCD(receivedMessage[0]);
                dataBase.setRH(receivedMessage[1]);

                dataBox.put(dataBase);
            });
        });
        thread.start();
    }

    public static String[] listYearData() {
        Box<DataBase> dataBox = ObjectBox.boxStore.boxFor(DataBase.class);
        return dataBox.query().build().property(DataBase_.Year).distinct().findStrings();
    }

    public static String[] listMonthData(String year) {
        Box<DataBase> dataBox = ObjectBox.boxStore.boxFor(DataBase.class);
        return dataBox.query(DataBase_.Year.equal(year)).build().property(DataBase_.Month).distinct().findStrings();
    }

    public static String[] listDayData(String year, String month) {
        Box<DataBase> dataBox = ObjectBox.boxStore.boxFor(DataBase.class);
        return dataBox.query(DataBase_.Year.equal(year).and(DataBase_.Month.equal(month))).build().property(DataBase_.Day).distinct().findStrings();
    }

    public static String[] listHourData(String year, String month, String day) {
        Box<DataBase> dataBox = ObjectBox.boxStore.boxFor(DataBase.class);
        return dataBox.query(DataBase_.Year.equal(year).and(DataBase_.Month.equal(month).and(DataBase_.Day.equal(day)))).build().property(DataBase_.Hour).distinct().findStrings();
    }

    public static String[] listMinuteData(String year, String month, String day, String hour) {
        Box<DataBase> dataBox = ObjectBox.boxStore.boxFor(DataBase.class);
        return dataBox.query(DataBase_.Year.equal(year).and(DataBase_.Month.equal(month).and(DataBase_.Day.equal(day).and(DataBase_.Hour.equal(hour))))).build().property(DataBase_.Minute).distinct().findStrings();
    }

    public static Query<DataBase> queryData(String year, String month, String day, String hour, String minute) {
        Box<DataBase> dataBox = ObjectBox.boxStore.boxFor(DataBase.class);
        return dataBox.query(DataBase_.Year.equal(year).and(DataBase_.Month.equal(month).and(DataBase_.Day.equal(day).and(DataBase_.Hour.equal(hour)).and(DataBase_.Minute.equal(minute))))).build();
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public void setMonth(String Month) {
        this.Month = Month;
    }

    public void setDay(String Day) {
        this.Day = Day;
    }

    public void setHour(String Hour) {
        this.Hour = Hour;
    }

    public void setMinute(String Minute) {
        this.Minute = Minute;
    }

    public void setSecond(Float Second) {
        this.Second = Second;
    }

    public void setCD(float CD) {
        this.CD = CD;
    }

    public void setRH(float RH) {
        this.RH = RH;
    }
}

