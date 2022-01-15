package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

interface subsystems{
    void init();
    void setOpenLoop(double throttle, double turn);
    void stop();
}
public class Drive implements subsystems{
    private static final int kright = 0;
    private static final int kright2 = 1;
    private static final int kleft = 2;
    private static final int kleft2 = 3;

    private TalonFX rightMaster  = new TalonFX(kright);
    private TalonFX rightMotor2  = new TalonFX(kright2);
    private TalonFX leftMaster  = new TalonFX(kleft);
    private TalonFX leftMotor2  = new TalonFX(kleft2);

    public void init(){
        rightMotor2.set(ControlMode.Follower, kright);
        leftMotor2.set(ControlMode.Follower, kleft);
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

