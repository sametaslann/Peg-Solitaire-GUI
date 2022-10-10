public class Vertex {

    private int ID;
    private String label;
    private double weight;

    private String key;
    private String value;


    private static int counter = 0;


    public Vertex(String label, double weight) {
        ID = counter++;
        this.label = label;
        this.weight = weight;
        this.key = " ";
        this.value = " ";
    }

    public Vertex(String label, double weight, String key, String value) {
        ID = counter++;
        this.label = label;
        this.weight = weight;
        this.key = key;
        this.value = value;

    }

    //Getters and Setters
    public int getID() {
        return ID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }



    
    
}
