package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Limelight {
    //seting up hardware
    private Limelight3A limelight;
    private IMU imu;
    private Pose3D botPose;
    private boolean targetVisible = false;
    private double Tx = 0;
    private double Ty = 0;
    private double Ta = 0;

    public void init(HardwareMap hwMap){
        limelight = hwMap.get(Limelight3A.class, "limelight");
        try {
            imu = hwMap.get(IMU.class, "imu");
            RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT,RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD);
            imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
        } catch (Exception e) {
            imu = null;
        }
        limelight.pipelineSwitch(0);
    }
    
    public void start(){
        limelight.start();
    }

    public void stop(){
        limelight.stop();
    }
    
    public void update(){
        handlePosition(getLimelightResult());
    }
    
    public LLResult getLimelightResult(){
        if (imu != null) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            limelight.updateRobotOrientation(orientation.getYaw());
        }
        return limelight.getLatestResult();

    }
    
    private void handlePosition(LLResult llResult){
        if (llResult != null && llResult.isValid()){
            if (imu != null){
                botPose = llResult.getBotpose_MT2();
            } else {
                botPose = llResult.getBotpose();
            }
            targetVisible = true;
            Tx = llResult.getTx();
            Ty = llResult.getTy();
            Ta = llResult.getTa();
        } else {
            targetVisible = false;
            Tx = 0;
            Ty = 0;
            Ta = 0;
        }
    }

    public double getTx() {
        return Tx;
    }

    public double getTy() {
        return Ty;
    }

    public double getTa() {
        return Ta;
    }

    public Pose3D getBotPose() {
        return botPose;
    }

    public boolean isTargetVisible() {
        return targetVisible;
    }
}
