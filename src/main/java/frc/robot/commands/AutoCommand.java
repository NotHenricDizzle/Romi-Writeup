// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.sensors.RomiGyro;
import frc.robot.subsystems.RomiDrivetrain;

import javax.tools.Diagnostic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Step;
import frc.robot.StepType;

/** An example command that uses an example subsystem. */
public class AutoCommand extends CommandBase {
  private final RomiDrivetrain drivetrain;
  private final RomiGyro gyro;

  // to end your auto command, set this to true.
  private boolean auto_finished = false;

  private int step = 0;

  // DO NOT CHANGE
  public AutoCommand(RomiDrivetrain drivetrain, RomiGyro gyro) {
    this.drivetrain = drivetrain;
    this.gyro = gyro;

    addRequirements(this.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.resetEncoders();
    gyro.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // this is where most of your logic will be.
      Step currentStep = Constants.STEPS[step];

      boolean stepComplete;

      switch(currentStep.type) {
        case MOVE:
          stepComplete = moveDistance(currentStep.value);
          break;
        case TURN:
          stepComplete = turnAngle(currentStep.value);
          break;
        default:
          System.out.println("WARNING: UNRECONGIZED TURN TYPE");
          stepComplete = true;
          break;
      }

      if (stepComplete) {
        nextStep();
      }

      if (step >= Constants.STEPS.length)
      {
        auto_finished = true;
      }
  }

  // Returns true if the step is completed
  private boolean moveDistance(double distance) {
    double driftCorrection = -0.1;

    boolean moveForward = distance > 0;
    distance = Math.abs(distance);

    // TODO: format this bool better?
    boolean exceededDistance = 
      Math.abs(drivetrain.getLeftDistanceInch()) >= distance
      || Math.abs(drivetrain.getRightDistanceInch()) >= distance;

    if (!exceededDistance) {
      if (moveForward) {
        drivetrain.arcadeDrive(1.0, driftCorrection * gyro.getAngleZ());
      }
      else {
        drivetrain.arcadeDrive(-1.0, driftCorrection * gyro.getAngleZ());
      }
    }

    return exceededDistance;
  }

  private boolean turnAngle(double angle) {
    boolean turnRight = angle > 0;

    angle = Math.abs(angle);

    boolean exceededAngle = Math.abs(gyro.getAngleZ()) > angle;

    if (!exceededAngle) {
      if (turnRight) {
        drivetrain.arcadeDrive(0.0, 1.0);
      }
      else {
        drivetrain.arcadeDrive(0.0, -1.0);
      }
    }

    return exceededAngle;
  }

  private void nextStep()
  {
    step++;
    drivetrain.resetEncoders();
    gyro.reset();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return auto_finished;
  }
}
