package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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
 * SERVICES; LOSS OF USE, DATA, OR PROFITS.74; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

@TeleOp(name="Final TeleOp")
public abstract class FinalTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    public DcMotor leftFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightFront = null;
    public DcMotor rightBack = null;
    public DcMotor outtake_extension = null;
    public DcMotor Intake = null;

    public DcMotor hanging = null;
    public Servo drone = null;
    public Servo left_bucket = null;
    public Servo right_bucket = null;
    public Servo outtake_wheel = null;

    public Servo hanging_servo = null;


    public static final double timeShift = 0.1;
    public static final double RightServoPos1 = 0.3 + timeShift;
    public static final double RightServoPos2 = 0.9;
    public static final double LeftServoPos1 = 0.3;
    public static final double LeftServoPos2 = 0.9 - timeShift;


    public static final double turnSpeed = 0.4;

    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        // Define and Initialize Motors
        leftFront = hwMap.get(DcMotor.class, "leftFront");
        leftBack = hwMap.get(DcMotor.class, "leftBack");
        rightFront = hwMap.get(DcMotor.class, "rightFront");
        rightBack = hwMap.get(DcMotor.class, "rightBack");
        Intake = hwMap.get(DcMotor.class, "Intake");
        outtake_extension = hwMap.get(DcMotor.class, "outtake_extension");
        hanging = hwMap.get(DcMotor.class, "hanging");
        drone = hwMap.get(Servo.class, "drone");
        left_bucket = hwMap.get(Servo.class, "left_bucket_servo");
        right_bucket = hwMap.get(Servo.class, "right_bucket_servo");
        outtake_wheel = hwMap.get(Servo.class, "outtake_wheel");
        hanging_servo = hwMap.get(Servo.class, "hanging_servo");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);


        telemetry.addData("Say", "Hello Driver");    //
    }


    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        left_bucket.setPosition(LeftServoPos1);
        right_bucket.setPosition(RightServoPos1);
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        /* gamepad 1 start ------------------------------------------------*/
        leftFront.setPower(gamepad1.left_stick_y);
        rightFront.setPower(gamepad1.right_stick_y);
        leftBack.setPower(gamepad1.right_stick_y);
        rightBack.setPower(gamepad1.left_stick_y);
        telemetry.log().add("loop enter");


        if (gamepad1.right_bumper) {
            leftFront.setPower(turnSpeed);
            rightFront.setPower(-turnSpeed);
            leftBack.setPower(turnSpeed);
            rightBack.setPower(-turnSpeed);
        } else if (gamepad1.left_bumper) {
            leftFront.setPower(-turnSpeed);
            rightFront.setPower(turnSpeed);
            leftBack.setPower(-turnSpeed);
            rightBack.setPower(turnSpeed);
        } else {
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);
        }

        if (gamepad1.x) {
            hanging.setPower(1);
        }

        /* gamepad 2 start -----------------------------------------------*/
        if (gamepad2.left_stick_y > 0.2) {
            outtake_wheel.setPosition(1);
        }
        Intake.setPower(gamepad2.left_stick_y);
        outtake_extension.setPower(gamepad2.right_stick_y);

        if(gamepad2.a)
        {
            right_bucket.setPosition(RightServoPos1);
            left_bucket.setPosition(LeftServoPos1);
            telemetry.addData("In:", "Position 1");
        }
        if(gamepad2.x)
        {
            right_bucket.setPosition(RightServoPos2);
            left_bucket.setPosition(LeftServoPos2);
            telemetry.addData("In:", "Position 2");
        }
        if (gamepad2.y) {
            telemetry.log().add("drone launched");
            drone.setPosition(0.7);
        }

        if(gamepad2.b) {
            hanging_servo.setPosition(1);
            telemetry.log().add("hanging activated");
        }
    }
}
