/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file illustrates the concept of driving a path based on time.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: RobotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backward for 1 Second
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@Autonomous(name="Short_Deposit_Center_Red", group="Robot")

public class Short_Deposit_Center_Red extends LinearOpMode {

    /* Declare OpMode members. */
    //motors
    public DcMotor leftFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightFront = null;
    public DcMotor rightBack = null;
    public DcMotor Intake = null;

    public Servo left_bucket = null;

    public Servo right_bucket = null;

    public Servo outtake_wheel = null;


    private ElapsedTime runtime = new ElapsedTime();


    static final double FORWARD_SPEED = 0.5;
    static final double TURN_SPEED = 0.4;
    static final double STRAFE_SPEED = 0.5;

    public static final double timeShift = 0.1;
    public static final double RIGHT_SERVO_POSITION_2 = 0.9;
    public static final double LEFT_SERVO_POSITION_2 = 0.9 - timeShift;


    public void Intake_Depositing() {
        Intake.setPower(0.8);
    }

    public void Outtake_Depositing() {
        right_bucket.setPosition(RIGHT_SERVO_POSITION_2);
        left_bucket.setPosition(LEFT_SERVO_POSITION_2);
        outtake_wheel.setPosition(1);
    }
    public void TurnRight() {
        leftFront.setPower(TURN_SPEED);
        leftBack.setPower(TURN_SPEED);
        rightFront.setPower(-TURN_SPEED);
        rightBack.setPower(-TURN_SPEED);
        runtime.reset();
    }

    public void TurnLeft(){
        leftFront.setPower(-TURN_SPEED);
        leftBack.setPower(-TURN_SPEED);
        rightFront.setPower(TURN_SPEED);
        rightBack.setPower(TURN_SPEED);
        runtime.reset();
    }

    public void MoveForward(){
        leftFront.setPower(FORWARD_SPEED);
        leftBack.setPower(FORWARD_SPEED);
        rightFront.setPower(FORWARD_SPEED);
        rightBack.setPower(FORWARD_SPEED);
        runtime.reset();
    }

    public void MoveBackward(){
        leftFront.setPower(-FORWARD_SPEED);
        leftBack.setPower(-FORWARD_SPEED);
        rightFront.setPower(-FORWARD_SPEED);
        rightBack.setPower(-FORWARD_SPEED);
        runtime.reset();
    }

    public void StrafeLeft(){
        leftFront.setPower(-STRAFE_SPEED);
        rightFront.setPower(STRAFE_SPEED);
        leftBack.setPower(STRAFE_SPEED);
        rightBack.setPower(-STRAFE_SPEED);
        runtime.reset();
    }

    public void StrafeRight(){
        leftFront.setPower(STRAFE_SPEED);
        rightFront.setPower(-STRAFE_SPEED);
        leftBack.setPower(-STRAFE_SPEED);
        rightBack.setPower(STRAFE_SPEED);
        runtime.reset();
    }
    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        Intake = hardwareMap.get(DcMotor.class, "Intake");
        outtake_wheel = hardwareMap.get(Servo.class, "outtake_wheel");
        left_bucket = hardwareMap.get(Servo.class, "left_bucket");
        left_bucket = hardwareMap.get(Servo.class, "right_bucket");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1:  Move Forward for 1 seconds
        MoveBackward();
        while (opModeIsActive() && (runtime.seconds() < 0.7)) {
            telemetry.addData("Moving Forward", "Complete", runtime.seconds());
            telemetry.update();
        }

        // Step 2: Stop motion
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);

        // Step 3:  Intake deposits a purple pixel
        Intake_Depositing();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Intake Depositing", "Complete", runtime.seconds());
            telemetry.update();
        }

        // Step 4: Stop motion
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);

        // Step 3:  Robot turns left for 1 second
        TurnRight();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Turning Left", "Complete", runtime.seconds());
            telemetry.update();
        }

        // Step 4:  Move Backwards for 0.5 seconds
        MoveForward();
        while (opModeIsActive() && (runtime.seconds() < 1.1)) {
            telemetry.addData("Moving Backwards", "Complete", runtime.seconds());
            telemetry.update();
        }

        // Step 5:  Outtake deposits yellow pixel
        Outtake_Depositing();
        while (opModeIsActive() && (runtime.seconds() < 5.0)) {
            telemetry.addData("Outtake Depositing", "Complete", runtime.seconds());
            telemetry.update();
        }
        // Step 5: Stop motion
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        telemetry.addData("Autonomous", "Complete!", runtime.seconds());
        telemetry.update();

    }
}
