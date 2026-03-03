/* FILE: courseman/model/Enrolment.java */

import java.util.Objects;

// [INFERRED] DCSL import base package assumed: domainapp.basics.model.meta
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Type;
import domainapp.basics.model.meta.AssocType;
import domainapp.basics.model.meta.AssocEndType;
import domainapp.basics.model.meta.Associate;

@DClass(mutable=true) // from Domain PUML: class Enrolment
public class Enrolment {

  /*** STATE SPACE ***/

  @DAttr(name="id", type=Type.Integer, id=true, auto=true, mutable=false, optional=false)
  private int id;
  private static int idCounter = 0;

  // [INFERRED] reason: marks are typically entered later; allow null during creation
  @DAttr(name="internalMark", type=Type.Real, optional=true, min=0.0, max=100.0, mutable=true)
  private Double internalMark;

  // [INFERRED] reason: marks are typically entered later; allow null during creation
  @DAttr(name="examMark", type=Type.Real, optional=true, min=0.0, max=100.0, mutable=true)
  private Double examMark;

  // from Domain PUML: finalGrade : char
  // [INFERRED] reason: final grade may be computed/assigned later
  @DAttr(name="finalGrade", type=Type.Char, optional=true, mutable=true)
  private Character finalGrade;

  // from Domain PUML: association class attached to (Student, CourseModule) .. Enrolment : enrolments
  // [INFERRED] reason: association-class semantics => each Enrolment links exactly 1 Student and 1 CourseModule
  @DAttr(name="student", type=Type.Domain, optional=false)
  @DAssoc(
      ascName="enrolments",
      role="enrolment",
      ascType=AssocType.One2Many,    // many enrolments per student
      endType=AssocEndType.One,
      associate=@Associate(type=Student.class, cardMin=1, cardMax=1)
  )
  private Student student;

  @DAttr(name="module", type=Type.Domain, optional=false)
  @DAssoc(
      ascName="enrolments",
      role="enrolment",
      ascType=AssocType.One2Many,    // many enrolments per module
      endType=AssocEndType.One,
      associate=@Associate(type=CourseModule.class, cardMin=1, cardMax=1)
  )
  private CourseModule module;

  /*** BEHAVIOUR SPACE (partial) ***/

  @DOpt(
      type=DOpt.Type.ObjectFormConstructor,
      requires="student != null and module != null",
      effects="this.id = genId(); this.student = student; this.module = module"
  )
  public Enrolment(
      @AttrRef("student") Student student,
      @AttrRef("module") CourseModule module
  ) {
    this.id = genId();
    this.student = student;
    this.module = module;
  }

  @DOpt(type=DOpt.Type.AutoAttributeValueGen, effects="return genId()")
  @AttrRef("id")
  private static int genId() {
    return ++idCounter;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return id")
  @AttrRef("id")
  public int getId() {
    return id;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return internalMark")
  @AttrRef("internalMark")
  public Double getInternalMark() {
    return internalMark;
  }

  @DOpt(
      type=DOpt.Type.Setter,
      requires="internalMark == null or (internalMark >= 0.0 and internalMark <= 100.0)", // [INFERRED] mark range
      effects="this.internalMark = internalMark"
  )
  @AttrRef("internalMark")
  public void setInternalMark(Double internalMark) {
    this.internalMark = internalMark;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return examMark")
  @AttrRef("examMark")
  public Double getExamMark() {
    return examMark;
  }

  @DOpt(
      type=DOpt.Type.Setter,
      requires="examMark == null or (examMark >= 0.0 and examMark <= 100.0)", // [INFERRED] mark range
      effects="this.examMark = examMark"
  )
  @AttrRef("examMark")
  public void setExamMark(Double examMark) {
    this.examMark = examMark;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return finalGrade")
  @AttrRef("finalGrade")
  public Character getFinalGrade() {
    return finalGrade;
  }

  @DOpt(type=DOpt.Type.Setter, requires="true", effects="this.finalGrade = finalGrade")
  @AttrRef("finalGrade")
  public void setFinalGrade(Character finalGrade) {
    this.finalGrade = finalGrade;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return student")
  @AttrRef("student")
  public Student getStudent() {
    return student;
  }

  @DOpt(type=DOpt.Type.Setter, requires="student != null", effects="this.student = student")
  @AttrRef("student")
  public void setStudent(Student student) {
    this.student = student;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return module")
  @AttrRef("module")
  public CourseModule getModule() {
    return module;
  }

  @DOpt(type=DOpt.Type.Setter, requires="module != null", effects="this.module = module")
  @AttrRef("module")
  public void setModule(CourseModule module) {
    this.module = module;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Enrolment)) return false;
    Enrolment other = (Enrolment) obj;
    return id == other.id;
  }

  @Override
  public String toString() {
    return "Enrolment(" + id + ")";
  }
}

// ----------------------------------------

/* FILE: courseman/model/Student.java */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

// [INFERRED] DCSL import base package assumed: domainapp.basics.model.meta
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Type;
import domainapp.basics.model.meta.AssocType;
import domainapp.basics.model.meta.AssocEndType;
import domainapp.basics.model.meta.Associate;

@DClass(mutable=true) // from Domain PUML: class Student
public class Student {

  /*** STATE SPACE ***/

  @DAttr(name="id", type=Type.Integer, id=true, auto=true, mutable=false, optional=false)
  private int id;
  private static int idCounter = 0;

  @DAttr(name="name", type=Type.String, optional=false, mutable=true, length=-1)
  private String name;

  // from Domain PUML: Student "1" -- "1" Address : lives-at
  @DAttr(name="address", type=Type.Domain, optional=false)
  @DAssoc(
      ascName="lives-at",
      role="student",
      ascType=AssocType.One2One,
      endType=AssocEndType.One,
      associate=@Associate(type=Address.class, cardMin=1, cardMax=1)
  )
  private Address address;

  // from Domain PUML: Student "*" --> "0..30" CourseModule : enrols-in
  @DAttr(name="modules", type=Type.Collection, optional=true)
  @DAssoc(
      ascName="enrols-in",
      role="student",
      ascType=AssocType.Many2Many,
      endType=AssocEndType.Many,
      associate=@Associate(type=CourseModule.class, cardMin=0, cardMax=30)
  )
  private Collection<CourseModule> modules;

  // [INFERRED] reason: enforce upper bound 30 for modules
  private int modulesCount;

  // from Domain PUML: association class Enrolment attached to Student–CourseModule association
  // [INFERRED] reason: maintain explicit enrolments collection for association-class instances
  @DAttr(name="enrolments", type=Type.Collection, optional=true)
  @DAssoc(
      ascName="enrolments",
      role="student",
      ascType=AssocType.One2Many,
      endType=AssocEndType.Many,
      associate=@Associate(type=Enrolment.class, cardMin=0, cardMax=30) // [INFERRED] mirrors enrols-in bound
  )
  private Collection<Enrolment> enrolments;

  // [INFERRED] reason: keep enrolments count aligned with module bound when used
  private int enrolmentsCount;

  /*** BEHAVIOUR SPACE (partial) ***/

  @DOpt(
      type=DOpt.Type.ObjectFormConstructor,
      requires="name != null and address != null",
      effects="this.id = genId(); this.name = name; this.address = address; this.modules = new ArrayList<>(); this.enrolments = new ArrayList<>()"
  )
  public Student(
      @AttrRef("name") String name,
      @AttrRef("address") Address address
  ) {
    this.id = genId();
    this.name = name;
    this.address = address;
    this.modules = new ArrayList<>();
    this.modulesCount = 0;
    this.enrolments = new ArrayList<>();
    this.enrolmentsCount = 0;
  }

  @DOpt(type=DOpt.Type.AutoAttributeValueGen, effects="return genId()")
  @AttrRef("id")
  private static int genId() {
    return ++idCounter;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return id")
  @AttrRef("id")
  public int getId() {
    return id;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return name")
  @AttrRef("name")
  public String getName() {
    return name;
  }

  @DOpt(type=DOpt.Type.Setter, requires="name != null", effects="this.name = name")
  @AttrRef("name")
  public void setName(String name) {
    this.name = name;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return address")
  @AttrRef("address")
  public Address getAddress() {
    return address;
  }

  @DOpt(type=DOpt.Type.Setter, requires="address != null", effects="this.address = address")
  @AttrRef("address")
  public void setAddress(Address address) {
    this.address = address;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return modules")
  @AttrRef("modules")
  public Collection<CourseModule> getModules() {
    return modules;
  }

  @DOpt(type=DOpt.Type.LinkCountGetter, effects="return modulesCount")
  @AttrRef("modulesCount")
  public int getModulesCount() {
    return modulesCount;
  }

  // ---- Link management: modules (enrols-in) ----

  @DOpt(
      type=DOpt.Type.LinkAdder,
      requires="modules != null and xs != null and (modulesCount + xs.size()) <= 30", // from PUML: 0..30
      effects="modules.addAll(xs); modulesCount = modules.size()"
  )
  @AttrRef("modules")
  public void addModules(Collection<CourseModule> xs) {
    if (xs == null || xs.isEmpty()) return;
    for (CourseModule m : xs) {
      addModules(m);
    }
  }

  @DOpt(
      type=DOpt.Type.LinkAdder,
      requires="modules != null and x != null and modulesCount < 30", // from PUML: 0..30
      effects="modules.add(x); modulesCount = modules.size()"
  )
  @AttrRef("modules")
  public void addModules(CourseModule x) {
    if (x == null) return;
    if (!modules.contains(x)) {
      modules.add(x);
      modulesCount = modules.size();
    }
  }

  @DOpt(
      type=DOpt.Type.LinkRemover,
      requires="modules != null and xs != null",
      effects="modules.removeAll(xs); modulesCount = modules.size()"
  )
  @AttrRef("modules")
  public void removeModules(Collection<CourseModule> xs)
      throws ConstraintViolationException { // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    if (xs == null || xs.isEmpty()) return;
    for (CourseModule m : xs) {
      removeModules(m);
    }
  }

  @DOpt(
      type=DOpt.Type.LinkRemover,
      requires="modules != null and x != null",
      effects="modules.remove(x); modulesCount = modules.size()"
  )
  @AttrRef("modules")
  public void removeModules(CourseModule x)
      throws ConstraintViolationException { // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    if (x == null) return;
    modules.remove(x);
    modulesCount = modules.size();
  }

  // ---- Link management: enrolments (association class) ----

  @DOpt(type=DOpt.Type.Getter, effects="return enrolments")
  @AttrRef("enrolments")
  public Collection<Enrolment> getEnrolments() {
    return enrolments;
  }

  @DOpt(type=DOpt.Type.LinkCountGetter, effects="return enrolmentsCount")
  @AttrRef("enrolmentsCount")
  public int getEnrolmentsCount() {
    return enrolmentsCount;
  }

  @DOpt(
      type=DOpt.Type.LinkAdder,
      requires="enrolments != null and xs != null and (enrolmentsCount + xs.size()) <= 30", // [INFERRED] mirrors 0..30
      effects="enrolments.addAll(xs); enrolmentsCount = enrolments.size()"
  )
  @AttrRef("enrolments")
  public void addEnrolments(Collection<Enrolment> xs) {
    if (xs == null || xs.isEmpty()) return;
    for (Enrolment e : xs) {
      addEnrolments(e);
    }
  }

  @DOpt(
      type=DOpt.Type.LinkAdder,
      requires="enrolments != null and x != null and enrolmentsCount < 30", // [INFERRED] mirrors 0..30
      effects="enrolments.add(x); enrolmentsCount = enrolments.size()"
  )
  @AttrRef("enrolments")
  public void addEnrolments(Enrolment x) {
    if (x == null) return;
    if (!enrolments.contains(x)) {
      enrolments.add(x);
      enrolmentsCount = enrolments.size();
    }
  }

  @DOpt(
      type=DOpt.Type.LinkRemover,
      requires="enrolments != null and xs != null",
      effects="enrolments.removeAll(xs); enrolmentsCount = enrolments.size()"
  )
  @AttrRef("enrolments")
  public void removeEnrolments(Collection<Enrolment> xs)
      throws ConstraintViolationException { // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    if (xs == null || xs.isEmpty()) return;
    for (Enrolment e : xs) {
      removeEnrolments(e);
    }
  }

  @DOpt(
      type=DOpt.Type.LinkRemover,
      requires="enrolments != null and x != null",
      effects="enrolments.remove(x); enrolmentsCount = enrolments.size()"
  )
  @AttrRef("enrolments")
  public void removeEnrolments(Enrolment x)
      throws ConstraintViolationException { // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    if (x == null) return;
    enrolments.remove(x);
    enrolmentsCount = enrolments.size();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Student)) return false;
    Student other = (Student) obj;
    return id == other.id;
  }

  @Override
  public String toString() {
    return "Student(" + id + ", " + name + ")";
  }
}

// ----------------------------------------

/* FILE: courseman/model/Address.java */

import java.util.Objects;

// [INFERRED] DCSL import base package assumed: domainapp.basics.model.meta
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Type;
import domainapp.basics.model.meta.AssocType;
import domainapp.basics.model.meta.AssocEndType;
import domainapp.basics.model.meta.Associate;

@DClass(mutable=true) // from Domain PUML: class Address
public class Address {

  /*** STATE SPACE ***/

  @DAttr(name="id", type=Type.Integer, id=true, auto=true, mutable=false, optional=false)
  private int id;
  private static int idCounter = 0;

  @DAttr(name="name", type=Type.String, optional=false, mutable=true, length=-1)
  private String name;

  // from Domain PUML: Student "1" -- "1" Address : lives-at
  // [INFERRED] reason: opposite end of the 1-1 association
  @DAttr(name="student", type=Type.Domain, optional=false)
  @DAssoc(
      ascName="lives-at",
      role="address",
      ascType=AssocType.One2One,
      endType=AssocEndType.One,
      associate=@Associate(type=Student.class, cardMin=1, cardMax=1)
  )
  private Student student;

  /*** BEHAVIOUR SPACE (partial) ***/

  @DOpt(
      type=DOpt.Type.ObjectFormConstructor,
      requires="name != null and student != null",
      effects="this.id = genId(); this.name = name; this.student = student"
  )
  public Address(
      @AttrRef("name") String name,
      @AttrRef("student") Student student
  ) {
    this.id = genId();
    this.name = name;
    this.student = student;
  }

  @DOpt(type=DOpt.Type.AutoAttributeValueGen, effects="return genId()")
  @AttrRef("id")
  private static int genId() {
    return ++idCounter;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return id")
  @AttrRef("id")
  public int getId() {
    return id;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return name")
  @AttrRef("name")
  public String getName() {
    return name;
  }

  @DOpt(type=DOpt.Type.Setter, requires="name != null", effects="this.name = name")
  @AttrRef("name")
  public void setName(String name) {
    this.name = name;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return student")
  @AttrRef("student")
  public Student getStudent() {
    return student;
  }

  @DOpt(type=DOpt.Type.Setter, requires="student != null", effects="this.student = student")
  @AttrRef("student")
  public void setStudent(Student student) {
    this.student = student;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Address)) return false;
    Address other = (Address) obj;
    return id == other.id;
  }

  @Override
  public String toString() {
    return "Address(" + id + ", " + name + ")";
  }
}

// ----------------------------------------

/* FILE: courseman/model/CourseModule.java */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

// [INFERRED] DCSL import base package assumed: domainapp.basics.model.meta
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Type;
import domainapp.basics.model.meta.AssocType;
import domainapp.basics.model.meta.AssocEndType;
import domainapp.basics.model.meta.Associate;

@DClass(mutable=true) // from Domain PUML: class CourseModule
public class CourseModule {

  /*** STATE SPACE ***/

  // from Domain PUML: CourseModule { - code : String }
  // [INFERRED] reason: "code" is typically a natural identifier and not auto-generated
  @DAttr(name="code", type=Type.String, id=true, auto=false, unique=true, mutable=false, optional=false, length=-1)
  private String code;

  @DAttr(name="name", type=Type.String, optional=false, mutable=true, length=-1)
  private String name;

  // [INFERRED] reason: semester is typically in a small positive range
  @DAttr(name="semester", type=Type.Integer, optional=false, mutable=true, min=1.0, max=3.0)
  private int semester;

  // [INFERRED] reason: credits are non-negative and typically small
  @DAttr(name="credits", type=Type.Integer, optional=false, mutable=true, min=0.0, max=60.0)
  private int credits;

  // from Domain PUML: Student "*" --> "0..30" CourseModule : enrols-in
  @DAttr(name="students", type=Type.Collection, optional=true)
  @DAssoc(
      ascName="enrols-in",
      role="module",
      ascType=AssocType.Many2Many,
      endType=AssocEndType.Many,
      associate=@Associate(type=Student.class, cardMin=0, cardMax=-1) // from PUML: "*" at Student end
  )
  private Collection<Student> students;

  private int studentsCount;

  // from Domain PUML: association class Enrolment attached to Student–CourseModule association
  @DAttr(name="enrolments", type=Type.Collection, optional=true)
  @DAssoc(
      ascName="enrolments",
      role="module",
      ascType=AssocType.One2Many,
      endType=AssocEndType.Many,
      associate=@Associate(type=Enrolment.class, cardMin=0, cardMax=-1)
  )
  private Collection<Enrolment> enrolments;

  private int enrolmentsCount;

  /*** BEHAVIOUR SPACE (partial) ***/

  @DOpt(
      type=DOpt.Type.ObjectFormConstructor,
      requires="code != null and name != null and semester >= 1 and semester <= 3 and credits >= 0 and credits <= 60", // [INFERRED] ranges
      effects="this.code = code; this.name = name; this.semester = semester; this.credits = credits; this.students = new ArrayList<>(); this.enrolments = new ArrayList<>()"
  )
  public CourseModule(
      @AttrRef("code") String code,
      @AttrRef("name") String name,
      @AttrRef("semester") int semester,
      @AttrRef("credits") int credits
  ) {
    this.code = code;
    this.name = name;
    this.semester = semester;
    this.credits = credits;

    this.students = new ArrayList<>();
    this.studentsCount = 0;

    this.enrolments = new ArrayList<>();
    this.enrolmentsCount = 0;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return code")
  @AttrRef("code")
  public String getCode() {
    return code;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return name")
  @AttrRef("name")
  public String getName() {
    return name;
  }

  @DOpt(type=DOpt.Type.Setter, requires="name != null", effects="this.name = name")
  @AttrRef("name")
  public void setName(String name) {
    this.name = name;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return semester")
  @AttrRef("semester")
  public int getSemester() {
    return semester;
  }

  @DOpt(
      type=DOpt.Type.Setter,
      requires="semester >= 1 and semester <= 3", // [INFERRED]
      effects="this.semester = semester"
  )
  @AttrRef("semester")
  public void setSemester(int semester) {
    this.semester = semester;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return credits")
  @AttrRef("credits")
  public int getCredits() {
    return credits;
  }

  @DOpt(
      type=DOpt.Type.Setter,
      requires="credits >= 0 and credits <= 60", // [INFERRED]
      effects="this.credits = credits"
  )
  @AttrRef("credits")
  public void setCredits(int credits) {
    this.credits = credits;
  }

  // ---- Link management: students ----

  @DOpt(type=DOpt.Type.Getter, effects="return students")
  @AttrRef("students")
  public Collection<Student> getStudents() {
    return students;
  }

  @DOpt(type=DOpt.Type.LinkCountGetter, effects="return studentsCount")
  @AttrRef("studentsCount")
  public int getStudentsCount() {
    return studentsCount;
  }

  @DOpt(
      type=DOpt.Type.LinkAdder,
      requires="students != null and xs != null",
      effects="students.addAll(xs); studentsCount = students.size()"
  )
  @AttrRef("students")
  public void addStudents(Collection<Student> xs) {
    if (xs == null || xs.isEmpty()) return;
    for (Student s : xs) {
      addStudents(s);
    }
  }

  @DOpt(
      type=DOpt.Type.LinkAdder,
      requires="students != null and x != null",
      effects="students.add(x); studentsCount = students.size()"
  )
  @AttrRef("students")
  public void addStudents(Student x) {
    if (x == null) return;
    if (!students.contains(x)) {
      students.add(x);
      studentsCount = students.size();
    }
  }

  @DOpt(
      type=DOpt.Type.LinkRemover,
      requires="students != null and xs != null",
      effects="students.removeAll(xs); studentsCount = students.size()"
  )
  @AttrRef("students")
  public void removeStudents(Collection<Student> xs)
      throws ConstraintViolationException { // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    if (xs == null || xs.isEmpty()) return;
    for (Student s : xs) {
      removeStudents(s);
    }
  }

  @DOpt(
      type=DOpt.Type.LinkRemover,
      requires="students != null and x != null",
      effects="students.remove(x); studentsCount = students.size()"
  )
  @AttrRef("students")
  public void removeStudents(Student x)
      throws ConstraintViolationException { // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    if (x == null) return;
    students.remove(x);
    studentsCount = students.size();
  }

  // ---- Link management: enrolments ----

  @DOpt(type=DOpt.Type.Getter, effects="return enrolments")
  @AttrRef("enrolments")
  public Collection<Enrolment> getEnrolments() {
    return enrolments;
  }

  @DOpt(type=DOpt.Type.LinkCountGetter, effects="return enrolmentsCount")
  @AttrRef("enrolmentsCount")
  public int getEnrolmentsCount() {
    return enrolmentsCount;
  }

  @DOpt(
      type=DOpt.Type.LinkAdder,
      requires="enrolments != null and xs != null",
      effects="enrolments.addAll(xs); enrolmentsCount = enrolments.size()"
  )
  @AttrRef("enrolments")
  public void addEnrolments(Collection<Enrolment> xs) {
    if (xs == null || xs.isEmpty()) return;
    for (Enrolment e : xs) {
      addEnrolments(e);
    }
  }

  @DOpt(
      type=DOpt.Type.LinkAdder,
      requires="enrolments != null and x != null",
      effects="enrolments.add(x); enrolmentsCount = enrolments.size()"
  )
  @AttrRef("enrolments")
  public void addEnrolments(Enrolment x) {
    if (x == null) return;
    if (!enrolments.contains(x)) {
      enrolments.add(x);
      enrolmentsCount = enrolments.size();
    }
  }

  @DOpt(
      type=DOpt.Type.LinkRemover,
      requires="enrolments != null and xs != null",
      effects="enrolments.removeAll(xs); enrolmentsCount = enrolments.size()"
  )
  @AttrRef("enrolments")
  public void removeEnrolments(Collection<Enrolment> xs)
      throws ConstraintViolationException { // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    if (xs == null || xs.isEmpty()) return;
    for (Enrolment e : xs) {
      removeEnrolments(e);
    }
  }

  @DOpt(
      type=DOpt.Type.LinkRemover,
      requires="enrolments != null and x != null",
      effects="enrolments.remove(x); enrolmentsCount = enrolments.size()"
  )
  @AttrRef("enrolments")
  public void removeEnrolments(Enrolment x)
      throws ConstraintViolationException { // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    // [DEFERRED/UNMAPPED] Missing type definition: ConstraintViolationException
    if (x == null) return;
    enrolments.remove(x);
    enrolmentsCount = enrolments.size();
  }

  @Override
  public int hashCode() {
    return Objects.hash(code);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof CourseModule)) return false;
    CourseModule other = (CourseModule) obj;
    return Objects.equals(code, other.code);
  }

  @Override
  public String toString() {
    return "CourseModule(" + code + ", " + name + ")";
  }
}

// ----------------------------------------

/* FILE: courseman/model/ElectiveModule.java */

import java.util.Objects;

// [INFERRED] DCSL import base package assumed: domainapp.basics.model.meta
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Type;

@DClass(mutable=true) // from Domain PUML: class ElectiveModule --|> CourseModule
public class ElectiveModule extends CourseModule {

  /*** STATE SPACE ***/

  @DAttr(name="deptName", type=Type.String, optional=false, mutable=true, length=-1)
  private String deptName;

  /*** BEHAVIOUR SPACE (partial) ***/

  @DOpt(
      type=DOpt.Type.ObjectFormConstructor,
      requires="code != null and name != null and semester >= 1 and semester <= 3 and credits >= 0 and credits <= 60 and deptName != null",
      effects="super(code,name,semester,credits); this.deptName = deptName"
  )
  public ElectiveModule(
      @AttrRef("code") String code,
      @AttrRef("name") String name,
      @AttrRef("semester") int semester,
      @AttrRef("credits") int credits,
      @AttrRef("deptName") String deptName
  ) {
    super(code, name, semester, credits);
    this.deptName = deptName;
  }

  @DOpt(type=DOpt.Type.Getter, effects="return deptName")
  @AttrRef("deptName")
  public String getDeptName() {
    return deptName;
  }

  @DOpt(type=DOpt.Type.Setter, requires="deptName != null", effects="this.deptName = deptName")
  @AttrRef("deptName")
  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  @Override
  public String toString() {
    return "ElectiveModule(" + getCode() + ", " + getName() + ", dept=" + deptName + ")";
  }
}

// ----------------------------------------

/* FILE: courseman/model/CompulsoryModule.java */

// [INFERRED] DCSL import base package assumed: domainapp.basics.model.meta
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;

@DClass(mutable=true) // from Domain PUML: class CompulsoryModule --|> CourseModule
public class CompulsoryModule extends CourseModule {

  /*** STATE SPACE ***/
  // from Domain PUML: CompulsoryModule has no additional attributes

  /*** BEHAVIOUR SPACE (partial) ***/

  @DOpt(
      type=DOpt.Type.ObjectFormConstructor,
      requires="code != null and name != null and semester >= 1 and semester <= 3 and credits >= 0 and credits <= 60",
      effects="super(code,name,semester,credits)"
  )
  public CompulsoryModule(
      @AttrRef("code") String code,
      @AttrRef("name") String name,
      @AttrRef("semester") int semester,
      @AttrRef("credits") int credits
  ) {
    super(code, name, semester, credits);
  }

  @Override
  public String toString() {
    return "CompulsoryModule(" + getCode() + ", " + getName() + ")";
  }
}