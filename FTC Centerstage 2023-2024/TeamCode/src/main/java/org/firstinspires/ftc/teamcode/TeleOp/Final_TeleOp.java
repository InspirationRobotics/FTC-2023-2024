package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
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

@TeleOp(name="LM2 TeleOp")
public class Final_TeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    public DcMotor leftFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightFront = null;
    public DcMotor rightBack = null;
    public DcMotor outtake_extension = null;
    public DcMotor intake = null;

    public DcMotor hanging1 = null;
    public DcMotor hanging2 = null;
    public Servo drone = null;
    public Servo left_bucket = null;
    public Servo right_bucket = null;
    public CRServo outtake_wheel = null;

    public Servo hanging_servo = null;


    public static final double RightServoPos1 = 0.4;
    public static final double RightServoPos2 = .1;
    public static final double LeftServoPos1 = 0.7;
    public static final double LeftServoPos2 = 1.2;


    public static final double turnSpeed = 0.4;

    HardwareMap hwMap = null;

    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");

        // Save reference to Hardware map
        // Define and Initialize Motors
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");//ctrl 0
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");//ctrl 1
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");//exp 0
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");//exp 1
        intake = hardwareMap.get(DcMotor.class, "intake");//ctrl 2
        outtake_extension = hardwareMap.get(DcMotor.class, "outtake_extension");//exp 3
        hanging1 = hardwareMap.get(DcMotor.class, "hanging_1");// exp 3
        hanging2 = hardwareMap.get(DcMotor.class, "hanging_2");//ctrl 3
        drone = hardwareMap.get(Servo.class, "drone");//exp 4
        hanging_servo = hardwareMap.get(Servo.class, "hanging_servo");//exp 2

        left_bucket = hardwareMap.get(Servo.class, "left_bucket_servo");//exp 5
        right_bucket = hardwareMap.get(Servo.class, "right_bucket_servo");//ctrl 5
        outtake_wheel = hardwareMap.get(CRServo.class, "outtake_wheel");//ctrl 4
        //hanginglift = hardwareMap.get(DcMotor.class, "hanginglift   ");

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
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
    }
    @Override
    public void loop() {
        /* gamepad 1 start ------------------------------------------------*/

        telemetry.log().add("loop enter");

        if(gamepad2.dpad_up){
            right_bucket.setPosition(RightServoPos1);
            left_bucket.setPosition(LeftServoPos1);
            telemetry.addData("In:", "Position 1");

        } else if(gamepad2.dpad_down){
            right_bucket.setPosition(RightServoPos2);
            left_bucket.setPosition(LeftServoPos2);
            telemetry.addData("In:", "Position 2");
        }

        if (gamepad1.dpad_right) {
            leftFront.setPower(turnSpeed);
            rightFront.setPower(-turnSpeed);
            leftBack.setPower(turnSpeed);
            rightBack.setPower(-turnSpeed);
        } else if (gamepad1.dpad_left) {
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
        intake.setPower(gamepad2.right_stick_y);
        if (gamepad1.x) {
            hanging2.setPower(1);
            hanging1.setPower(1);
            hanging1.setTargetPosition(1);
        } else if (gamepad1.b){
            hanging2.setPower(-1);
            hanging1.setPower(-1);

        } else {
            hanging2.setPower(0);
            hanging1.setPower(0);

        }

        /* gamepad 2 start -----------------------------------------------*/

        leftFront.setPower(gamepad1.left_stick_y);
        leftBack.setPower(gamepad1.left_stick_y);
        rightFront.setPower(gamepad1.right_stick_y);
        rightBack.setPower(gamepad1.right_stick_y);

        if (gamepad2.left_stick_y > 0.2) {
            outtake_extension.setPower(1);
        } else if (gamepad2.left_stick_y < -0.2) {

            outtake_extension.setPower(-1);

        }

        if (gamepad2.right_bumper)
            outtake_wheel.setPower(1);
        else if (gamepad2.left_bumper)
            outtake_wheel.setPower(-1);

        if (gamepad2.y) {
            telemetry.log().add("drone launched");
            drone.setPosition(0);
        } else if (gamepad2.x) {
            drone.setPosition(.4);
        }

        if(gamepad2.a) {
            hanging_servo.setPosition(1);
            telemetry.log().add("hanging activated");
        } else if(gamepad2.b) {
            hanging_servo.setPosition(0);
            telemetry.log().add("hanging activated");
        } else{

        }
    }
}