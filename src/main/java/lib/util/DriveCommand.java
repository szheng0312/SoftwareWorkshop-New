package lib.util;

public class DriveCommand {
    private double rightMotorInput, leftMotorInput;

    public DriveCommand(double rightMotorInput, double leftMotorInput){
        this.rightMotorInput = rightMotorInput;
        this.leftMotorInput = leftMotorInput;
    }
    public double getRight(){
        return rightMotorInput;
    }
    public double getLeft(){
        return leftMotorInput;
    }
    
}
