package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DriveTrain{
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    double frontLeftMotorSpeed = 0;
    double frontRightMotorSpeed = 0;
    double backLeftMotorSpeed = 0;
    double backRightMotorSpeed = 0;

    private double speedMultiplier = 0.55;
    public void init(HardwareMap hwMap){
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
            setSpeedMultiplier(1.0);
        }
        if (gamepad1.dpadRightWasPressed()){
            setSpeedMultiplier(0.75);
        }
        if (gamepad1.dpadDownWasPressed()){
            setSpeedMultiplier(0.5);
        }
        if (gamepad1.dpadLeftWasPressed()){
            setSpeedMultiplier(0.25);
        }
    }
}
