// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.sensors.RomiGyro;
import frc.robot.subsystems.RomiDrivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

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
    double currentStep = Constants.STEPS[step];
    setMovement();

    if (step % 2 == 0) {
      if (drivetrain.getLeftDistanceInch() >= currentStep
          || drivetrain.getRightDistanceInch() >= currentStep)
      {
            step++;
      }
    }
    else {
      if (gyro.getAngleZ() >= currentStep)
      {
        step++;
      }
    }

  }

  private void setMovement() {
    if (step % 2 == 0)
    {
      drivetrain.arcadeDrive(1.0, 0.0);
    }
    else {
      drivetrain.arcadeDrive(0.0, 1.0);
    }
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
