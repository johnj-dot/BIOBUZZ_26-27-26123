package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;

public class AprilTagAlignment {
    private Limelight limelight;
    private ElapsedTime runtime;


    // ------------------------ Rotation Locked ----------------
    double rotationMultiplier = 1;

    // ------------------------ PD Controller ------------------
    double kP = 0.0002;
    double kD = 0.0001;

    double error = 0;
    double lastError = 0;
    double goalX = 0;
    double angleTolerance = 0.2;
    double curTime = 0;
    double lastTime = 0;

    // ----------------------- Controller based PD tuning --------
    double[] stepSizes = {1.0,0.1,0.01,0.001,0.0001};
    int stepIndex = 2;
    double output = 0;

    public void init(Limelight limelight, ElapsedTime runtime, MecanumDrive drive){
        this.limelight = limelight;
        this.runtime = runtime;
    }

    public void start() {
        runtime.reset();
        curTime = runtime.time();
        lastTime = curTime;
        error = goalX - limelight.getTx();
        lastError = error;
    }
    public void update(){
        lastTime = curTime;
        curTime = runtime.time();
        double dt = curTime - lastTime;

        lastError = error;
        error = goalX - limelight.getTx();

        double derivative = 0;
        if (dt > 0) {
            derivative = (error - lastError) / dt;
        }
        output = (kP * error) + (kD * derivative);
    }

    public double getOutput() {
        return output;
    }

    public double getRotationMultiplier(){
        return rotationMultiplier;
    }
}

