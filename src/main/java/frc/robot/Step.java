package frc.robot;

public class Step {
    public StepType type;
    public double value;

    public Step(StepType stepType, double stepValue) {
        type = stepType;
        value = stepValue;
    }
}
