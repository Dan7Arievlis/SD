import java.util.Random;

public enum Decision {
  DIREITA {
    @Override
    public String toString() {
      return "direita";
    }
  },
  ESQUERDA {
    @Override
    public String toString() {
      return "esquerda";
    }
  },
  CIMA {
    @Override
    public String toString() {
      return "cima";
    }
  },
  BAIXO {
    @Override
    public String toString() {
      return "baixo";
    }
  };

  private static Random PRNG = new Random();

  public static Decision randomDecision() {
    Decision[] decisions = values();
    return decisions[PRNG.nextInt(decisions.length)];
  }
}