package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
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

        waitForStart();

        while (opModeIsActive()){

            double fRLM;
            double fRRM;
            double bALM;
            double bARM;

            fRLM = -gamepad1.left_stick_y;
            fRRM = gamepad1.left_stick_y;
            bALM = -gamepad1.left_stick_y;
            bARM = gamepad1.left_stick_y;

            frontLeftMotor.setPower(fRLM);
            frontRightMotor.setPower(fRRM);
            backLeftMotor.setPower(bALM);
            backRightMotor.setPower(bARM);


        }


    }
}