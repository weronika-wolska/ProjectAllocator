package entities;

public class TableRow {
    private long id;
    private String name;
    private String project;

    public TableRow(Long id, String name, String project) {
        this.id = id;
        this.name = name;
        this.project = project;
    }

    @Override
    public String toString() {
        return id + " " + this.name + " " + this.project;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}