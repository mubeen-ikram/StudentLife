package com.tnc.studentlife.ModelClasses;

import java.io.Serializable;
import java.util.Date;

public class EventInformation  implements Serializable {
    private String eventName;
    private Date startTime;
    private Date endTime;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
