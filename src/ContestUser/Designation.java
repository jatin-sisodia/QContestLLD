package ContestUser;

public enum Designation {
    YELLOW(1000,1),
    RED(1500,1.5),
    BLACK(2000, 2);

    private int value;
    private double coefficient;
    private Designation(int value, double coeff){
        this.value = value;
        this.coefficient = coeff;
    }
    public int getValue() {
        return value;
    }
    public double getCoefficient() {
        return coefficient;
    }
}
