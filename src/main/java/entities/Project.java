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

    @Override
    public String toString() {
        return "Project [projectName=" + projectName + ", stream=" + stream + ", supervisor=" + supervisor.getName() + "]";
    }
    
}