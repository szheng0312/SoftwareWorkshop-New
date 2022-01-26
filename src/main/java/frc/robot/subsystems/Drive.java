package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.robot.Constants;

public class Drive extends Subsystem {

    public class PeriodicIO{
        double rightDemand;
        double leftDemand;
    }
    public static Drive mDrive;

    public PeriodicIO mPeriodicIO;

    TalonFX rightMaster, rightMotor2, leftMaster, leftMaster2;
    Joystick throttleJS, turnJS;

    public Drive(){
        TalonFX rightMaster  = new TalonFX(Constants.kRightMaster);
        TalonFX rightMotor2  = new TalonFX(Constants.kRight2);
        TalonFX leftMaster  = new TalonFX(Constants.kLeftMaster);
        TalonFX leftMotor2  = new TalonFX(Constants.kLeft2);
        Joystick throttleJS = new Joystick(Constants.throttleJSid);
        Joystick turnJS = new Joystick(Constants.throttleJSid);
        rightMotor2.set(ControlMode.Follower, Constants.kRightMaster);
        leftMotor2.set(ControlMode.Follower, Constants.kLeftMaster);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
    }

    public synchronized static Drive getInstance() {
        if (mDrive == null) {
            mDrive = new Drive();
        }

        return mDrive;
    }
    
    public void readPeriodicInputs() {
        double rightMovement = rightMaster.getSelectedSensorPosition(1);
        double leftMovement = leftMaster.getSelectedSensorPosition(1);
        SmartDashboard.putNumber("Right Encoder:", rightMovement);
        SmartDashboard.putNumber("Left Encoder:", leftMovement);
        mDrive.setOpenLoop(throttleJS.getRawAxis(1), turnJS.getRawAxis(0));
    }

    public void writePeriodicOutputs() {
        rightMaster.set(ControlMode.PercentOutput, mPeriodicIO.rightDemand);
        leftMaster.set(ControlMode.PercentOutput, mPeriodicIO.leftDemand);
    }

    public void setOpenLoop(double throttle, double turn){
        mPeriodicIO.rightDemand = throttle + turn;
        mPeriodicIO.leftDemand = throttle - turn;
        if (Math.abs(throttle) > 1 || Math.abs(turn) > 1){
            double constant = Math.max(mPeriodicIO.rightDemand, mPeriodicIO.leftDemand);
            mPeriodicIO.rightDemand /= constant;
            mPeriodicIO.leftDemand /= constant;
        }

    }

    public void stop(){
        rightMaster.setNeutralMode(NeutralMode.Brake);
        leftMaster.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public boolean checkSystem() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void outputTelemetry() {
        // TODO Auto-generated method stub
        
    }


}
