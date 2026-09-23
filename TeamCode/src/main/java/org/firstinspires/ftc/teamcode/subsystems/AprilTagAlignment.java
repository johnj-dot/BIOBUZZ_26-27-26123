package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

public class AprilTagAlignment {
    private Limelight limelight;
    private ElapsedTime runtime;


    // ------------------------ Rotation Locked ----------------
    double rotationMultiplier = 1;
    double addedRotation = 0;

    // ------------------------ PD Controller ------------------
    double kP = 0.001;
    double kD = 0.0001;
    double dt = 0;
    double derivative = 0;

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

    String currentlyModifying = "kP";

    // --------------------------------

    public void init(Limelight limelight, ElapsedTime runtime){
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
    public void update(Gamepad gamepad1){
        handlePDupdates();
        handleIncrements(gamepad1);
        handleAutoRotation(gamepad1);

    }
    private void handlePDupdates(){
        lastTime = curTime;
        curTime = runtime.time();
        dt = curTime - lastTime;

        lastError = error;
        error = goalX - limelight.getTx();
        if (dt > 0) {
            derivative = (error - lastError) / dt;
        }
        output = (kP * error) + (kD * derivative);
    }
    private void handleAutoRotation(Gamepad gamepad1){
        if (gamepad1.left_trigger>0.3) {
            if (limelight.isTargetVisible()){
                rotationMultiplier = 0;
                if (Math.abs(error) < angleTolerance){
                    addedRotation = 0;
                }
                else {
                    addedRotation = output;
                }
            }
            else {
                rotationMultiplier = 1;
                addedRotation = 0;
            }

        } else {
            rotationMultiplier = 1;
            addedRotation = 0;
        }
    }
    private void handleIncrements(Gamepad gamepad1){
        if (gamepad1.dpadUpWasPressed()){
            if (currentlyModifying.equals("kP")){
                kP += stepSizes[stepIndex];
            }
            if (currentlyModifying.equals("kD")){
                kD += stepSizes[stepIndex];
            }
        }
        if (gamepad1.dpadDownWasPressed()){
            if (currentlyModifying.equals("kP")){
                kP -= stepSizes[stepIndex];
            }
            if (currentlyModifying.equals("kD")){
                kD -= stepSizes[stepIndex];
            }
        }
        if (gamepad1.dpadLeftWasPressed()){
            if (currentlyModifying.equals("kD")){
                currentlyModifying = "kP";
            }
            else {
                currentlyModifying = "kD";
            }
        }
        if (gamepad1.bWasPressed()){
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
    }
    public double getRotationMultiplier(){
        return rotationMultiplier;
    }
    public double getAddedRotation(){return addedRotation;}
    public void changeStepIncrement(){
        stepIndex = (stepIndex+1) % stepSizes.length;
    }
    public double getStepSize() {
        return stepSizes[stepIndex];
    }
    public double getkP(){
        return kP;
    }
    public double getkD(){
        return kD;
    }
    public String getCurrentlyModifying() {
        return currentlyModifying;
    }
}

