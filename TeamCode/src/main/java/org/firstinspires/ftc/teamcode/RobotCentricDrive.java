package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp
public class RobotCentricDrive extends LinearOpMode{

    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;


    @Override
    public void runOpMode()  {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontLeftMotor = hardwareMap.get(DcMotor.class, "FLM");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FRM");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BLM");
        backRightMotor = hardwareMap.get(DcMotor.class, "BRM");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //cuz left side is opposite


        waitForStart();

        while (opModeIsActive()){

            double y = -gamepad1.left_stick_y; //Cuz y stick is reversed-For forward and backward movement
            double rx = gamepad1.right_stick_x; //For turning/pivoting
            double x = gamepad1.left_stick_x * 1.1; // This is for strafing, and *1.1 is to have more even and precise strafing

            //Left motors will be -rx due to them being "flipped" While right motors will stay + since they're reg.
            // For the x the wheels diagonal to each other will be the same sign as they need to rotate the same way to strafe properly
            // Math.abs makes sure that we look at the actual size of the number and not the direction of it(- or +).
            //The denominator scales everything down so no motor exceeds [-1,1] and keeps ratios "intact"
            //Using Math.max() prevents scaling up unnecesary values.
            //The 1 helps keeps the demoniator and dividing "even"
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftMotorPower = (y-x-rx) / denominator;
            double backLeftMotorPower = (y+x-rx) /  denominator;
            double frontRightMotorPower = (y+x+rx) / denominator;
            double backRightMotorPower = (y-x+rx) / denominator;

            frontLeftMotor.setPower(frontLeftMotorPower);
            backLeftMotor.setPower(backLeftMotorPower);
            frontRightMotor.setPower(frontRightMotorPower);
            backRightMotor.setPower(backRightMotorPower);


        }


    }
}