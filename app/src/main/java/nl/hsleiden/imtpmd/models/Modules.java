package nl.hsleiden.imtpmd.models;

public class Modules {
    private String code;
    private String naam;
    private int ECTS;
    private int jaar;
    private int semester;
    private String soort;

    public Modules(String code, String naam, int ECTS, int jaar, int semester, String soort) {
        this.code = code;
        this.naam = naam;
        this.ECTS = ECTS;
        this.jaar = jaar;
        this.semester = semester;
        this.soort = soort;
    }

    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }

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
}
