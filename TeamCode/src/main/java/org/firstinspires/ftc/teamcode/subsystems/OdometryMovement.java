package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.odometry.GoBildaPinpointDriver;

public class OdometryMovement {

    private GoBildaPinpointDriver pinpoint;
    private MecanumDrive driveTrain;

    double inputDistance = 0;
    int[] increments = {1,2,6,12};
    int incrementIndex = 0;
    String[] direction = {"forward", "strafe"};
    int directionIndex = 0;

    public void cycleIncrement() {
        incrementIndex = (incrementIndex + 1) % increments.length;
    }

    public int getSelectedIncrement() {
        return increments[incrementIndex];
    }

    public void cycleDirection() {
        directionIndex = (directionIndex + 1) % direction.length;
    }

    public String getSelectedDirection() {
        return direction[directionIndex];
    }
    public void init(HardwareMap hwMap, MecanumDrive driveTrain){
        this.driveTrain = driveTrain;
        pinpoint = hwMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(2.0, -7.0); // Pod offsets
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD
        );
        pinpoint.resetPosAndIMU();
    }

    public void update(String movementDirection, double distance) {
        pinpoint.update();
        handleDistanceInput(movementDirection, distance);
    }

    public Pose2D getPose() {
        pinpoint.update();
        return pinpoint.getPosition();
    }

    public double getXInches() {
        return getPose().getX(DistanceUnit.INCH);
    }

    public double getYInches() {
        return getPose().getY(DistanceUnit.INCH);
    }

    public double getHeadingDegrees() {
        return getPose().getHeading(AngleUnit.DEGREES);
    }

    private double startYInches = 0;
    private double startXInches = 0;
    private boolean isDrivingY = false;
    private boolean isStrafingX = false;

    public void driveForwardInches(double distanceInches) {
        pinpoint.update();
        if (!isDrivingY) {
            startYInches = getYInches();
            isDrivingY = true;
        }
        double distanceTraveled = getYInches() - startYInches;
        double error = distanceInches - distanceTraveled;

        double kP = 0.08;
        double power = kP * error;

        if (driveTrain != null) {
            driveTrain.drive(power, 0, 0);
        }
    }

    public void strafeInches(double distanceInches) {
        pinpoint.update();
        if (!isStrafingX) {
            startXInches = getXInches();
            isStrafingX = true;
        }
        double distanceTraveled = getXInches() - startXInches;
        double error = distanceInches - distanceTraveled;

        double kP = 0.08;
        double power = kP * error;

        if (driveTrain != null) {
            driveTrain.drive(0, power, 0);
        }
    }

    public void stopMoving() {
        isDrivingY = false;
        isStrafingX = false;
        if (driveTrain != null) {
            driveTrain.drive(0, 0, 0);
        }
    }

    private void handleDistanceInput(String movementDirection, double distance){
        if ("forward".equalsIgnoreCase(movementDirection)) {
            driveForwardInches(distance);
        } else if ("strafe".equalsIgnoreCase(movementDirection)) {
            strafeInches(distance);
        }
    }
}
