package org.md.Estate;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class EstateFilter {

    @QueryParam("page")
    @DefaultValue("1")
    private Integer page = 1;

    @QueryParam("limit")
    @DefaultValue("10")
    private Integer limit = 10;

    @QueryParam("sort")
    @DefaultValue("prezzo")
    private String sort = "prezzo";

    @QueryParam("desc")
    @DefaultValue("false")
    private Boolean desc = false;

    @QueryParam("stato")
    private String stato = "";

    @QueryParam("citta")
    private String citta = "";

    @QueryParam("quartiere")
    private String quartiere = "";

    @QueryParam("via")
    private String via = "";

    @QueryParam("minPrice")
    private Double minPrice = null;

    @QueryParam("maxPrice")
    private Double maxPrice = null;

    @QueryParam("minSpace")
    private Double minSpace = null;

    @QueryParam("maxSpace")
    private Double maxSpace = null;

    @QueryParam("minRooms")
    private Integer minRooms = null;

    @QueryParam("maxRooms")
    private Integer maxRooms = null;

    @QueryParam("wc")
    private Integer wc = null;

    @QueryParam("elevator")
    private Boolean elevator = null;

    @QueryParam("state")
    private String state = "";

    @QueryParam("garage")
    private Integer garage = null;

    @QueryParam("energeticClass")
    private String energeticClass = "";

    public Boolean getDesc() {
        return desc;
    }

    public void setDesc(Boolean desc) {
        this.desc = desc;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getQuartiere() {
        return quartiere;
    }

    public void setQuartiere(String quartiere) {
        this.quartiere = quartiere;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinSpace() {
        return minSpace;
    }

    public void setMinSpace(Double minSpace) {
        this.minSpace = minSpace;
    }

    public Double getMaxSpace() {
        return maxSpace;
    }

    public void setMaxSpace(Double maxSpace) {
        this.maxSpace = maxSpace;
    }

    public Integer getMinRooms() {
        return minRooms;
    }

    public void setMinRooms(Integer minRooms) {
        this.minRooms = minRooms;
    }

    public Integer getMaxRooms() {
        return maxRooms;
    }

    public void setMaxRooms(Integer maxRooms) {
        this.maxRooms = maxRooms;
    }

    public Integer getWc() {
        return wc;
    }

    public void setWc(Integer wc) {
        this.wc = wc;
    }

    public Boolean getElevator() {
        return elevator;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getGarage() {
        return garage;
    }

    public void setGarage(Integer garage) {
        this.garage = garage;
    }

    public String getEnergeticClass() {
        return energeticClass;
    }

    public void setEnergeticClass(String energeticClass) {
        this.energeticClass = energeticClass;
    }

    @Override
    public String toString() {
        return "EstateFilter{" +
                "page=" + page +
                ", limit=" + limit +
                ", sort='" + sort + '\'' +
                ", stato='" + stato + '\'' +
                ", citta='" + citta + '\'' +
                ", quartiere='" + quartiere + '\'' +
                ", via='" + via + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", minSpace=" + minSpace +
                ", maxSpace=" + maxSpace +
                ", minRooms=" + minRooms +
                ", maxRooms=" + maxRooms +
                ", wc=" + wc +
                ", elevator=" + elevator +
                ", state='" + state + '\'' +
                ", garage=" + garage +
                ", energeticClass='" + energeticClass + '\'' +
                '}';
    }
}
