package demo;

public class Report {
    private String title;
    private String results;
    private String hours;
    private String activities;
    private String firma;

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
