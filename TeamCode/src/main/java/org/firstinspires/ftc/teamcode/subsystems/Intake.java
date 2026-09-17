package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private DcMotor intake;
    private DcMotor windmill;
    public static final double INTAKE_IN = 0.7;
    public static final double INTAKE_OUT = -0.7;
    public static final double WINDMILL_IN = -1;
    public static final double WINDMILL_OUT = 1;

    public void init(HardwareMap hwMap){
        intake = hwMap.get(DcMotor.class, "intake");
        windmill = hwMap.get(DcMotor.class, "windmill");
        windmill.setDirection(DcMotorSimple.Direction.FORWARD);
        windmill.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void start(){
        intake.setPower(INTAKE_IN);
        windmill.setPower(WINDMILL_IN);
    }
    public void out(){
        intake.setPower(INTAKE_OUT);
        windmill.setPower(WINDMILL_OUT);
    }
    public void stop(){
        intake.setPower(0);
        windmill.setPower(0);
    }

    public void update(Gamepad gamepad2){
        handleManualIntakeControl(gamepad2);
    }
    private void handleManualIntakeControl(Gamepad gamepad2){
        if (gamepad2.left_trigger>0.5){
            start();
        } else if (gamepad2.right_bumper) {
            stop();
        }
    }
}
