package com.hl5.countbook;


public class Counter {
    public String name;
    public String date;
    public int currentValue;
    public int initialValue;
    public String comment;


    @Override
    public String toString() {
        return  name + '\n' +
                 date + '\n' +
                 currentValue + "\n"
                 + initialValue + "\n" +
                comment;
    }

    public Counter(String name, String date, int currentValue, int initialValue, String comment) {
        this.name = name;
        this.date = date;
        this.currentValue = currentValue;
        this.initialValue = initialValue;
        this.comment = comment;
    }
}
