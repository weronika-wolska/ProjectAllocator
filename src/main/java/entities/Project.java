package entities;

public class Project{
    private String projectName;
    private Stream stream;
    private StaffMember supervisor;

    public Project(String projectName, Stream stream, StaffMember supervisor){
        this.projectName=projectName;
        this.stream=stream;
        this.supervisor=supervisor;
    }

    public Project(String projectName){
        this.projectName=projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    public StaffMember getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(StaffMember supervisor) {
        this.supervisor = supervisor;
    }

   /* @Override
    public String toString() {
        return "Project [projectName=" + projectName + ", stream=" + stream + ", supervisor=" + supervisor.getName() + "]";
    }*/

    public boolean isTheSameProject(String projectName) {
        //System.out.println("Entered isTheSameProject in:" + this.projectName + "_with_" + projectName + "_");
        String realNameOfThisProject = this.projectName.trim().toLowerCase();
        if(realNameOfThisProject.length() > 0) {
            if(realNameOfThisProject.charAt(realNameOfThisProject.length() - 1) == '.' || realNameOfThisProject.charAt(realNameOfThisProject.length() - 1) == '?') {
                realNameOfThisProject = realNameOfThisProject.substring(0, realNameOfThisProject.length() - 1);
            }

        }
        String realNameOfPotentialTwinProject = projectName.trim().toLowerCase();
        if(realNameOfPotentialTwinProject.length() > 0) {
            //System.out.println("Here is the last character:" + realNameOfPotentialTwinProject.charAt(realNameOfPotentialTwinProject.length() - 1) + "| and here is the length:" + realNameOfPotentialTwinProject.length());
            if(realNameOfPotentialTwinProject.charAt(realNameOfPotentialTwinProject.length() - 1) == '.' || realNameOfPotentialTwinProject.charAt(realNameOfPotentialTwinProject.length() - 1) == '?') {
                //System.out.println("A . or ? character was found as last");
                realNameOfPotentialTwinProject = realNameOfPotentialTwinProject.substring(0, realNameOfPotentialTwinProject.length() - 1);
            }
        }
        //System.out.println("Here they are, this and potential:" + realNameOfThisProject + "_and_" + realNameOfPotentialTwinProject + "_and result, they are equal is:" + realNameOfPotentialTwinProject.equals(realNameOfThisProject));
        return realNameOfPotentialTwinProject.equals(realNameOfThisProject);
    }

    public static boolean isValidProjectName(String projectName) {
        return projectName != null &&
                !projectName.equals("");
    }


}