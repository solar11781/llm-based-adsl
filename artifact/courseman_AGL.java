/* FILE: agl/generated/CoursemanActivityGraph.java */

import java.util.List;
import java.util.ArrayList;

// [INFERRED] AGL import base package assumed: agl.model
import agl.model.ActivityGraph;
import agl.model.Node;
import agl.model.Edge;
import agl.model.ControlNode;
import agl.model.MergeNode;
import agl.model.ForkNode;
import agl.model.CoordinatorNode;
import agl.model.DecisionNode;
import agl.model.JoinNode;
import agl.model.ModuleAct;
import agl.model.State;
import agl.model.Module;
import agl.model.ModuleService;
import agl.model.DomainClass;

/**
 * [INFERRED] reason: No explicit ActivityGraph instance model was provided
 * in the Domain PUML or requirements document. A minimal valid activity graph
 * satisfying the AGL meta-model multiplicities is generated.
 */
public class CoursemanActivityGraph {

  /* =========================================================
     Graph core
     ========================================================= */

  private ActivityGraph graph;

  /* =========================================================
     Nodes (minimum >= 2 required by meta-model)
     ========================================================= */

  private Node startNode;
  private Node studentNode;
  private Node moduleNode;
  private Node enrolmentNode;

  private DecisionNode decisionNode;
  private MergeNode mergeNode;

  /* =========================================================
     Edges
     ========================================================= */

  private Edge e1;
  private Edge e2;
  private Edge e3;
  private Edge e4;
  private Edge e5;

  /* =========================================================
     Graph collections
     ========================================================= */

  private List<Node> nodes;
  private List<Edge> edges;

  /* =========================================================
     Module Actions
     ========================================================= */

  private ModuleAct createEnrolmentAct;

  /* =========================================================
     Domain bindings
     ========================================================= */

  private DomainClass studentDomain;
  private DomainClass moduleDomain;
  private DomainClass enrolmentDomain;

  /* =========================================================
     Constructor
     ========================================================= */

  public CoursemanActivityGraph() {
    initDomainBindings();
    initNodes();
    initActions();
    initEdges();
    initGraph();
  }

  /* =========================================================
     Domain bindings
     ========================================================= */

  private void initDomainBindings() {

    // [INFERRED] DomainClass bindings derived from domain PUML classes
    studentDomain = new DomainClass("Student");
    moduleDomain = new DomainClass("CourseModule");
    enrolmentDomain = new DomainClass("Enrolment");

  }

  /* =========================================================
     Nodes
     ========================================================= */

  private void initNodes() {

    startNode = new Node("Start");

    studentNode = new Node("Student");
    studentNode.setRefCls(studentDomain);

    moduleNode = new Node("CourseModule");
    moduleNode.setRefCls(moduleDomain);

    enrolmentNode = new Node("Enrolment");
    enrolmentNode.setRefCls(enrolmentDomain);

    decisionNode = new DecisionNode("CheckModuleSelection");

    mergeNode = new MergeNode("MergeFlow");

    nodes = new ArrayList<>();
    nodes.add(startNode);
    nodes.add(studentNode);
    nodes.add(moduleNode);
    nodes.add(enrolmentNode);
    nodes.add(decisionNode);
    nodes.add(mergeNode);

  }

  /* =========================================================
     Module Actions
     ========================================================= */

  private void initActions() {

    createEnrolmentAct = new ModuleAct();

    List<State> preStates = new ArrayList<>();
    preStates.add(State.Init);

    List<State> postStates = new ArrayList<>();
    postStates.add(State.Created);

    createEnrolmentAct.setPreStates(preStates);
    createEnrolmentAct.setPostStates(postStates);

    enrolmentNode.setActSeq(createEnrolmentAct);

  }

  /* =========================================================
     Edges
     ========================================================= */

  private void initEdges() {

    e1 = new Edge(startNode, studentNode);
    e2 = new Edge(studentNode, moduleNode);
    e3 = new Edge(moduleNode, decisionNode);
    e4 = new Edge(decisionNode, enrolmentNode);
    e5 = new Edge(enrolmentNode, mergeNode);

    edges = new ArrayList<>();
    edges.add(e1);
    edges.add(e2);
    edges.add(e3);
    edges.add(e4);
    edges.add(e5);

  }

  /* =========================================================
     Graph initialization
     ========================================================= */

  private void initGraph() {

    graph = new ActivityGraph();

    graph.setNodes(nodes);
    graph.setEdges(edges);

    validateGraph();

  }

  /* =========================================================
     Validation logic
     ========================================================= */

  private void validateGraph() {

    // Constraint from PUML:
    // ActivityGraph must have >= 2 nodes

    if (nodes == null || nodes.size() < 2) {
      throw new IllegalStateException("ActivityGraph must contain at least two nodes");
    }

    // Node.label must not be null

    for (Node n : nodes) {
      if (n.getLabel() == null) {
        throw new IllegalStateException("Node label cannot be null");
      }
    }

  }

  /* =========================================================
     Accessors
     ========================================================= */

  public ActivityGraph getGraph() {
    return graph;
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public List<Edge> getEdges() {
    return edges;
  }

}

// ----------------------------------------

/* FILE: agl/generated/StateEnum.java */

import agl.model.State;

/**
 * State enum reproduced from AGL meta-model PUML.
 */
public enum StateEnum {

  Init,
  Opened,
  Created,
  NewObject,
  Updated,
  Reset,
  Cancelled,
  Editing,
  Deleted

}