package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
    //public DcMotor outtake_extension = null;
    //public DcMotor intake = null;

    public DcMotor hanging = null;
    //public DcMotor hanging2 = null;
    public Servo drone = null;
    public Servo gripper = null;
    public Servo flipper = null;
    //public Servo left_bucket = null;
    //public Servo right_bucket = null;
    //public CRServo outtake_wheel = null;

    //public Servo hanging_servo = null;


    //public static final double RightServoPos1 = 0.4;
    //public static final double RightServoPos2 = .1;
    //public static final double LeftServoPos1 = 0.7;
    //public static final double LeftServoPos2 = 1.2;


    public static final double turnSpeed = 0.4;

    HardwareMap hwMap = null;

    double leftFrontSpeed = 0;
    double leftRearSpeed = 0;
    double rightFrontSpeed = 0;
    double rightRearSpeed = 0;




    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");

        // Save reference to Hardware map
        // Define and Initialize Motors
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");// ctrl 0
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");// ctrl 1
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");// ctrl 2
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");// ctrl 3
        hanging = hardwareMap.get(DcMotor.class, "hanging");// exp 0
        drone = hardwareMap.get(Servo.class, "drone");// exp 1
        flipper = hardwareMap.get(Servo.class, "flipper");
        gripper = hardwareMap.get(Servo.class, "gripper");

        //components not being used for LM2:
        //hanging_servo = hardwareMap.get(Servo.class, "hanging_servo");//exp 2
        //left_bucket = hardwareMap.get(Servo.class, "left_bucket_servo");//exp 5
        //right_bucket = hardwareMap.get(Servo.class, "right_bucket_servo");//ctrl 5
        //outtake_wheel = hardwareMap.get(CRServo.class, "outtake_wheel");//ctrl 4
        //intake = hardwareMap.get(DcMotor.class, "intake");//ctrl 2
        //outtake_extension = hardwareMap.get(DcMotor.class, "outtake_extension");//exp 3
        //hanging2 = hardwareMap.get(DcMotor.class, "hanging_2");//ctrl 3


        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addData("Say", "Hello Driver");
    }
    @Override
    public void loop() {
        leftFrontSpeed = gamepad1.left_stick_y + gamepad1.left_trigger - gamepad1.right_trigger;
        leftRearSpeed = gamepad1.left_stick_y - gamepad1.left_trigger + gamepad1.right_trigger;
        rightRearSpeed = gamepad1.right_stick_y + gamepad1.left_trigger - gamepad1.right_trigger;
        rightFrontSpeed = gamepad1.right_stick_y - gamepad1.left_trigger + gamepad1.right_trigger;

        if (leftFrontSpeed > 1)
            leftFrontSpeed = 1;
        if (leftFrontSpeed < -1)
            leftFrontSpeed = -1;
        if (rightFrontSpeed > 1)
            rightFrontSpeed = 1;
        if (rightFrontSpeed < -1)
            rightFrontSpeed = -1;
        if (leftRearSpeed > 1)
            leftRearSpeed = 1;
        if (leftRearSpeed < -1)
            leftRearSpeed = -1;
        if (rightRearSpeed > 1)
            rightRearSpeed = 1;
        if (rightRearSpeed < -1)
            rightRearSpeed = -1;

        leftFront.setPower(leftFrontSpeed);
        rightFront.setPower(rightFrontSpeed);
        leftBack.setPower(leftRearSpeed);
        rightBack.setPower(rightRearSpeed);
        hanging.setPower(0);

        if(gamepad2.dpad_up)
            hanging.setPower(1);
        if(gamepad2.dpad_down)
            hanging.setPower(-1);
        if(gamepad2.x)
            drone.setPosition(0.7);
        if(gamepad2.y)
            drone.setPosition(0);
        if(gamepad1.a)
            flipper.setPosition(0.4);
        if(gamepad1.b)
            flipper.setPosition(0.8);
        if(gamepad1.x)
            gripper.setPosition(0);
        if(gamepad1.y)
            gripper.setPosition(0.5);
    }
}