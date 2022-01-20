package frc.robot.subsystems;

import java.time.Clock;
import java.util.concurrent.TimeUnit;


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

    public double getError(){
        return error;
    }

    public void set(double rightSetPoint, double leftSetPoint){
        this.rightSetPoint = rightSetPoint;
        this.leftSetPoint = leftSetPoint;
    }

    public void updateLoop() {
        try {
            TimeUnit.MILLISECONDS.sleep(dt);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
