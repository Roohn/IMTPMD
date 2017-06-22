package nl.hsleiden.imtpmd.models;

import java.io.Serializable;

public class Modules implements Serializable{
    private String code;
    private String naam;
    private int ECTS;
    private int jaar;
    private int semester;
    private String soort;
    private String cijfer;

    public Modules(String code, String naam, int ECTS, int jaar, int semester, String soort, String cijfer) {
        this.code = code;
        this.naam = naam;
        this.ECTS = ECTS;
        this.jaar = jaar;
        this.semester = semester;
        this.soort = soort;
        this.cijfer = cijfer;
    }

    public Modules(String code, String naam, int ECTS, String soort, String cijfer) {
        this.code = code;
        this.naam = naam;
        this.ECTS = ECTS;
        this.soort = soort;
        this.cijfer = cijfer;
    }

    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }

    public String getCijfer() { return cijfer; }

    public int getECTS() {
        return ECTS;
    }

    public int getJaar() {
        return jaar;
    }

    public int getSemester() {
        return semester;
    }

    public String getSoort() {
        return soort;
    }

    public void setCijfer(String cijfer) {
        this.cijfer = cijfer;
    }
}
