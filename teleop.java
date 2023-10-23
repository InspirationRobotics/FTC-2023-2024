package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

@TeleOp(name="teleop", group="Pushbot")
public class teleop extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    public DcMotor leftFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightFront = null;
    public DcMotor rightBack = null;
    public DcMotor Intake = null;
    public DcMotor outtake_extension = null;
    public Servo Drone = null;
    public Servo left_bucket = null;
    public Servo right_bucket = null;
    public Servo outtake_wheel = null;

    public static final double STRAFE_SPEED = 1;


    public void init() {

        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        Intake = hardwareMap.dcMotor.get("Intake");
        outtake_extension = hardwareMap.dcMotor.get("Outtake");
        Drone = hardwareMap.servo.get("Drone");
        left_bucket = hardwareMap.servo.get("left_bucket");
        right_bucket = hardwareMap.servo.get("right_bucket");
        outtake_wheel = hardwareMap.servo.get("outtake_wheel");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
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
    public void loop() {
        /* gamepad 1 start ------------------------------------------------*/
        leftFront.setPower(gamepad1.left_stick_y);
        rightFront.setPower(-gamepad1.right_stick_y);
        leftBack.setPower(-gamepad1.left_stick_y);
        rightBack.setPower(gamepad1.right_stick_y);
        telemetry.log().add("loop enter");


        if (gamepad1.right_bumper) {
            leftFront.setPower(STRAFE_SPEED);
            rightFront.setPower(STRAFE_SPEED);
            leftBack.setPower(STRAFE_SPEED);
            rightBack.setPower(STRAFE_SPEED);
        }
        else if (gamepad1.left_bumper) {
            leftFront.setPower(-STRAFE_SPEED);
            rightFront.setPower(-STRAFE_SPEED);
            leftBack.setPower(-STRAFE_SPEED);
            rightBack.setPower(-STRAFE_SPEED);
        }
        else {
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);
        }
        /* gamepad 2 start ------------------------------------------------*/
        if (gamepad2.y) {
            telemetry.log().add("button y pressed");
            Drone.setPosition(1);
        }

        if (gamepad2.right_stick_button) {
            telemetry.log().add("outtake activated");
            outtake_extension.setPower(1);
        }

        if (gamepad2.left_stick_button) {
            telemetry.log().add("intake activated");
            Intake.setPower(1);
        }

    }

}
