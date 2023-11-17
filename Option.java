public abstract class Option {
    private SecLevel classification;
    private String name;

    public Option(SecLevel c, String n) {
        this.classification = c;
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
