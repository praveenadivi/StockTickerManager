package com.rbc.stocktickermanager.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvNumber;
import com.rbc.stocktickermanager.constants.Constants;

import javax.persistence.*;
import java.util.Date;
import java.util.StringJoiner;

@Entity(name = "TRADE_DATA")
@Table(name="TRADE_DATA")
@SequenceGenerator(
        name = "TRADE_DATA_SEQ_GEN",
        sequenceName = "TRADE_DATA_SEQ"
)
public class Trade {

    @Id
    @Column
    @CsvIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = true)
    @CsvBindByName(column = "quarter", required = false)
    private int quarter;

    @Column(nullable = true)
    @CsvBindByName(column = "stock", required = false)
    private String stock;

    @Column(nullable = true)
    @CsvBindByName(column = "date", required = false)
    @CsvDate("MM/dd/yyyy")
    private Date date;

    @Column(nullable = true)
    @CsvBindByName(column = "open", required = false)
    @CsvNumber("'$'0.##")
    private double open;

    @Column(nullable = true)
    @CsvBindByName(column = "high", required = false)
    @CsvNumber("'$'0.##")
    private double high;

    @Column(nullable = true)
    @CsvBindByName(column = "low", required = false)
    @CsvNumber("'$'0.##")
    private double low;

    @Column(nullable = true)
    @CsvBindByName(column = "close", required = false)
    @CsvNumber("'$'0.##")
    private double close;

    @Column(nullable = true)
    @CsvBindByName(column = "volume", required = false)
    private long volume;

    @Column(name = "percent_change_price", nullable = true)
    @CsvBindByName(column = "percent_change_price", required = false)
    private double percentChangePrice;

    @Column(name = "percent_change_volume_over_last_wk", nullable = true)
    @CsvBindByName(column = "percent_change_volume_over_last_wk", required = false)
    private double percentChangeVolumeOverLastWk;

    @Column(name = "previous_weeks_volume", nullable = true)
    @CsvBindByName(column = "previous_weeks_volume", required = false)
    private double previousWeeksVolume;

    @Column(name = "next_weeks_open", nullable = true)
    @CsvBindByName(column = "next_weeks_open", required = false)
    @CsvNumber("'$'0.##")
    private double nextWeeksOpen;

    @Column(name = "next_weeks_close", nullable = true)
    @CsvBindByName(column = "next_weeks_close", required = false)
    @CsvNumber("'$'0.##")
    private double nextWeeksClose;

    @Column(name = "percent_change_next_weeks_price",  nullable = true)
    @CsvBindByName(column = "percent_change_next_weeks_price", required = false)
    private double percentChangeNextWeeksPrice;

    @Column(name = "days_to_next_dividend",  nullable = true)
    @CsvBindByName(column = "days_to_next_dividend", required = false)
    private int daysToNextDividend;

    @Column(name = "percent_return_next_dividend",  nullable = true)
    @CsvBindByName(column = "percent_return_next_dividend", required = false)
    private double percentReturnNextDividend;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getPercentChangePrice() {
        return percentChangePrice;
    }

    public void setPercentChangePrice(double percentChangePrice) {
        this.percentChangePrice = percentChangePrice;
    }

    public double getPercentChangeVolumeOverLastWk() {
        return percentChangeVolumeOverLastWk;
    }

    public void setPercentChangeVolumeOverLastWk(double percentChangeVolumeOverLastWk) {
        this.percentChangeVolumeOverLastWk = percentChangeVolumeOverLastWk;
    }

    public double getPreviousWeeksVolume() {
        return previousWeeksVolume;
    }

    public void setPreviousWeeksVolume(double previousWeeksVolume) {
        this.previousWeeksVolume = previousWeeksVolume;
    }

    public double getNextWeeksOpen() {
        return nextWeeksOpen;
    }

    public void setNextWeeksOpen(double nextWeeksOpen) {
        this.nextWeeksOpen = nextWeeksOpen;
    }

    public double getNextWeeksClose() {
        return nextWeeksClose;
    }

    public void setNextWeeksClose(double nextWeeksClose) {
        this.nextWeeksClose = nextWeeksClose;
    }

    public double getPercentChangeNextWeeksPrice() {
        return percentChangeNextWeeksPrice;
    }

    public void setPercentChangeNextWeeksPrice(double percentChangeNextWeeksPrice) {
        this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice;
    }

    public int getDaysToNextDividend() {
        return daysToNextDividend;
    }

    public void setDaysToNextDividend(int daysToNextDividend) {
        this.daysToNextDividend = daysToNextDividend;
    }

    public double getPercentReturnNextDividend() {
        return percentReturnNextDividend;
    }

    public void setPercentReturnNextDividend(double percentReturnNextDividend) {
        this.percentReturnNextDividend = percentReturnNextDividend;
    }

    @Override
    public String toString() {
        return new StringJoiner(",", "", "")
                .add("" + quarter)
                .add("" + stock)
                .add("" + Constants.SupportedFormats.DATE_FORMAT.format(date))
                .add("" + open)
                .add("" + high)
                .add("" + low)
                .add("" + close)
                .add("" + volume)
                .add("" + percentChangePrice)
                .add("" + percentChangeVolumeOverLastWk)
                .add("" + previousWeeksVolume)
                .add("" + nextWeeksOpen)
                .add("" + nextWeeksClose)
                .add("" + percentChangeNextWeeksPrice)
                .add("" + daysToNextDividend)
                .add("" + percentReturnNextDividend)
                .toString();
    }
}
