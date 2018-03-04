package com.example.bo.datepick_p8;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Cong Nhat on 9/15/2017.
 */

public class Job {
    private String title;
    private String description;
    private Date dateFinish;
    private Date timeFinish;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public Date getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(Date timeFinish) {
        this.timeFinish = timeFinish;
    }

    public Job(String title, String description, Date dateFinish, Date timeFinish) {
        this.title = title;
        this.description = description;
        this.dateFinish = dateFinish;
        this.timeFinish = timeFinish;
    }

    public Job() {
        super();
    }
    public String getDateFormat(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(d);
    }
    public String getTimeFormat(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(d);
    }
    @Override
    public String toString() {
        return this.title+"-"+ getDateFormat(this.dateFinish)+"-"+ getTimeFormat(this.timeFinish);
    }
}
