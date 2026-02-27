import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

// [TRACE] Source: /mnt/data/PUML_coursema.puml
// [NOTE] DCSL annotation parameter sets are constrained by /mnt/data/DCSL_PUML.puml (meta-model).
// [DEFERRED/UNMAPPED] The meta-model's DAttr does NOT define common parameters such as (name, type).
//   -> We therefore encode attribute names/types via Java field names/types, and only use allowed DAttr params:
//      (mutable, optional, length, unique, id, auto, min, max).

// =====================================================
// Address
// =====================================================

@DClass(mutable=true) // [TRACE] PUML_coursema.puml: class Address
class Address {

  /*** STATE SPACE ***/

  @DAttr(mutable=false, optional=false, unique=true, id=true, auto=true) // [TRACE] Address.id : int
  private int id;
  // [TRACE] PUML_coursema.puml: Address has attribute id:int
  // [INFERRED] id is treated as identifier + auto-generated (common CourseMan-style pattern).

  @DAttr(mutable=true, optional=false, length=-1, unique=false) // [TRACE] Address.name : String
  private String name;

  // --- Association: Student lives-at Address (1..1) ---
  // [TRACE] PUML_coursema.puml: Student "1" -- "1" Address : lives-at
  // [INFERRED] Bidirectional representation: Address.resident mirrors Student.address.

  @DAttr(mutable=true, optional=false) // [TRACE] Address end multiplicity "1"
  @DAssoc(
      ascName="lives-at", // [TRACE] association label
      role="resident",    // [INFERRED] role name on Address end
      ascType=AssocType.OneOne,
      endType=AssocEndType.One,
      associate=@Associate(type=Student.class, cardMin=1, cardMax=1)
  )
  private Student resident;

  /*** BEHAVIOUR SPACE (partial) ***/

  // Auto id generator (Address)
  private static int idCount = 0; // [TRACE] supports auto id for Address.id (inferred)

  @DOpt(type=OptType.AutoAttributeValueGen, requires="true", effects="id := genId()")
  @AttrRef("id")
  private static int genId() {
    return ++idCount;
  }

  @DOpt(type=OptType.ObjectFormConstructor, requires="name != null", effects="this.id := genId(); this.name := name")
  Address(@AttrRef("name") String name) {
    this.id = genId();
    this.name = name;
  }

  // getters/setters
  @DOpt(type=OptType.Getter, requires="true", effects="result = id")
  @AttrRef("id")
  public int getId() {
    return id;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = name")
  @AttrRef("name")
  public String getName() {
    return name;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] name != null", effects="name := p_name")
  public void setName(@AttrRef("name") String p_name) {
    this.name = p_name;
  }

  // association end accessors
  @DOpt(type=OptType.Getter, requires="true", effects="result = resident")
  @AttrRef("resident")
  public Student getResident() {
    return resident;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] resident != null", effects="resident := p_resident")
  public void setResident(@AttrRef("resident") Student p_resident) {
    this.resident = p_resident;
  }
}

// =====================================================
// Student
// =====================================================

@DClass(mutable=true) // [TRACE] PUML_coursema.puml: class Student
class Student {

  /*** STATE SPACE ***/

  @DAttr(mutable=false, optional=false, unique=true, id=true, auto=true) // [TRACE] Student.id : int
  private int id;
  // [INFERRED] id treated as identifier + auto.

  @DAttr(mutable=true, optional=false, length=-1, unique=false) // [TRACE] Student.name : String
  private String name;

  // --- Association: Student lives at Address (1..1) ---
  // [TRACE] PUML_coursema.puml: Student "1" -- "1" Address : lives-at

  @DAttr(mutable=true, optional=false) // [TRACE] Student end multiplicity "1"
  @DAssoc(
      ascName="lives-at", // [TRACE] association label
      role="student",     // [INFERRED] role name on Student end
      ascType=AssocType.OneOne,
      endType=AssocEndType.One,
      associate=@Associate(type=Address.class, cardMin=1, cardMax=1)
  )
  private Address address;

  // --- Association Class Link: (Student, CourseModule) .. Enrolment : enrolments ---
  // [TRACE] PUML_coursema.puml: (Student, CourseModule) .. Enrolment : enrolments
  // [TRACE] PUML_coursema.puml: Student "*" --> "0..30" CourseModule : enrols-in (captured via Enrolment)
  // [INFERRED] We realise the Student–CourseModule link using association class Enrolment.

  @DAttr(mutable=true, optional=true) // [TRACE] Student enrolments is 0..30 (captured in @Associate)
  @DAssoc(
      ascName="enrolments",
      role="student",
      ascType=AssocType.OneMany,     // [DEFERRED/UNMAPPED] meta-model lacks ManyOne/ManyMany per-end forms here.
      endType=AssocEndType.Many,
      associate=@Associate(type=Enrolment.class, cardMin=0, cardMax=30)
  )
  private Collection<Enrolment> enrolments;

  // [TRACE] PUML_coursema.puml: Student "0..30" CourseModule (max=30)
  // Counter needed for upper-bound constraint support in link add/remove
  private int enrolmentsCount; // [TRACE] derived from association upper bound 30

  /*** BEHAVIOUR SPACE (partial) ***/

  // Auto id generator (Student)
  private static int idCount = 0; // [TRACE] supports auto id for Student.id (inferred)

  @DOpt(type=OptType.AutoAttributeValueGen, requires="true", effects="id := genId()")
  @AttrRef("id")
  private static int genId() {
    return ++idCount;
  }

  @DOpt(
      type=OptType.ObjectFormConstructor,
      requires="name != null AND address != null",
      effects="this.id := genId(); this.name := name; this.address := address; this.enrolments := {}; this.enrolmentsCount := 0"
  )
  Student(@AttrRef("name") String name, @AttrRef("address") Address address) {
    this.id = genId();
    this.name = name;
    this.address = address;

    this.enrolments = new ArrayList<>();
    this.enrolmentsCount = 0;

    // [INFERRED] Maintain bidirectional 1-1 with Address
    address.setResident(this);
  }

  // Attribute accessors
  @DOpt(type=OptType.Getter, requires="true", effects="result = id")
  @AttrRef("id")
  public int getId() {
    return id;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = name")
  @AttrRef("name")
  public String getName() {
    return name;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] p_name != null", effects="name := p_name")
  public void setName(@AttrRef("name") String p_name) {
    this.name = p_name;
  }

  // lives-at
  @DOpt(type=OptType.Getter, requires="true", effects="result = address")
  @AttrRef("address")
  public Address getAddress() {
    return address;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] p_address != null", effects="address := p_address")
  public void setAddress(@AttrRef("address") Address p_address) {
    this.address = p_address;
    // [INFERRED] keep bidirectional
    p_address.setResident(this);
  }

  // enrolments link count
  @DOpt(type=OptType.LinkCountGetter, requires="true", effects="result = enrolmentsCount")
  @AttrRef("enrolmentsCount")
  public int getEnrolmentsCount() {
    return enrolmentsCount;
  }

  @DOpt(type=OptType.LinkCountSetter, requires="p_count >= 0 AND p_count <= 30", effects="enrolmentsCount := p_count")
  public void setEnrolmentsCount(@AttrRef("enrolmentsCount") int p_count) {
    this.enrolmentsCount = p_count;
  }

  // enrolments getters
  @DOpt(type=OptType.Getter, requires="true", effects="result = enrolments")
  @AttrRef("enrolments")
  public Collection<Enrolment> getEnrolments() {
    return enrolments == null ? Collections.emptyList() : Collections.unmodifiableCollection(enrolments);
  }

  // Link adders/removers
  @DOpt(
      type=OptType.LinkAdder,
      requires="[INFERRED] xs != null AND enrolmentsCount + |xs| <= 30",
      effects="enrolments := enrolments ∪ xs; enrolmentsCount := enrolmentsCount + |xs|"
  )
  @AttrRef("enrolments")
  public void addEnrolments(Collection<Enrolment> xs) {
    if (xs == null || xs.isEmpty()) return;
    if (enrolmentsCount + xs.size() > 30) {
      // [DEFERRED/UNMAPPED] ConstraintViolationException type/package not specified in PUML/meta-model
      throw new ConstraintViolationException("[INFERRED] Max enrolments exceeded (30).");
    }
    if (this.enrolments == null) this.enrolments = new ArrayList<>();
    this.enrolments.addAll(xs);
    this.enrolmentsCount += xs.size();
  }

  @DOpt(
      type=OptType.LinkAdder,
      requires="[INFERRED] x != null AND enrolmentsCount + 1 <= 30",
      effects="enrolments := enrolments ∪ {x}; enrolmentsCount := enrolmentsCount + 1"
  )
  @AttrRef("enrolments")
  public void addEnrolments(Enrolment x) {
    if (x == null) return;
    if (enrolmentsCount + 1 > 30) {
      throw new ConstraintViolationException("[INFERRED] Max enrolments exceeded (30).");
    }
    if (this.enrolments == null) this.enrolments = new ArrayList<>();
    this.enrolments.add(x);
    this.enrolmentsCount++;
  }

  @DOpt(
      type=OptType.LinkRemover,
      requires="[INFERRED] xs != null",
      effects="enrolments := enrolments \\ xs; enrolmentsCount := enrolmentsCount - |xs|"
  )
  @AttrRef("enrolments")
  public void removeEnrolments(Collection<Enrolment> xs) throws ConstraintViolationException {
    if (xs == null || xs.isEmpty() || this.enrolments == null) return;
    this.enrolments.removeAll(xs);
    this.enrolmentsCount = Math.max(0, this.enrolmentsCount - xs.size());
  }

  @DOpt(
      type=OptType.LinkRemover,
      requires="[INFERRED] x != null",
      effects="enrolments := enrolments \\ {x}; enrolmentsCount := enrolmentsCount - 1"
  )
  @AttrRef("enrolments")
  public void removeEnrolments(Enrolment x) throws ConstraintViolationException {
    if (x == null || this.enrolments == null) return;
    boolean removed = this.enrolments.remove(x);
    if (removed) this.enrolmentsCount = Math.max(0, this.enrolmentsCount - 1);
  }
}

// =====================================================
// CourseModule (superclass)
// =====================================================

@DClass(mutable=true) // [TRACE] PUML_coursema.puml: class CourseModule
class CourseModule {

  /*** STATE SPACE ***/

  @DAttr(mutable=false, optional=false, unique=true, id=true, auto=false) // [TRACE] CourseModule.code : String
  private String code;
  // [INFERRED] code treated as identifier (common module code pattern).

  @DAttr(mutable=true, optional=false, length=-1, unique=false) // [TRACE] CourseModule.name : String
  private String name;

  @DAttr(mutable=true, optional=false, min=0, max=+Double.POSITIVE_INFINITY) // [TRACE] CourseModule.semester : int
  private int semester;

  @DAttr(mutable=true, optional=false, min=0, max=+Double.POSITIVE_INFINITY) // [TRACE] CourseModule.credits : int
  private int credits;

  // --- Association Class Link: enrolments (CourseModule side) ---
  // [TRACE] PUML_coursema.puml: (Student, CourseModule) .. Enrolment : enrolments

  @DAttr(mutable=true, optional=true)
  @DAssoc(
      ascName="enrolments",
      role="module",
      ascType=AssocType.OneMany,  // [DEFERRED/UNMAPPED] meta-model lacks explicit ManyMany end form for association-class modelling.
      endType=AssocEndType.Many,
      associate=@Associate(type=Enrolment.class, cardMin=0, cardMax=-1) // * -> -1 (allowed by Associate.cardMax:int)
  )
  private Collection<Enrolment> enrolments;

  private int enrolmentsCount; // [TRACE] supports link ops (no upper bound in PUML)

  /*** BEHAVIOUR SPACE (partial) ***/

  @DOpt(
      type=OptType.ObjectFormConstructor,
      requires="code != null AND name != null",
      effects="this.code := code; this.name := name; this.semester := semester; this.credits := credits; this.enrolments := {}; this.enrolmentsCount := 0"
  )
  CourseModule(
      @AttrRef("code") String code,
      @AttrRef("name") String name,
      @AttrRef("semester") int semester,
      @AttrRef("credits") int credits
  ) {
    this.code = code;
    this.name = name;
    this.semester = semester;
    this.credits = credits;
    this.enrolments = new ArrayList<>();
    this.enrolmentsCount = 0;
  }

  // getters/setters
  @DOpt(type=OptType.Getter, requires="true", effects="result = code")
  @AttrRef("code")
  public String getCode() {
    return code;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = name")
  @AttrRef("name")
  public String getName() {
    return name;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] p_name != null", effects="name := p_name")
  public void setName(@AttrRef("name") String p_name) {
    this.name = p_name;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = semester")
  @AttrRef("semester")
  public int getSemester() {
    return semester;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] p_semester > 0", effects="semester := p_semester")
  public void setSemester(@AttrRef("semester") int p_semester) {
    this.semester = p_semester;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = credits")
  @AttrRef("credits")
  public int getCredits() {
    return credits;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] p_credits >= 0", effects="credits := p_credits")
  public void setCredits(@AttrRef("credits") int p_credits) {
    this.credits = p_credits;
  }

  // enrolments link count
  @DOpt(type=OptType.LinkCountGetter, requires="true", effects="result = enrolmentsCount")
  @AttrRef("enrolmentsCount")
  public int getEnrolmentsCount() {
    return enrolmentsCount;
  }

  @DOpt(type=OptType.LinkCountSetter, requires="[INFERRED] p_count >= 0", effects="enrolmentsCount := p_count")
  public void setEnrolmentsCount(@AttrRef("enrolmentsCount") int p_count) {
    this.enrolmentsCount = p_count;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = enrolments")
  @AttrRef("enrolments")
  public Collection<Enrolment> getEnrolments() {
    return enrolments == null ? Collections.emptyList() : Collections.unmodifiableCollection(enrolments);
  }

  @DOpt(type=OptType.LinkAdder, requires="[INFERRED] xs != null", effects="enrolments := enrolments ∪ xs; enrolmentsCount := enrolmentsCount + |xs|")
  @AttrRef("enrolments")
  public void addEnrolments(Collection<Enrolment> xs) {
    if (xs == null || xs.isEmpty()) return;
    if (this.enrolments == null) this.enrolments = new ArrayList<>();
    this.enrolments.addAll(xs);
    this.enrolmentsCount += xs.size();
  }

  @DOpt(type=OptType.LinkAdder, requires="[INFERRED] x != null", effects="enrolments := enrolments ∪ {x}; enrolmentsCount := enrolmentsCount + 1")
  @AttrRef("enrolments")
  public void addEnrolments(Enrolment x) {
    if (x == null) return;
    if (this.enrolments == null) this.enrolments = new ArrayList<>();
    this.enrolments.add(x);
    this.enrolmentsCount++;
  }

  @DOpt(type=OptType.LinkRemover, requires="[INFERRED] xs != null", effects="enrolments := enrolments \\ xs; enrolmentsCount := enrolmentsCount - |xs|")
  @AttrRef("enrolments")
  public void removeEnrolments(Collection<Enrolment> xs) throws ConstraintViolationException {
    if (xs == null || xs.isEmpty() || this.enrolments == null) return;
    this.enrolments.removeAll(xs);
    this.enrolmentsCount = Math.max(0, this.enrolmentsCount - xs.size());
  }

  @DOpt(type=OptType.LinkRemover, requires="[INFERRED] x != null", effects="enrolments := enrolments \\ {x}; enrolmentsCount := enrolmentsCount - 1")
  @AttrRef("enrolments")
  public void removeEnrolments(Enrolment x) throws ConstraintViolationException {
    if (x == null || this.enrolments == null) return;
    boolean removed = this.enrolments.remove(x);
    if (removed) this.enrolmentsCount = Math.max(0, this.enrolmentsCount - 1);
  }
}

// =====================================================
// ElectiveModule (subclass)
// =====================================================

@DClass(mutable=true) // [TRACE] PUML_coursema.puml: class ElectiveModule; inheritance ElectiveModule --|> CourseModule
class ElectiveModule extends CourseModule {

  /*** STATE SPACE ***/

  @DAttr(mutable=true, optional=false, length=-1, unique=false) // [TRACE] ElectiveModule.deptName : String
  private String deptName;

  /*** BEHAVIOUR SPACE (partial) ***/

  @DOpt(
      type=OptType.ObjectFormConstructor,
      requires="code != null AND name != null AND deptName != null",
      effects="super(code,name,semester,credits); this.deptName := deptName"
  )
  ElectiveModule(
      @AttrRef("code") String code,
      @AttrRef("name") String name,
      @AttrRef("semester") int semester,
      @AttrRef("credits") int credits,
      @AttrRef("deptName") String deptName
  ) {
    super(code, name, semester, credits);
    this.deptName = deptName;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = deptName")
  @AttrRef("deptName")
  public String getDeptName() {
    return deptName;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] p_deptName != null", effects="deptName := p_deptName")
  public void setDeptName(@AttrRef("deptName") String p_deptName) {
    this.deptName = p_deptName;
  }
}

// =====================================================
// CompulsoryModule (subclass)
// =====================================================

@DClass(mutable=true) // [TRACE] PUML_coursema.puml: class CompulsoryModule; inheritance CompulsoryModule --|> CourseModule
class CompulsoryModule extends CourseModule {

  /*** STATE SPACE ***/
  // [TRACE] PUML_coursema.puml: CompulsoryModule has no declared attributes

  /*** BEHAVIOUR SPACE (partial) ***/

  @DOpt(
      type=OptType.ObjectFormConstructor,
      requires="code != null AND name != null",
      effects="super(code,name,semester,credits)"
  )
  CompulsoryModule(
      @AttrRef("code") String code,
      @AttrRef("name") String name,
      @AttrRef("semester") int semester,
      @AttrRef("credits") int credits
  ) {
    super(code, name, semester, credits);
  }
}

// =====================================================
// Enrolment (association class)
// =====================================================

@DClass(mutable=true) // [TRACE] PUML_coursema.puml: class Enrolment (association class)
class Enrolment {

  /*** STATE SPACE ***/

  @DAttr(mutable=false, optional=false, unique=true, id=true, auto=true) // [TRACE] Enrolment.id : int
  private int id;
  // [INFERRED] id treated as identifier + auto.

  @DAttr(mutable=true, optional=false) // [TRACE] Enrolment.internalMark : Double
  private Double internalMark;

  @DAttr(mutable=true, optional=false) // [TRACE] Enrolment.examMark : Double
  private Double examMark;

  @DAttr(mutable=true, optional=false) // [TRACE] Enrolment.finalGrade : char
  private char finalGrade;

  // Link ends for association class (Student <-> CourseModule)
  // [TRACE] PUML_coursema.puml: (Student, CourseModule) .. Enrolment : enrolments

  @DAttr(mutable=true, optional=false)
  @DAssoc(
      ascName="enrolments",
      role="student",
      ascType=AssocType.OneMany, // [DEFERRED/UNMAPPED] Using OneMany to model link-to-one end due to meta-model limits
      endType=AssocEndType.One,
      associate=@Associate(type=Student.class, cardMin=1, cardMax=1)
  )
  private Student student;

  @DAttr(mutable=true, optional=false)
  @DAssoc(
      ascName="enrolments",
      role="module",
      ascType=AssocType.OneMany, // [DEFERRED/UNMAPPED] Using OneMany to model link-to-one end due to meta-model limits
      endType=AssocEndType.One,
      associate=@Associate(type=CourseModule.class, cardMin=1, cardMax=1)
  )
  private CourseModule module;

  /*** BEHAVIOUR SPACE (partial) ***/

  // Auto id generator (Enrolment)
  private static int idCount = 0; // [TRACE] supports auto id for Enrolment.id (inferred)

  @DOpt(type=OptType.AutoAttributeValueGen, requires="true", effects="id := genId()")
  @AttrRef("id")
  private static int genId() {
    return ++idCount;
  }

  @DOpt(
      type=OptType.ObjectFormConstructor,
      requires="student != null AND module != null AND internalMark != null AND examMark != null",
      effects="this.id := genId(); this.student := student; this.module := module; this.internalMark := internalMark; this.examMark := examMark; this.finalGrade := finalGrade"
  )
  Enrolment(
      @AttrRef("student") Student student,
      @AttrRef("module") CourseModule module,
      @AttrRef("internalMark") Double internalMark,
      @AttrRef("examMark") Double examMark,
      @AttrRef("finalGrade") char finalGrade
  ) {
    this.id = genId();
    this.student = student;
    this.module = module;
    this.internalMark = internalMark;
    this.examMark = examMark;
    this.finalGrade = finalGrade;

    // [INFERRED] Maintain bidirectional links via collections
    student.addEnrolments(this);
    module.addEnrolments(this);
  }

  // getters/setters
  @DOpt(type=OptType.Getter, requires="true", effects="result = id")
  @AttrRef("id")
  public int getId() {
    return id;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = internalMark")
  @AttrRef("internalMark")
  public Double getInternalMark() {
    return internalMark;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] p_internalMark != null", effects="internalMark := p_internalMark")
  public void setInternalMark(@AttrRef("internalMark") Double p_internalMark) {
    this.internalMark = p_internalMark;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = examMark")
  @AttrRef("examMark")
  public Double getExamMark() {
    return examMark;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] p_examMark != null", effects="examMark := p_examMark")
  public void setExamMark(@AttrRef("examMark") Double p_examMark) {
    this.examMark = p_examMark;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = finalGrade")
  @AttrRef("finalGrade")
  public char getFinalGrade() {
    return finalGrade;
  }

  @DOpt(type=OptType.Setter, requires="true", effects="finalGrade := p_finalGrade")
  public void setFinalGrade(@AttrRef("finalGrade") char p_finalGrade) {
    this.finalGrade = p_finalGrade;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = student")
  @AttrRef("student")
  public Student getStudent() {
    return student;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] p_student != null", effects="student := p_student")
  public void setStudent(@AttrRef("student") Student p_student) {
    this.student = p_student;
  }

  @DOpt(type=OptType.Getter, requires="true", effects="result = module")
  @AttrRef("module")
  public CourseModule getModule() {
    return module;
  }

  @DOpt(type=OptType.Setter, requires="[INFERRED] p_module != null", effects="module := p_module")
  public void setModule(@AttrRef("module") CourseModule p_module) {
    this.module = p_module;
  }
}

// =====================================================
// [DEFERRED/UNMAPPED] Runtime exception placeholder
// =====================================================
// The PUML inputs do not define the Java package/type for ConstraintViolationException.
// We include a minimal placeholder so the generated code is self-contained.
class ConstraintViolationException extends RuntimeException {
  public ConstraintViolationException(String msg) {
    super(msg);
  }
}