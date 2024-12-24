package demo;

public class Report {
    String title;
    String results;
    String hours;
    String activities;
    String firma;

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

    public String getTitle(){
        return title;
    }
}
