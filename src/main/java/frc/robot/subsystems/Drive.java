package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

interface Subsystems{
    void init();
    void setOpenLoop(double throttle, double turn);
    void stop();
}
public class Drive implements Subsystems {
    private final static int kRight = 0;
    private final static int kRight2 = 1;
    private final static int kLeft = 2;
    private final static int kLeft2 = 3;

    private TalonFX rightMaster  = new TalonFX(kRight);
    private TalonFX rightMotor2  = new TalonFX(kRight2);
    private TalonFX leftMaster  = new TalonFX(kLeft);
    private TalonFX leftMotor2  = new TalonFX(kLeft2);

    public void init(){
        rightMotor2.set(ControlMode.Follower, kRight);
        leftMotor2.set(ControlMode.Follower, kLeft);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
    }

    public void setOpenLoop(double throttle, double turn){
        rightMaster.set(ControlMode.PercentOutput, throttle + turn);
        leftMaster.set(ControlMode.PercentOutput, throttle - turn);
    }
    public void stop(){
        rightMaster.setNeutralMode(NeutralMode.Brake);
        leftMaster.setNeutralMode(NeutralMode.Brake);
    }


}
