package org.firstinspires.ftc.teamcode.Testing;

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

@TeleOp(name="Bucket Servo Positioning", group="Pushbot")
public class Bucket_Servo_Positioning extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    public Servo right_bucket = null;

    public Servo left_bucket = null;
    public static final double timeShift = 0.1;
    public static final double RIGHT_SERVO_POSITION_1 = 0.3 + timeShift;
    public static final double RIGHT_SERVO_POSITION_2 = 0.9;
    public static final double LEFT_SERVO_POSITION_1 = 0.3;
    public static final double LEFT_SERVO_POSITION_2 = 0.9 - timeShift;



    public void init() {


        right_bucket = hardwareMap.servo.get("right_bucket");
        left_bucket = hardwareMap.servo.get("left_bucket");

        telemetry.addData("Say", "Hello Driver");//
        right_bucket.setDirection(Servo.Direction.FORWARD);
        left_bucket.setDirection(Servo.Direction.REVERSE);

    }



    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void loop() {
        /* gamepad 1 start ------------------------------------------------*/
        if (gamepad1.a) {
            right_bucket.setPosition(RIGHT_SERVO_POSITION_1);
            left_bucket.setPosition(LEFT_SERVO_POSITION_1);
            telemetry.addData("In:", "Position 1");
        }
        if(gamepad1.x){
            right_bucket.setPosition(RIGHT_SERVO_POSITION_2);
            left_bucket.setPosition(LEFT_SERVO_POSITION_2);
            telemetry.addData("In:", "Position 2");
        }
    }

}

