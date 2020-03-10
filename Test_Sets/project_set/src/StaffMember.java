public class StaffMember{
    private String name;
    private ArrayList<String> researchActivity;
    private String researchArea;
    private classes.Stream specialFocus;

    public StaffMember(String name, String researchActivity, String researchArea, classes.Stream specialFocus){
        this.name=name;
        this.researchActivity=researchActivity;
        this.researchArea=researchArea;
        this.specialFocus=specialFocus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResearchActivity() {
        return researchActivity;
    }

    public void setResearchActivity(String researchActivity) {
        this.researchActivity = researchActivity;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public Stream getSpecialFocus() {
        return specialFocus;
    }

    public void setSpecialFocus(Stream specialFocus) {
        this.specialFocus = specialFocus;
    }

    @Override
    public String toString() {
        return "StaffMember [name=" + name + ", researchActivity=" + researchActivity + ", researchArea=" + researchArea
                + ", specialFocus=" + specialFocus + "]";
    }

    
}