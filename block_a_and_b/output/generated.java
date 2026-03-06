```java
/* FILE: ./tests/sample_inputs/courseman.puml */

import java.util.Collection;
import java.util.HashSet;

@DClass(mutable=true)
class Enrolment {
    // [REQ] from ./requirements_doc.txt: Internal mark must be between 0 and 100.
    private Double internalMark = null;
    @AttrRef("internalMark")
    public Double getInternalMark() {
        return internalMark;
    }
    @Mutator(requires="internalMark>=0 && internalMark<=100", effects="internalMark=internalMark")
    public void setInternalMark(Double internalMark) {
        this.internalMark = internalMark;
    }

    // [REQ] from ./requirements_doc.txt: Exam mark must be between 0 and 100.
    private Double examMark = null;
    @AttrRef("examMark")
    public Double getExamMark() {
        return examMark;
    }
    @Mutator(requires="examMark>=0 && examMark<=100", effects="examMark=examMark")
    public void setExamMark(Double examMark) {
        this.examMark = examMark;
    }

    // [REQ] from ./requirements_doc.txt: Final grade must be a character between 'A' and 'F'.
    private char finalGrade = '\u0000';
    @AttrRef("finalGrade")
    public char getFinalGrade() {
        return finalGrade;
    }
    @Mutator(requires="finalGrade>=65 && finalGrade<=90", effects="finalGrade=finalGrade") // Assuming 'A' is 65 and 'F' is 90
    public void setFinalGrade(char finalGrade) {
        this.finalGrade = finalGrade;
    }

    // [DEFERRED/UNMAPPED] Missing type definition: Address
    private Address address = null;
    @AttrRef("address")
    public Address getAddress() {
        return address;
    }
    @Mutator(requires="address!=null", effects="address=address")
    public void setAddress(Address address) {
        this.address = address;
    }

    // [DEFERRED/UNMAPPED] Missing type definition: Student
    private Student student = null;
    @AttrRef("student")
    public Student getStudent() {
        return student;
    }
    @Mutator(requires="student!=null", effects="student=student")
    public void setStudent(Student student) {
        this.student = student;
    }

    // [DEFERRED/UNMAPPED] Missing type definition: CourseModule
    private Collection<CourseModule> courseModules = new HashSet<>();
    @AttrRef("courseModules")
    public Collection<CourseModule> getCourseModules() {
        return courseModules;
    }
    @Mutator(requires="courseModules!=null", effects="courseModules=courseModules")
    public void setCourseModules(Collection<CourseModule> courseModules) {
        this.courseModules = courseModules;
    }

    // [DEFERRED/UNMAPPED] Missing type definition: ElectiveModule
    private Collection<ElectiveModule> electiveModules = new HashSet<>();
    @AttrRef("electiveModules")
    public Collection<ElectiveModule> getElectiveModules() {
        return electiveModules;
    }
    @Mutator(requires="electiveModules!=null", effects="electiveModules=electiveModules")
    public void setElectiveModules(Collection<ElectiveModule> electiveModules) {
        this.electiveModules = electiveModules;
    }

    // [DEFERRED/UNMAPPED] Missing type definition: CompulsoryModule
    private Collection<CompulsoryModule> compulsoryModules = new HashSet<>();
    @AttrRef("compulsoryModules")
    public Collection<CompulsoryModule> getCompulsoryModules() {
        return compulsoryModules;
    }
    @Mutator(requires="compulsoryModules!=null", effects="compulsoryModules=compulsoryModules")
    public void setCompulsoryModules(Collection<CompulsoryModule> compulsoryModules) {
        this.compulsoryModules = compulsoryModules;
    }

    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Enrolment{" +
                "internalMark=" + internalMark +
                ", examMark=" + examMark +
                ", finalGrade='" + finalGrade + '\'' +
                ", address=" + address +
                ", student=" + student +
                ", courseModules=" + courseModules +
                ", electiveModules=" + electiveModules +
                ", compulsoryModules=" + compulsoryModules +
                '}';
    }
}

@DClass(mutable=true)
class Student {
    // [DEFERRED/UNMAPPED] Missing type definition: Address
    private Address address = null;
    @AttrRef("address")
    public Address getAddress() {
        return address;
    }
    @Mutator(requires="address!=null", effects="address=address")
    public void setAddress(Address address) {
        this.address = address;
    }

    // [DEFERRED/UNMAPPED] Missing type definition: CourseModule
    private Collection<CourseModule> courseModules = new HashSet<>();
    @AttrRef("courseModules")
    public Collection<CourseModule> getCourseModules() {
        return courseModules;
    }
    @Mutator(requires="courseModules!=null", effects="courseModules=courseModules")
    public void setCourseModules(Collection<CourseModule> courseModules) {
        this.courseModules = courseModules;
    }

    // [DEFERRED/UNMAPPED] Missing type definition: ElectiveModule
    private Collection<ElectiveModule> electiveModules = new HashSet<>();
    @AttrRef("electiveModules")
    public Collection<ElectiveModule> getElectiveModules() {
        return electiveModules;
    }
    @Mutator(requires="electiveModules!=null", effects="electiveModules=electiveModules")
    public void setElectiveModules(Collection<ElectiveModule> electiveModules) {
        this.electiveModules = electiveModules;
    }

    // [DEFERRED/UNMAPPED] Missing type definition: CompulsoryModule
    private Collection<CompulsoryModule> compulsoryModules = new HashSet<>();
    @AttrRef("compulsoryModules")
    public Collection<CompulsoryModule> getCompulsoryModules() {
        return compulsoryModules;
    }
    @Mutator(requires="compulsoryModules!=null", effects="compulsoryModules=compulsoryModules")
    public void setCompulsoryModules(Collection<CompulsoryModule> compulsoryModules) {
        this.compulsoryModules = compulsoryModules;
    }

    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Student{" +
                "address=" + address +
                ", courseModules=" + courseModules +
                ", electiveModules=" + electiveModules +
                ", compulsoryModules=" + compulsoryModules +
                '}';
    }
}

@DClass(mutable=true)
class Address {
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Address{}";
    }
}

@DClass(mutable=true)
class CourseModule {
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "CourseModule{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", semester=" + semester +
                ", credits=" + credits +
                '}';
    }
}

@DClass(mutable=true)
class ElectiveModule {
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ElectiveModule{" +
                "deptName='" + deptName + '\'' +
                '}';
    }
}

@DClass(mutable=true)
class CompulsoryModule {
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "CompulsoryModule{}";
    }
}
```