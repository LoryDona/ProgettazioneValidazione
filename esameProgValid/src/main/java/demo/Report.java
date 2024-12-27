package demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String title;
    private String results;
    private String hours;
    private String activities;
    private String firma;
    private String progetto;
    private boolean isbozza;

    protected Report(){}

    public Report(String tit, String res, String h, String act,String f, String prog,boolean bozza) {
        title=tit;
        results=res;
        hours=h;
        activities=act;
        firma=f;
        progetto=prog;
        isbozza=bozza;
    }
    public int hashCode(){return Long.hashCode(id);}
    public boolean equals(Report a){return a.getId()==id;}
    public Long getId() {
        return id;
    }
    public String getTitle(){return title;}
    public String getResults(){return results;}
    public String getHours(){return hours;}
    public String getActivities(){return activities;}
    public String getFirma(){return firma;}
    public boolean getIsBozza() {
        return isbozza;
    }
    public String getProgetto() {
        return progetto;
    }
    public void setIsBozza(boolean bozza){isbozza=bozza;}



    public String toString(){
        return "Risultati:\n"+results+"\nOre: "+hours+"\nActivities:\n"+activities+"\nFirma: "+firma;
    }

}

