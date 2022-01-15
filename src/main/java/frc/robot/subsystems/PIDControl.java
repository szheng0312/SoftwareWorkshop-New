package frc.robot.subsystems;

import java.time.Clock;
import java.util.concurrent.TimeUnit;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class PIDControl {
    Clock clock = Clock.systemDefaultZone();
    private double Kp = 0;
    private double Ki = 0;
    private double Kd = 0;

    private double rightSetPoint;
    private double leftSetPoint;
    
    private double error;
    private double previousError = 0;

    private long time;
    private long previousTime;

    private long dt;
    private double integral = 0;

    private TalonFX rightMaster;
    private TalonFX leftMaster;

    public PIDControl(TalonFX rightMaster, TalonFX leftMaster){
        this.rightMaster = rightMaster;
        this.leftMaster = leftMaster;
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
    }

    public void set(double rightSetPoint, double leftSetPoint){
        this.rightSetPoint = rightSetPoint;
        this.leftSetPoint = leftSetPoint;
    }

    public void updateLoop() {
        double rightMeasuredValue = rightMaster.getSelectedSensorPosition(0);
        double leftMeasuredValue = leftMaster.getSelectedSensorPosition(0);
        double rightError = findPID(rightMeasuredValue, rightSetPoint);
        double leftError = findPID(leftMeasuredValue, leftSetPoint);
        try {
            TimeUnit.MILLISECONDS.sleep(dt);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        rightMaster.set(ControlMode.PercentOutput, rightError);
        leftMaster.set(ControlMode.PercentOutput, leftError);
    }
    public double findPID(double measuredValue, double desiredPoint){
        time = clock.millis();
        dt = time - previousTime;
        error = desiredPoint - measuredValue;
        integral = integral + error * dt;
        double derivative = (error - previousError) / dt;
        double output = Kp * error + Ki * integral + Kd * derivative;
        previousError = error;
        previousTime = time;
        return output;
    }

}
