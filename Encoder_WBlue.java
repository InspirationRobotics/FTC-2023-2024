package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Encoder_WBlue")
//@Disabled
public class Encoder_WBlue extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    static DcMotor leftFront;
    static DcMotor rightFront;
    static DcMotor leftBack;
    static DcMotor rightBack;

    public  double COUNTS_PER_MOTOR_REV;
    public  double DRIVE_GEAR_REDUCTION;
    public  double WHEEL_DIAMETER_INCH;
    public  double COUNTS_PER_INCH;
    public  double ROBOT_DIAMETER;
    public  double ROBOT_CIRCUMFERENCE;

    double power = 0.5;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");


        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        COUNTS_PER_MOTOR_REV = 537.6;
        DRIVE_GEAR_REDUCTION = 19.2/1;     // This is < 1.0 if geared UP (32 teeth to 16 teeth)
        WHEEL_DIAMETER_INCH = 96;     // For figuring circumference
        COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCH* 3.1415);
        ROBOT_CIRCUMFERENCE = ROBOT_DIAMETER * 3.1415;

        waitForStart();
        runtime.reset();


        //strafe left
        encoderStrafeINCH(34, 0.5);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //runs backwards
        encoderMoveINCH(-28, -28, 0.5);

        //runs forward
        encoderMoveINCH(20, 20, 0.4);

        //turn
        encoderMoveINCH(25, -25, 0.4);

        //strafe left
        encoderStrafeINCH(25, 0.4);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //runs forward
        encoderMoveINCH(70, 70, 0.3);
    }

    int leftPos = 0;
    int rightPos = 0;

    public void encoderMoveBasic(double leftTarget, double rightTarget, double power){

        leftPos += leftTarget;
        rightPos += rightTarget;

        leftFront.setTargetPosition(leftPos);
        leftBack.setTargetPosition(leftPos);
        rightFront.setTargetPosition(rightPos);
        rightBack.setTargetPosition(rightPos);

        leftFront.setPower(power);
        rightFront.setPower(power);
        leftBack.setPower(power);
        rightBack.setPower(power);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

        /*public void encoderStrafe (double distance, double power){

            int ticks = (int)(distance * 301.59);

            leftFront.setTargetPosition(leftFront.getCurrentPosition()-ticks);
            leftBack.setTargetPosition(leftBack.getCurrentPosition()+ticks);
            rightFront.setTargetPosition(rightFront.getCurrentPosition()+ticks);
            rightBack.setTargetPosition(rightBack.getCurrentPosition()-ticks);

            leftFront.setPower(-power);
            rightFront.setPower(power);
            leftBack.setPower(power);
            rightBack.setPower(-power);

            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }*/

    public void encoderStrafeINCH(double inches, double power){
        int ticks = (int)(inches*COUNTS_PER_INCH);

        leftFront.setTargetPosition(leftFront.getCurrentPosition()-ticks);
        leftBack.setTargetPosition(leftBack.getCurrentPosition()+ticks);
        rightFront.setTargetPosition(rightFront.getCurrentPosition()+ticks);
        rightBack.setTargetPosition(rightBack.getCurrentPosition()-ticks);

        leftFront.setPower(power);
        rightFront.setPower(power);
        leftBack.setPower(power);
        rightBack.setPower(power);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        //continue moving
        while (opModeIsActive() && ((leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy()))) {
        }

        // Stop all motion;
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

    }

    public void encoderMoveINCH(double leftINCH, double rightINCH, double power){
        int newLeftTarget = leftFront.getCurrentPosition()+(int)(leftINCH*COUNTS_PER_INCH);
        int newRightTarget = rightFront.getCurrentPosition()+(int)(rightINCH*COUNTS_PER_INCH);
        double leftSpeed;
        double rightSpeed;

        leftFront.setTargetPosition(newLeftTarget);
        leftBack.setTargetPosition(newLeftTarget);
        rightFront.setTargetPosition(newRightTarget);
        rightBack.setTargetPosition(newRightTarget);

        if (Math.abs(leftINCH) > Math.abs(rightINCH)) {
            leftSpeed = power;
            rightSpeed = (power * rightINCH) / leftINCH;
        } else {
            rightSpeed = power;
            leftSpeed = (power * leftINCH) / rightINCH;
        }

        leftFront.setPower(leftSpeed);
        rightFront.setPower(rightSpeed);
        leftBack.setPower(leftSpeed);
        rightBack.setPower(rightSpeed);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        //continue movinggg
        while (opModeIsActive() && ((leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy()))) {
        }

        // Stop all motion;
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

    }
}