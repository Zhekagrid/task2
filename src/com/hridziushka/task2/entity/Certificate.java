package com.hridziushka.task2.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class Certificate {
    static Logger logger = LogManager.getLogger();
    private String number;
    private LocalDate from;
    private LocalDate to;
    private String recordingAgency;

    public Certificate(String number, LocalDate from, LocalDate to, String recordingAgency) {
        this.number = number;
        this.from = from;
        this.to = to;
        this.recordingAgency = recordingAgency;
        logger.log(Level.INFO, "Certificate was created");
    }

    public Certificate() {
        logger.log(Level.INFO, "Certificate was created");
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {

        this.to = to;
    }

    public String getRecordingAgency() {
        return recordingAgency;
    }

    public void setRecordingAgency(String recordingAgency) {
        this.recordingAgency = recordingAgency;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Certificate{");
        sb.append("number=").append(number);
        sb.append(", from=").append(from);
        sb.append(", to=").append(to);
        sb.append(", recordingAgency='").append(recordingAgency).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Certificate)) return false;

        Certificate that = (Certificate) o;

        if (getNumber() != null ? !getNumber().equals(that.getNumber()) : that.getNumber() != null) return false;
        if (getFrom() != null ? !getFrom().equals(that.getFrom()) : that.getFrom() != null) return false;
        if (getTo() != null ? !getTo().equals(that.getTo()) : that.getTo() != null) return false;
        return getRecordingAgency() != null ? getRecordingAgency().equals(that.getRecordingAgency()) : that.getRecordingAgency() == null;
    }

    @Override
    public int hashCode() {
        int result = getNumber() != null ? getNumber().hashCode() : 0;
        result = 31 * result + (getFrom() != null ? getFrom().hashCode() : 0);
        result = 31 * result + (getTo() != null ? getTo().hashCode() : 0);
        result = 31 * result + (getRecordingAgency() != null ? getRecordingAgency().hashCode() : 0);
        return result;
    }
}
