/* FILE: modules/student/StudentModuleConfig.java */

import mccl.ModuleConfig;
import mccl.ModelConfig;
import mccl.ControllerConfig;
import mccl.ViewConfig;
import mccl.ScopeConfig;
import mccl.PropertySet;
import mccl.Tree;
import mccl.ModuleType;
import mccl.LAName;
import mccl.OpenPolicy;

// [INFERRED] MCCL import base package assumed: mccl

public class StudentModuleConfig extends ModuleConfig {

  private ModelConfig modelCfg;
  private ControllerConfig controllerCfg;
  private ViewConfig viewCfg;
  private ScopeConfig scopeCfg;

  public StudentModuleConfig() {
    this.name = "StudentModule";
    this.type = ModuleType.DomainData; // [INFERRED] typical MCCL module type for domain modules

    // Model configuration
    modelCfg = new ModelConfig();
    modelCfg.domainClass = Student.class; // [DEFERRED/UNMAPPED] Missing type definition: Student
    modelCfg.editable = true; // [INFERRED] default editable=true

    // Controller configuration
    controllerCfg = new ControllerConfig();
    controllerCfg.ctrlClass = StudentController.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: StudentController
    controllerCfg.dataCtrl = StudentDataController.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: StudentDataController
    controllerCfg.defaultCommand = LAName.Open;
    controllerCfg.openPolicy = OpenPolicy.I;

    // View configuration
    viewCfg = new ViewConfig();
    viewCfg.topX = 0;
    viewCfg.topY = 0;

    this.model = modelCfg;
    this.ctl = controllerCfg;
    this.view = viewCfg;
  }
}

// ----------------------------------------

/* FILE: modules/address/AddressModuleConfig.java */

import mccl.ModuleConfig;
import mccl.ModelConfig;
import mccl.ControllerConfig;
import mccl.ViewConfig;
import mccl.ScopeConfig;
import mccl.ModuleType;
import mccl.LAName;
import mccl.OpenPolicy;

// [INFERRED] MCCL import base package assumed: mccl

public class AddressModuleConfig extends ModuleConfig {

  private ModelConfig modelCfg;
  private ControllerConfig controllerCfg;
  private ViewConfig viewCfg;

  public AddressModuleConfig() {
    this.name = "AddressModule";
    this.type = ModuleType.DomainData; // [INFERRED]

    // Model configuration
    modelCfg = new ModelConfig();
    modelCfg.domainClass = Address.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: Address
    modelCfg.editable = true; // [INFERRED]

    // Controller configuration
    controllerCfg = new ControllerConfig();
    controllerCfg.ctrlClass = AddressController.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: AddressController
    controllerCfg.dataCtrl = AddressDataController.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: AddressDataController
    controllerCfg.defaultCommand = LAName.Open;
    controllerCfg.openPolicy = OpenPolicy.I;

    // View configuration
    viewCfg = new ViewConfig();
    viewCfg.topX = 0;
    viewCfg.topY = 0;

    this.model = modelCfg;
    this.ctl = controllerCfg;
    this.view = viewCfg;
  }
}

// ----------------------------------------

/* FILE: modules/coursemodule/CourseModuleModuleConfig.java */

import mccl.ModuleConfig;
import mccl.ModelConfig;
import mccl.ControllerConfig;
import mccl.ViewConfig;
import mccl.ModuleType;
import mccl.LAName;
import mccl.OpenPolicy;

// [INFERRED] MCCL import base package assumed: mccl

public class CourseModuleModuleConfig extends ModuleConfig {

  private ModelConfig modelCfg;
  private ControllerConfig controllerCfg;
  private ViewConfig viewCfg;

  public CourseModuleModuleConfig() {
    this.name = "CourseModuleModule";
    this.type = ModuleType.DomainData; // [INFERRED]

    // Model configuration
    modelCfg = new ModelConfig();
    modelCfg.domainClass = CourseModule.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: CourseModule
    modelCfg.editable = true; // [INFERRED]

    // Controller configuration
    controllerCfg = new ControllerConfig();
    controllerCfg.ctrlClass = CourseModuleController.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: CourseModuleController
    controllerCfg.dataCtrl = CourseModuleDataController.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: CourseModuleDataController
    controllerCfg.defaultCommand = LAName.Open;
    controllerCfg.openPolicy = OpenPolicy.I;

    // View configuration
    viewCfg = new ViewConfig();
    viewCfg.topX = 0;
    viewCfg.topY = 0;

    this.model = modelCfg;
    this.ctl = controllerCfg;
    this.view = viewCfg;
  }
}

// ----------------------------------------

/* FILE: modules/enrolment/EnrolmentModuleConfig.java */

import mccl.ModuleConfig;
import mccl.ModelConfig;
import mccl.ControllerConfig;
import mccl.ViewConfig;
import mccl.ModuleType;
import mccl.LAName;
import mccl.OpenPolicy;

// [INFERRED] MCCL import base package assumed: mccl

public class EnrolmentModuleConfig extends ModuleConfig {

  private ModelConfig modelCfg;
  private ControllerConfig controllerCfg;
  private ViewConfig viewCfg;

  public EnrolmentModuleConfig() {
    this.name = "EnrolmentModule";
    this.type = ModuleType.DomainData; // [INFERRED]

    // Model configuration
    modelCfg = new ModelConfig();
    modelCfg.domainClass = Enrolment.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: Enrolment
    modelCfg.editable = true; // [INFERRED]

    // Controller configuration
    controllerCfg = new ControllerConfig();
    controllerCfg.ctrlClass = EnrolmentController.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: EnrolmentController
    controllerCfg.dataCtrl = EnrolmentDataController.class; 
    // [DEFERRED/UNMAPPED] Missing type definition: EnrolmentDataController
    controllerCfg.defaultCommand = LAName.Open;
    controllerCfg.openPolicy = OpenPolicy.I;

    // View configuration
    viewCfg = new ViewConfig();
    viewCfg.topX = 0;
    viewCfg.topY = 0;

    this.model = modelCfg;
    this.ctl = controllerCfg;
    this.view = viewCfg;
  }
}