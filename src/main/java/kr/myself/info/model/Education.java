package kr.myself.info.model;

public class Education {
    private Integer graduationYear;
    private String schoolName;
    private String major;
    private String graduationSatus;

    public Education(){};

    public Education(Integer graduationYear, String schoolName, String major, String graduationSatus) {
        this.graduationYear = graduationYear;
        this.schoolName = schoolName;
        this.major = major;
        this.graduationSatus = graduationSatus;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGraduationSatus() {
        return graduationSatus;
    }

    public void setGraduationSatus(String graduationSatus) {
        this.graduationSatus = graduationSatus;
    }

    @Override
    public String toString() {
        return "Education{" +
                "graduationYear=" + graduationYear +
                ", schoolName='" + schoolName + '\'' +
                ", major='" + major + '\'' +
                ", graduationSatus='" + graduationSatus + '\'' +
                '}';
    }
}
