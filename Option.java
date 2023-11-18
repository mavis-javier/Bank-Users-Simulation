public abstract class Option {
    private SecLevel classification;
    private ActionType myActionType;
    private String name;

    public Option(SecLevel c, ActionType a, String n) {
        this.classification = c;
        this.myActionType = a;
        this.name = n;
    }

    public void start() {}

    public SecLevel getClassification() {
        return this.classification;
    }
    public String toString() {
        return name;
    }
}
