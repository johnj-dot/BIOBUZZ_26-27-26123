package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.AprilTagAlignment;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.OdometryMovement;

@TeleOp
public class TeleOp_OdometryTesting extends OpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    public AprilTagAlignment aprilTagAlignment = new AprilTagAlignment();
    public MecanumDrive driveTrain = new MecanumDrive();
    public OdometryMovement odometryMovement = new OdometryMovement();

    @Override
    public void init(){
        driveTrain.init(hardwareMap, aprilTagAlignment);
        odometryMovement.init(hardwareMap, driveTrain);
    }

    @Override
    public void loop(){

    }
}
