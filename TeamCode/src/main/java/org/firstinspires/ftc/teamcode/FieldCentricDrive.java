package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp
public class FieldCentricDrive extends LinearOpMode{
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;

    public IMU imu;
    /*Y is yaw, yaw is helicopter(in terms of robot its turning/rotating), X is pitch, pitch is like
     imagine your at the back of a car, and the wheel moving to ur perspective is pitch. and Z is roll,
     and roll is like u behind a torpedo and the torpedo spinning. */
    //Y,X,Z(the labels) are interchangeable, BUT YAW PITCH AND ROLL are always same.



    @Override
    public void runOpMode()  {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FLM");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FRM");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BLM");
        backRightMotor = hardwareMap.get(DcMotor.class, "BRM");
        // Gets the IMU from the hardware map
        imu = hardwareMap.get(IMU.class, "imu");



        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //cuz left side is opposite

// Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
// Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);




    waitForStart();

    while (opModeIsActive()) {

          //Controls are same as robot centric
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

//option is a button and is like the hamburger i think
        if (gamepad1.options) {
            imu.resetYaw();
        }
//Gets the yaw
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

/* The code gets the vectors(directions of force). But in field centric(example): your robot should
be able to go like forward (up the field) when you press the joystick forward even if your robot isn't
facing that way. So this math/code changes the force vectors of the wheels so that the robot goes in the desired direction */
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX *1.1; // Counters/ "fixes" imperfect strafing

/* This all is the same math as robot centric drive its just that the variables are named a bit different
due to it being different in field centric drive */
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftMotorPower = (rotY + rotX + rx) / denominator;
        double backLeftMotorPower = (rotY - rotX + rx) / denominator;
        double frontRightMotorPower = (rotY - rotX - rx) / denominator;
        double backRightMotorPower = (rotY + rotX - rx) / denominator;

        frontLeftMotor.setPower(frontLeftMotorPower);
        backLeftMotor.setPower(backLeftMotorPower);
        frontRightMotor.setPower(frontRightMotorPower);
        backRightMotor.setPower(backRightMotorPower);

    }

}

}
