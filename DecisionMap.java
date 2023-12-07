import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DecisionMap {
  private Map<Decision, Integer> decisionMap;

  public DecisionMap() {
    this.decisionMap = new HashMap<>();
  }

  public Map<Decision, Integer> getDecisionMap() {
    return decisionMap;
  }

  public void insert(Decision decisao) {
    if (this.decisionMap.containsKey(decisao)) {
      this.decisionMap.put(decisao, this.decisionMap.get(decisao) + 1);
    } else {
      this.decisionMap.put(decisao, 1);
    }
  }

  public boolean verifyCorum() {
    // System.out.println(this.decisionMap);
    for (Decision decision : this.decisionMap.keySet()) {
      if (this.decisionMap.get(decision) > ClusterSimulation.TOTAL_NODES / 2) {
        return true;
      }
    }

    return false;
  }

  public Decision corum() {
    int max = Integer.MIN_VALUE;
    Decision result = null;
    for (Entry<Decision, Integer> entry : this.decisionMap.entrySet()) {
      if(entry.getValue() > max) {
        max = entry.getValue();
        result = entry.getKey();
      }
    }

    ClusterSimulation.proper = this.decisionMap;
    return result;
  }
}
