/*
class: Settings

The Settings class provides a convenient location to store any data that may otherwise be
hardcoded. This prevents the code from being littered with "magic" numbers and other
kinds of arbitrary data. All of the values contained in this class should be treated as
read-only, but this policy is not enforced.

The Settings class also provides an interface for saving and loading data for use on
later executions of the program.
*/
class Settings
{
	public static int DefaultTeamSize = 3;
	public static AssignmentMethod DefaultAssignmentMethod = AssignmentMethod.RANDOM;

	public static void loadSettings()
	{
	}

	public static void loadSettings(String settingsFile)
	{
	}

	public static void saveSettings()
	{
	}

	public static void saveSettings(String settingsFile)
	{
	}
}

