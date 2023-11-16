public class Option {
    private SecLevel classification;
    private String name;

    public void setClassification(SecLevel classification) {
        this.classification = classification;
    }
    public void setName(String name) {
        this.name = name;
    }

    public SecLevel getClassification() {
        return this.classification;
    }
    public String toString() {
        return name;
    }
}
