package org.aluminati3555.lib.util;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;

import org.aluminati3555.lib.data.AluminatiData;
import org.aluminati3555.lib.drivers.AluminatiTalonSRX;

/**
 * This class provides utilities
 * 
 * @author Caleb Heydon
 */
public class AluminatiUtil {
    /**
     * Config the talons for path following
     * 
     * @param left
     * @param right
     */
    public static void configTalonsPathFollowing(AluminatiTalonSRX left, AluminatiTalonSRX right) {
        // Configure left talon
        left.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        left.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5);

        // Configure right talon for velocity mode
        right.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        right.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5);

        // Configure pid for velocity mode (used in path following)
        left.config_kF(0, AluminatiData.encoderF);
        left.config_kP(0, AluminatiData.encoderP);
        left.config_kI(0, AluminatiData.encoderI);
        left.config_kD(0, AluminatiData.encoderD);
        left.config_IntegralZone(0, AluminatiData.iZone);

        right.config_kF(0, AluminatiData.encoderF);
        right.config_kP(0, AluminatiData.encoderP);
        right.config_kI(0, AluminatiData.encoderI);
        right.config_kD(0, AluminatiData.encoderD);
        right.config_IntegralZone(0, AluminatiData.iZone);
    }

    /**
     * This function converts encoder ticks to inches travelled
     * 
     * @param x
     * @return
     */
    public static double inchesToEncoderTicks(double x) {
        double circumference = Math.PI * AluminatiData.wheelDiamater;
        return (x / circumference) * AluminatiData.encoderUnitsPerRotation;
    }

    /**
     * This function converts inches to meters
     */
    public static double inchesToMeters(double inches) {
        return inches * 0.0254;
    }

    /**
     * Converts rotations to inches
     */
    public static double rotationsToInches(double rotations) {
        return rotations * (AluminatiData.wheelDiamater * Math.PI);
    }

    /**
     * Converts rpm to inches per second
     */
    public static double rpmToInchesPerSecond(double rpm) {
        return rotationsToInches(rpm) / 60;
    }

    /**
     * Converts rpm to native units
     */
    public static int convertRPMToNativeUnits(double rpm) {
        return (int) (rpm * AluminatiData.encoderUnitsPerRotation / 600.0);
    }

    /**
     * Converts native units to rpm
     */
    public static int convertNativeUnitsToRPM(double nativeUnits) {
        return (int) (nativeUnits / AluminatiData.encoderUnitsPerRotation * 600.0);
    }

    /**
     * Converts inches to rotations
     */
    public static double inchesToRotations(double inches) {
        return inches / (AluminatiData.wheelDiamater * Math.PI);
    }

    /**
     * Converts inches per second to rpm
     */
    public static double inchesPerSecondToRPM(double inchesPerSecond) {
        return inchesToRotations(inchesPerSecond) * 60;
    }

    /**
     * Updates the path following feedforward values
     */
    public static void generatePathFollowingFeedforwardValues() {
        AluminatiData.pathFollowingProfileKFFV = 1 / AluminatiData.pathFollowingMaxVel;
        AluminatiData.pathFollowingProfileKFFA = 1 / AluminatiData.pathFollowingMaxAccel;
    }
}
