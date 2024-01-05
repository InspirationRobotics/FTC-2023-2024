package org.firstinspires.ftc.teamcode.TeleOp;

import android.drm.DrmStore;

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

@TeleOp(name="TeleopAndymark")
public class LM3_TeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    public DcMotor leftFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightFront = null;
    public DcMotor rightBack = null;
    //public DcMotor armMotor = null;


    HardwareMap hwMap = null;



    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");

        // Save reference to Hardware map
        // Define and Initialize Motors
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");// ctrl 0
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");// ctrl 1
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");// ctrl 2
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");// ctrl 3
        //armMotor = hardwareMap.get(DcMotor.class, "armMotor");// ctrl 3


        //components not being used for LM2:
        //hanging_servo = hardwareMap.get(Servo.class, "hanging_servo");//exp 2
        //left_bucket = hardwareMap.get(Servo.class, "left_bucket_servo");//exp 5
        //right_bucket = hardwareMap.get(Servo.class, "right_bucket_servo");//ctrl 5
        //outtake_wheel = hardwareMap.get(CRServo.class, "outtake_wheel");//ctrl 4
        //intake = hardwareMap.get(DcMotor.class, "intake");//ctrl 2
        //outtake_extension = hardwareMap.get(DcMotor.class, "outtake_extension");//exp 3
        //hanging2 = hardwareMap.get(DcMotor.class, "hanging_2");//ctrl 3

        telemetry.addData("Say", "Hello Driver");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    @Override
    public void loop() {
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        //armMotor.setPower(0);

        leftFront.setPower(gamepad1.left_stick_y);
        leftBack.setPower(gamepad1.right_stick_y);
        rightFront.setPower(gamepad1.right_stick_y);
        rightBack.setPower(gamepad1.left_stick_y);

        //if (gamepad1.a) {
           // leftFront.setPower(1);
        //}
        //if (gamepad1.b) {
          //  rightFront.setPower(1);
       // }
        //if (gamepad1.x) {
          //  leftBack.setPower(1);
       // }
       // if (gamepad1.y) {
          //  rightBack.setPower(1);
       // }

        if (gamepad1.left_bumper) {
            leftFront.setPower(-1);
            leftBack.setPower(-1);
            rightFront.setPower(1);
            rightBack.setPower(1);
        } else if (gamepad1.right_bumper) {
            leftFront.setPower(1);
            leftBack.setPower(1);
            rightFront.setPower(-1);
            rightBack.setPower(-1);
        }
    }
}
