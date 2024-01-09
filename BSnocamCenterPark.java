package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class BSnocamCenterPark extends LinearOpMode {
    //RIGHT

    public Servo clawLeft = null;
    public Servo clawRight = null;
    public DcMotor wristMotor = null;
    public DcMotor armMotor = null;

    int wristDownPosition = -91;
    // Position of the wrist when it's on the ground (ticks)
    int wristUpPosition = 145;
    // Position of the arm when it's lifted (ticks)
    int armUpPosition = 1900;
    // Position of the arm when it's down (ticks)
    int armDownPosition = 0;



    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");
        wristMotor = hardwareMap.get(DcMotor.class, "wristMotor");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        // Reset the motor encoder so that it reads zero ticks
        wristMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        wristMotor.setTargetPosition(138);
        wristMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wristMotor.setPower(0.3);
        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(new Pose2d()) // for red long - center spike mark
                .waitSeconds(3)
                .forward(28)
                .build();
        drive.followTrajectorySequence(trajSeq);
        clawLeft.setPosition(0.3);
        trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .waitSeconds(2)
                .back(26)
                .build();
        drive.followTrajectorySequence(trajSeq);
        clawLeft.setPosition(0);
        wristMotor.setTargetPosition(0);
        wristMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wristMotor.setPower(0.3);
        trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .waitSeconds(2)
                .strafeLeft(50)
                .build();
        drive.followTrajectorySequence(trajSeq);
        //armMotor.setTargetPosition(1900);
        // armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // armMotor.setPower(0.3);
        //trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
        //  .back(8)
        //  .waitSeconds(5)
        //  .build();
        //drive.followTrajectorySequence(trajSeq);
        if(isStopRequested()) return;
    }
}