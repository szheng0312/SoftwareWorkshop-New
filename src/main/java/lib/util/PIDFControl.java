package lib.util;

import edu.wpi.first.wpilibj.Timer;


public class PIDFControl {
    private double Kp, Ki, Kd, Kf;
    private double setPoint;
    private double previousError = 0;
    private double previousTime = 0;
    private double integral = 0;

    public void setSetPoint(double setPoint){
        this.setPoint = setPoint;
    }

    public void setKp(double Kp){
        this.Kp = Kp;
    }

    public void setKi(double Ki){
        this.Ki = Ki;
    }

    public void setKd(double Kd){
        this.Kd = Kd;
    }

    public void setKf(double Kf){
        this.Kf = Kf;
    }
    public double getError(){
        return previousError;
    }

    public double findPID(double measuredValue){
        double currentTime = Timer.getFPGATimestamp();
        double dt = currentTime - previousTime;
        double error = setPoint - measuredValue;
        integral = integral + error * dt;
        double derivative = (error - previousError) / dt;
        double output = Kp * error + Ki * integral + Kd * derivative + Kf * setPoint;
        previousError = error;
        previousTime = currentTime;
        return output;
    }

}
