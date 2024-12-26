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

    protected Report(){}

    public Report(String tit, String res, String h, String act,String f) {
        title=tit;
        results=res;
        hours=h;
        activities=act;
        firma=f;
    }
    public int hashCode(){
        return title.hashCode();
    }

    public boolean equals(Report a){
        return title.equals(a.getTitle());
    }
    public Long getId() {
        return id;
    }
    public String getTitle(){return title;}
    public String getResults(){return results;}
    public String getHours(){return hours;}
    public String getActivities(){return activities;}
    public String getFirma(){return firma;}
}
