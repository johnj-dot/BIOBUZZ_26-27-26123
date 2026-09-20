package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDrive {


    // ---------------------- Getting Hardware ----------------
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private AprilTagAlignment aprilTagAlignment;


    // ----------------------- Motor Variables -----------------
    double frontLeftMotorSpeed = 0;
    double frontRightMotorSpeed = 0;
    double backLeftMotorSpeed = 0;
    double backRightMotorSpeed = 0;


    private double speedMultiplier = 0.50;


    public void init(HardwareMap hwMap, AprilTagAlignment aprilTagAlignment){
        this.aprilTagAlignment = aprilTagAlignment;
        frontLeftMotor = hwMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hwMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hwMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hwMap.get(DcMotor.class, "backRightMotor");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive(double y, double x, double rx) {
        if (aprilTagAlignment != null) {
            rx *= aprilTagAlignment.getRotationMultiplier();
        }

        frontLeftMotorSpeed = -y + x + rx;
        backLeftMotorSpeed = -y - x + rx;
        frontRightMotorSpeed = -y - x - rx;
        backRightMotorSpeed = -y + x - rx;

        frontLeftMotor.setPower(frontLeftMotorSpeed*speedMultiplier);
        backLeftMotor.setPower(backLeftMotorSpeed*speedMultiplier);
        frontRightMotor.setPower(frontRightMotorSpeed*speedMultiplier);
        backRightMotor.setPower(backRightMotorSpeed*speedMultiplier);
    }
    public void setSpeedMultiplier(double multiplier){
        speedMultiplier = multiplier;
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }
    public void update(Gamepad gamepad1){
        handleDpadSpeedSwitching(gamepad1);
    }
    private void handleDpadSpeedSwitching(Gamepad gamepad1){
        if (gamepad1.dpadUpWasPressed()){
            //Set speed to 100%
            setSpeedMultiplier(1.0);
        }
        if (gamepad1.dpadRightWasPressed()){
            //Set speed to 75%
            setSpeedMultiplier(0.75);
        }
        if (gamepad1.dpadDownWasPressed()){
            //Set speed to 50%
            setSpeedMultiplier(0.5);
        }
        if (gamepad1.dpadLeftWasPressed()){
            //Set speed to 25%
            setSpeedMultiplier(0.25);
        }
    }
}
