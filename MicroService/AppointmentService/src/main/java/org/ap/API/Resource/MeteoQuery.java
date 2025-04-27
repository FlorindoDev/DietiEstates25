package org.ap.API.Resource;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class MeteoQuery {

    @QueryParam("hourly")
    @DefaultValue("precipitation,precipitation_probability,cloud_cover")
    private String hourly;

    @QueryParam("latitude")
    @DefaultValue("none")
    private String latitudine;

    @QueryParam("longitude")
    @DefaultValue("none")
    private String logitudine;

    @QueryParam("time_mode")
    @DefaultValue("time_interval")
    private String timeMode;

    @QueryParam("start_date")
    @DefaultValue("none")
    private String startDate;

    @QueryParam("end_date")
    @DefaultValue("none")
    private String endDate;


    public String getHourly() {
        return hourly;
    }

    public void setHourly(String hourly) {
        this.hourly = hourly;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLogitudine() {
        return logitudine;
    }

    public void setLogitudine(String logitudine) {
        this.logitudine = logitudine;
    }

    public String getTimeMode() {
        return timeMode;
    }

    public void setTimeMode(String timeMode) {
        this.timeMode = timeMode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String makeParametersQuery(MeteoQuery query){

        StringBuilder parameters = new StringBuilder("latitude=" + query.getLatitudine());

        parameters.append("&longitude=").append(query.getLogitudine());

        parameters.append("&hourly=").append(query.getHourly());

        parameters.append("&time_mode=").append(query.getTimeMode());

        parameters.append("&start_date=").append(query.getStartDate());

        parameters.append("&end_date=").append(query.getEndDate());

        return parameters.toString();
    }
}
