/*
 * Copyright (c) 2019 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;


@Autonomous
public class Roadrunner_Parking_Red_Long extends LinearOpMode {
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);




    public Servo gripper = null;
    public Servo flipper = null;


    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        {


            telemetry.addLine("Waiting for start");
            telemetry.update();


            /*
             * Wait for the user to press start on the Driver Station
             */
            waitForStart();

//TRAJECTORY FOR THE ROBOT THAT CANT GO UNDER THE TRUS, IT GOES THROUGH THE BARS IN THE CENTER
            TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(new Pose2d())
//                    .strafeTo(new Vector2d(25, -8.5))
                    .forward(3)
                    .waitSeconds(0.5)
                    .strafeRight(29)
                    .waitSeconds(0.5)
                    .forward(28)
                    .waitSeconds(.5)
//                    .waitSeconds(5) account for other teams who are running autonomous
                    .turn(Math.toRadians(90))
                    .waitSeconds(.5)

//                    .back(37)

                    .build();
            drive.followTrajectorySequence(trajSeq);

            sleep(1000);
            trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .back(68)
                    .strafeRight(29)
                    .waitSeconds(.5)
                    .back(10)


                    .build();
            drive.followTrajectorySequence(trajSeq);

        }
    }
}

    


    