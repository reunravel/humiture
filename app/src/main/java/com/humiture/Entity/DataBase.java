package com.humiture.Entity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import io.objectbox.Box;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class DataBase {

    @Id
    public long id;
    public String Year, Month, Day, Hour, Minute, Second;
    public float CD, RH;

    public DataBase() {

    }

    public static void addData() {
        Thread thread = new Thread(() -> {
            Box<DataBase> dataBox = ObjectBox.boxStore.boxFor(DataBase.class);

            SimpleDateFormat Year = new SimpleDateFormat("yyyy", Locale.CHINA);
            SimpleDateFormat Month = new SimpleDateFormat("MM", Locale.CHINA);
            SimpleDateFormat Day = new SimpleDateFormat("dd", Locale.CHINA);
            SimpleDateFormat Hour = new SimpleDateFormat("HH", Locale.CHINA);
            SimpleDateFormat Minute = new SimpleDateFormat("mm", Locale.CHINA);
            SimpleDateFormat Second = new SimpleDateFormat("ss", Locale.CHINA);

            TcpClient.sharedCenter().setReceiveCallbackBlockBox(receivedMessage -> {
                DataBase dataBase = new DataBase();

                dataBase.setYear(Year.format(System.currentTimeMillis()));
                dataBase.setMonth(Month.format(System.currentTimeMillis()));
                dataBase.setDay(Day.format(System.currentTimeMillis()));
                dataBase.setHour(Hour.format(System.currentTimeMillis()));
                dataBase.setMinute(Minute.format(System.currentTimeMillis()));
                dataBase.setSecond(Second.format(System.currentTimeMillis()));
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String Month) {
        this.Month = Month;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String Day) {
        this.Day = Day;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String Hour) {
        this.Hour = Hour;
    }

    public String getMinute() {
        return Minute;
    }

    public void setMinute(String Minute) {
        this.Minute = Minute;
    }

    public String getSecond() {
        return Second;
    }

    public void setSecond(String Second) {
        this.Second = Second;
    }

    public float getCD() {
        return CD;
    }

    public void setCD(float CD) {
        this.CD = CD;
    }

    public float getRH() {
        return RH;
    }

    public void setRH(float RH) {
        this.RH = RH;
    }
}

