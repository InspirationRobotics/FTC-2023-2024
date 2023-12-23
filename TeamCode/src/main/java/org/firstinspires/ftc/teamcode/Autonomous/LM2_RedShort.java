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
public class LM2_RedShort extends LinearOpMode
{
    OpenCvWebcam webcam;

    final int LEFT = 1;
    final int CENTER = 2;
    final int RIGHT = 3;

    int pixelLocation = 0;

    public Servo gripper = null;
    public Servo flipper = null;


    @Override
    public void runOpMode()
    {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        flipper = hardwareMap.get(Servo.class, "flipper");
        gripper = hardwareMap.get(Servo.class, "gripper");
        /*
         * Instantiate an OpenCvCamera object for the camera we'll be using.
         * In this sample, we're using a webcam. Note that you will need to
         * make sure you have added the webcam to your configuration file and
         * adjusted the name here to match what you named it in said config file.
         *
         * We pass it the view that we wish to use for camera monitor (on
         * the RC phone). If no camera monitor is desired, use the alternate
         * single-parameter constructor instead (commented out below)
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        // OR...  Do Not Activate the Camera Monitor View
        //webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"));

        /*
         * Specify the image processing pipeline we wish to invoke upon receipt
         * of a frame from the camera. Note that switching pipelines on-the-fly
         * (while a streaming session is in flight) *IS* supported.
         */
        webcam.setPipeline(new SamplePipeline());

        /*
         * Open the connection to the camera device. New in v1.4.0 is the ability
         * to open the camera asynchronously, and this is now the recommended way
         * to do it. The benefits of opening async include faster init time, and
         * better behavior when pressing stop during init (i.e. less of a chance
         * of tripping the stuck watchdog)
         *
         * If you really want to open synchronously, the old method is still available.
         */
        webcam.setMillisecondsPermissionTimeout(5000); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                /*
                 * Tell the webcam to start streaming images to us! Note that you must make sure
                 * the resolution you specify is supported by the camera. If it is not, an exception
                 * will be thrown.
                 *
                 * Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
                 * supports streaming from the webcam in the uncompressed YUV image format. This means
                 * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
                 * Streaming at e.g. 720p will limit you to up to 10FPS and so on and so forth.
                 *
                 * Also, we specify the rotation that the webcam is used in. This is so that the image
                 * from the camera sensor can be rotated such that it is always displayed with the image upright.
                 * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                 * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                 * away from the user.
                 */
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        telemetry.addLine("Waiting for start");
        telemetry.update();

        gripper.setPosition(0);
        flipper.setPosition(0.8);

        /*
         * Wait for the user to press start on the Driver Station
         */
        waitForStart();

        while (opModeIsActive())
        {
            /*
             * Send some stats to the telemetry
             */
            telemetry.addData("Frame Count", webcam.getFrameCount());
            telemetry.addData("FPS", String.format("%.2f", webcam.getFps()));
            telemetry.addData("Total frame time ms", webcam.getTotalFrameTimeMs());
            telemetry.addData("Pipeline time ms", webcam.getPipelineTimeMs());
            telemetry.addData("Overhead time ms", webcam.getOverheadTimeMs());
            telemetry.addData("Theoretical max FPS", webcam.getCurrentPipelineMaxFps());
            telemetry.update();

            if (pixelLocation == CENTER) {
                TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(new Pose2d()) // for red short
                        .forward(32)
                        .waitSeconds(0.5)
                        .back(5)
                        .waitSeconds(.5)
                        .lineToLinearHeading(new Pose2d(38, -44, Math.toRadians(94)))
                        .waitSeconds(0.5)
                        .build();
                drive.followTrajectorySequence(trajSeq);
                flipper.setPosition(0.4);
                gripper.setPosition(0.5);
                sleep(1000);
                trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .forward(2)
                        .strafeLeft(30)
                        .build();
                drive.followTrajectorySequence(trajSeq);
                flipper.setPosition(0.8);
                trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .back(12)
                        .build();
                drive.followTrajectorySequence(trajSeq);
                flipper.setPosition(0.8);
                break;
        }

            if (pixelLocation == RIGHT) {
                TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(new Pose2d())
                        .strafeTo(new Vector2d(25, -8.5))
                        .back(5)
                        .lineToLinearHeading(new Pose2d(29, -42, Math.toRadians(94)))
                        .build();
                drive.followTrajectorySequence(trajSeq);
                flipper.setPosition(0.4);
                gripper.setPosition(0.5);
                sleep(1000);
                trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .forward(2)
                        .strafeLeft(25)
                        .build();
                drive.followTrajectorySequence(trajSeq);
                flipper.setPosition(0.8);
                trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .back(12)
                        .build();
                drive.followTrajectorySequence(trajSeq);
                flipper.setPosition(0.8);
                break;
            }

            if (pixelLocation == LEFT) {
                TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(new Pose2d())
                        .lineTo(new Vector2d(29,0))
                        .waitSeconds(0.5)
                        .turn(Math.toRadians(94))
                        .build();
                drive.followTrajectorySequence(trajSeq);
                trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .forward(8)
                        .waitSeconds(0.5)
                        .back(45)
                        .strafeRight(6)
                        .build();
                drive.followTrajectorySequence(trajSeq);
                flipper.setPosition(0.4);
                gripper.setPosition(0.5);
                sleep(1000);
                trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .forward(2)
                        .strafeLeft(30)
                        .build();
                drive.followTrajectorySequence(trajSeq);
                flipper.setPosition(0.8);
                trajSeq = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .back(12)
                        .build();
                drive.followTrajectorySequence(trajSeq);
                flipper.setPosition(0.8);
                break;

            }




            /*
             * For the purposes of this sample, throttle ourselves to 10Hz loop to avoid burning
             * excess CPU cycles for no reason. (By default, telemetry is only sent to the DS at 4Hz
             * anyway). Of course in a real OpMode you will likely not want to do this.
             */
            sleep(100);
        }
    }

    /*
     * An example image processing pipeline to be run upon receipt of each frame from the camera.
     * Note that the processFrame() method is called serially from the frame worker thread -
     * that is, a new camera frame will not come in while you're still processing a previous one.
     * In other words, the processFrame() method will never be called multiple times simultaneously.
     *
     * However, the rendering of your processed image to the viewport is done in parallel to the
     * frame worker thread. That is, the amount of time it takes to render the image to the
     * viewport does NOT impact the amount of frames per second that your pipeline can process.
     *
     * IMPORTANT NOTE: this pipeline is NOT invoked on your OpMode thread. It is invoked on the
     * frame worker thread. This should not be a problem in the vast majority of cases. However,
     * if you're doing something weird where you do need it synchronized with your OpMode thread,
     * then you will need to account for that accordingly.
     */
    class SamplePipeline extends OpenCvPipeline
    {
        boolean viewportPaused;

        //Defining a rectangle example: Rect exampleRect = new Rect ( x starting location, y starting location, width, height)
        Rect rightRect = new Rect (260,60,45, 45);
        Rect centerRect = new Rect (110, 60, 45, 45);

        // creating a variable to store the color of the rectangle
        Scalar rectColor = new Scalar (0.0, 0.0, 255.0);

        Mat centerCrop; //empty matrix
        Mat rightCrop; //empty matrix
        double centerAvg; //avg for rgb (it determines what color)
        double rightAvg; //avg for rgb (it determines what color)


        /*
         * NOTE: if you wish to use additional Mat objects in your processing pipeline, it is
         * highly recommended to declare them here as instance variables and re-use them for
         * each invocation of processFrame(), rather than declaring them as new local variables
         * each time through processFrame(). This removes the danger of causing a memory leak
         * by forgetting to call mat.release(), and it also reduces memory pressure by not
         * constantly allocating and freeing large chunks of memory.
         */

        @Override
        public Mat processFrame(Mat input)
        {
            /*
             * IMPORTANT NOTE: the input Mat that is passed in as a parameter to this method
             * will only dereference to the same image for the duration of this particular
             * invocation of this method. That is, if for some reason you'd like to save a copy
             * of this particular frame for later use, you will need to either clone it or copy
             * it to another Mat.
             */

            Imgproc.rectangle(input, centerRect, rectColor, 3);
            Imgproc.rectangle(input, rightRect, rectColor, 3);

            //grabs the pixels ONLY in the sub matrix (centerRect or rightRect)
            centerCrop = input.submat(centerRect);
            rightCrop = input.submat(rightRect);

            //average RGB of the sub matrix
            Scalar centerRGBavg = Core.mean(centerCrop);
            Scalar leftRGBavg = Core.mean(rightCrop);

            //algorithm for detecting red
            centerAvg = 2*centerRGBavg.val[0] - centerRGBavg.val[1] - centerRGBavg.val[2];
            rightAvg = 2*leftRGBavg.val[0] - leftRGBavg.val[1] - leftRGBavg.val[2];

            // 0 = red, 1 = green, 2 = blue

            // the value at which we say, neither pixel region is red (must be on the left)
            double threshold = 10.0;

            if(centerAvg > rightAvg && centerAvg >= threshold){
                telemetry.addLine("Prop Location: Center");
                pixelLocation = CENTER;
            }
            else if (rightAvg > centerAvg && rightAvg >= threshold){
                telemetry.addLine("Prop Location: Right");
                pixelLocation = RIGHT;
            }
            else{
                telemetry.addLine("Prop Location: Left");
                pixelLocation = LEFT;
            }

            telemetry.addLine("Center: " + centerAvg);
            telemetry.addLine("Right: " + rightAvg);



            /**
             * NOTE: to see how to get data from your pipeline to your OpMode as well as how
             * to change which stage of the pipeline is rendered to the viewport when it is
             * tapped, please see {@link PipelineStageSwitchingExample}
             */

            return input;
        }

        @Override
        public void onViewportTapped()
        {
            /*
             * The viewport (if one was specified in the constructor) can also be dynamically "paused"
             * and "resumed". The primary use case of this is to reduce CPU, memory, and power load
             * when you need your vision pipeline running, but do not require a live preview on the
             * robot controller screen. For instance, this could be useful if you wish to see the live
             * camera preview as you are initializing your robot, but you no longer require the live
             * preview after you have finished your initialization process; pausing the viewport does
             * not stop running your pipeline.
             *
             * Here we demonstrate dynamically pausing/resuming the viewport when the user taps it
             */

            viewportPaused = !viewportPaused;

            if(viewportPaused)
            {
                webcam.pauseViewport();
            }
            else
            {
                webcam.resumeViewport();
            }
        }
    }
}