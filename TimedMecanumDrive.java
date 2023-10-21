package org.firstinspires.ftc.teamcode;

public abstract class TimedMecanumDrive extends HardwareMap_Inspire {

    public void MoveForward(long milliseconds, double speed) throws InterruptedException{
        leftFront.setPower(speed);
        leftBack.setPower(speed);
        rightBack.setPower(speed);
        rightFront.setPower(speed);
        Thread.sleep(milliseconds);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }
    public void MoveBackward(long milliseconds, double speed) throws InterruptedException{
        leftFront.setPower(-speed);
        leftBack.setPower(-speed);
        rightBack.setPower(-speed);
        rightFront.setPower(-speed);
        Thread.sleep(milliseconds);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }
    public void TurnRight(long milliseconds, double speed) throws InterruptedException{
        leftFront.setPower(speed);
        leftBack.setPower(speed);
        rightBack.setPower(-speed);
        rightFront.setPower(-speed);
        Thread.sleep(milliseconds);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }
    public void TurnLeft(long milliseconds, double speed) throws InterruptedException{
        leftFront.setPower(-speed);
        leftBack.setPower(-speed);
        rightBack.setPower(speed);
        rightFront.setPower(speed);
        Thread.sleep(milliseconds);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }
    public void StrafeLeft(long milliseconds, double speed) throws InterruptedException{
        leftFront.setPower(-speed);
        leftBack.setPower(speed);
        rightBack.setPower(-speed);
        rightFront.setPower(speed);
        Thread.sleep(milliseconds);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }
    public void StrafeRight(long milliseconds, double speed) throws InterruptedException{
        leftFront.setPower(speed);
        leftBack.setPower(-speed);
        rightBack.setPower(speed);
        rightFront.setPower(-speed);
        Thread.sleep(milliseconds);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void spinIntake(long milliseconds, double speed) throws InterruptedException{
        intake.setPower(speed);
        Thread.sleep(milliseconds);
        intake.setPower(0);
    }

    public void extend(long milliseconds, double speed) throws InterruptedException{
        extension.setPower(speed);
        Thread.sleep(milliseconds);
        extension.setPower(0);
    }

    public abstract void start();

    public abstract void loop() throws InterruptedException;
}