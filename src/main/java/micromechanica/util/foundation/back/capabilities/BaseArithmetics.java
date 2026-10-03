package micromechanica.util.foundation.back.capabilities;

public class BaseArithmetics implements IBaseArithmetics {

    protected int value;
    protected final int minValue;
    protected final int maxValue;

    protected boolean canAdd = true;
    protected boolean canExtract = true;

    public BaseArithmetics(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        if (value < minValue || value > maxValue) {
            crash();
        }

        this.value = value;
    }

    @Override
    public void addValue(int amount) {
        setValue(value + amount);
    }

    @Override
    public void substractValue(int amount) {
        setValue(value - amount);
    }

    public void disableInput() {
        canAdd = false;
    }

    public void disableOutput() {
        canExtract = false;
    }

    protected void crash() {
        throw new IndexOutOfBoundsException(
                "Tried setting incorrect value while working with capability"
        );
    }
}
