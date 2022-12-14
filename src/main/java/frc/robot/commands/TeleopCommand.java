package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiDrivetrain;

public class TeleopCommand extends CommandBase {
    private final Joystick joystick = new Joystick(0);
    private final RomiDrivetrain drivetrain;

    public TeleopCommand(RomiDrivetrain robotDrivetrain) {
        drivetrain = robotDrivetrain;

        this.addRequirements(this.drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.resetEncoders();
    }

    @Override
    public void execute() {
        System.out.println(joystick.getThrottle());
        //drivetrain.arcadeDrive(joystick.getThrottle(), 0.0);
    }

    @Override
    public void end(boolean interrupted) {}

  // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
