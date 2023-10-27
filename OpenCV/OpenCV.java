package org.firstinspires.ftc.teamcode.OpenCV;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@TeleOp (name="open_cv", group="Pushbot")
public class OpenCV extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat ();
    Mat leftMat = new Mat();
    Mat rightMat = new Mat ();

    Rect leftROI = new Rect
            (new Point(60, 35),
                    new Point(120, 75));

    Rect rightROI = new Rect
            (new Point(140, 35),
                    new Point(200, 75));

    public OpenCV(Telemetry t) { telemetry = t;}

    @Override
    public Mat processFrame (Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        // if team prop = blue
        Scalar lowHSV_blue = new Scalar(240,100,78.4);
        Scalar highHSV_blue = new Scalar(240,100,98);
        // if team prop = red
        Scalar lowHSV_red = new Scalar(0,100,88.2);
        Scalar highHSV_red = new Scalar(0,100,100);

        Core.inRange(mat, lowHSV_blue, highHSV_blue, mat);
        Core.inRange(mat, lowHSV_red, highHSV_red, mat);

        leftMat = mat.submat(leftROI);
        rightMat = mat.submat(rightROI);


        return input;
    }
}
