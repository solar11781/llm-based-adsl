 ```java
/* FILE: block_a_and_b/input/domain/courseman.puml */
package courseman;

import java.util.*;
import dsl.annotations.*;

// ========================
// Classes
// ========================

@DClass(mutable=true)
class Enrolment {
  // /*** STATE SPACE ***/
  @DAttr(name="id", type=Type.Integer, id=true, auto=true)
  private int id;

  @DAttr(name="internalMark", type=Type.Real)
  private Double internalMark;

  @DAttr(name="examMark", type=Type.Real)
  private Double examMark;

  @DAttr(name="finalGrade", type=Type.Character)
  private char finalGrade;

  // /*** BEHAVIOUR SPACE (partial) ***/
  public Enrolment(@DAttr(name="internalMark") Double internalMark, @DAttr(name="examMark") Double examMark, @DAttr(name="finalGrade") char finalGrade) {
    this.internalMark = internalMark;
    this.examMark = examMark;
    this.finalGrade = finalGrade;
  }

  public int getId() {
    return id;
  }

  private void setId(int id) {
    this.id = id;
  }

  public Double getInternalMark() {
    return internalMark;
  }

  public void setInternalMark(@DAttr(name="internalMark", requires="internalMark >= 0 && internalMark <= 100") Double internalMark) {
    this.internalMark = internalMark;
  }

  public Double getExamMark() {
    return examMark;
  }

  public void setExamMark(@DAttr(name="examMark", requires="examMark >= 0 && examMark <= 100") Double examMark) {
    this.examMark = examMark;
  }

  public char getFinalGrade() {
    return finalGrade;
  }

  private void setFinalGrade(char finalGrade) {
    this.finalGrade = finalGrade;
  }
}

@DClass(mutable=true)
class Student {
  // /*** STATE SPACE ***/
  @DAttr(name="id", type=Type.Integer, id=true, auto=true)
  private int id;

  @DAttr(name="name", type=Type.String, length=50)
  private String name;

  // /*** ASSOCIATIONS ***/
  @DAssoc(ascName="enrolments", role="Student", ascType=AssocType.OneMany, endType=AssocEndType.Many)
  private List<CourseModule> enrolsIn = new ArrayList<>();

  @DAssoc(ascName="lives-at", role="Student", ascType=AssocType.OneOne, endType=AssocEndType.One)
  private Address livesAt;

  // /*** BEHAVIOUR SPACE (partial) ***/
  public Student(@DAttr(name="name") String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  private void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(@DAttr(name="name", requires="name != null && !name.isEmpty()") String name) {
    this.name = name;
  }

  public List<CourseModule> getEnrolsIn() {
    return enrolsIn;
  }

  public void addEnrolment(CourseModule courseModule) {
    if (!enrolsIn.contains(courseModule)) {
      enrolsIn.add(courseModule);
      // Update association count
      ((CourseModule) courseModule).setEnrolmentCount(((CourseModule) courseModule).getEnrolmentCount() + 1);
    } else {
      throw new ConstraintViolationException("CourseModule already enrolled");
    }
  }

  public void removeEnrolment(CourseModule courseModule) {
    if (enrolsIn.contains(courseModule)) {
      enrolsIn.remove(courseModule);
      // Update association count
      ((CourseModule) courseModule).setEnrolmentCount(((CourseModule) courseModule).getEnrolmentCount() - 1);
    } else {
      throw new ConstraintViolationException("CourseModule not found for removal");
    }
  }

  public Address getLivesAt() {
    return livesAt;
  }

  private void setLivesAt(Address address) {
    this.livesAt = address;
  }
}

@DClass(mutable=true)
class Address {
  // /*** STATE SPACE ***/
  @DAttr(name="id", type=Type.Integer, id=true, auto=true)
  private int id;

  @DAttr(name="name", type=Type.String, length=100)
  private String name;

  // /*** BEHAVIOUR SPACE (partial) ***/
  public Address(@DAttr(name="name") String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  private void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(@DAttr(name="name", requires="name != null && !name.isEmpty()") String name) {
    this.name = name;
  }
}

@DClass(mutable=true)
class CourseModule {
  // /*** STATE SPACE ***/
  @DAttr(name="code", type=Type.String, length=10)
  private String code;

  @DAttr(name="name", type=Type.String, length=50)
  private String name;

  @DAttr(name="semester", type=Type.Integer)
  private int semester;

  @DAttr(name="credits", type=Type.Integer)
  private int credits;

  // /*** ASSOCIATIONS ***/
  @DAssoc(ascName="enrolments", role="CourseModule", ascType=AssocType.ManyOne, endType=AssocEndType.One)
  private int enrolmentCount = 0;

  // /*** BEHAVIOUR SPACE (partial) ***/
  public CourseModule(@DAttr(name="code") String code, @DAttr(name="name") String name, @DAttr(name="semester") int semester, @DAttr(name="credits") int credits) {
    this.code = code;
    this.name = name;
    this.semester = semester;
    this.credits = credits;
  }

  public String getCode() {
    return code;
  }

  public void setCode(@DAttr(name="code", requires="code != null && !code.isEmpty()") String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(@DAttr(name="name", requires="name != null && !name.isEmpty()") String name) {
    this.name = name;
  }

  public int getSemester() {
    return semester;
  }

  public void setSemester(@DAttr(name="semester", requires="semester > 0 && semester < 9") int semester) {
    this.semester = semester;
  }

  public int getCredits() {
    return credits;
  }

  public void setCredits(@DAttr(name="credits", requires="credits > 0 && credits <= 15") int credits) {
    this.credits = credits;
  }

  protected void setEnrolmentCount(int count) {
    this.enrolmentCount = count;
  }

  public int getEnrolmentCount() {
    return enrolmentCount;
  }
}

@DClass(mutable=true)
class ElectiveModule extends CourseModule {
  // /*** STATE SPACE ***/
  @DAttr(name="deptName", type=Type.String, length=50)
  private String deptName;

  // /*** BEHAVIOUR SPACE (partial) ***/
  public ElectiveModule(@DAttr(name="code") String code, @DAttr(name="name") String name, @DAttr(name="semester") int semester, @DAttr(name="credits") int credits, @DAttr(name="deptName") String deptName) {
    super(code, name, semester, credits);
    this.deptName = deptName;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(@DAttr(name="deptName", requires="deptName != null && !deptName.isEmpty()") String deptName) {
    this.deptName = deptName;
  }
}

@DClass(mutable=true)
class CompulsoryModule extends CourseModule {
  // /*** BEHAVIOUR SPACE (partial) ***/
  public CompulsoryModule(@DAttr(name="code") String code, @DAttr(name="name") String name, @DAttr(name="semester") int semester, @DAttr(name="credits") int credits) {
    super(code, name, semester, credits);
  }
}
```
