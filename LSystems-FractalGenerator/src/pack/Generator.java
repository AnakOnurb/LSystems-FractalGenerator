package pack;

/*
 * F: Draw a line and move forward
 * G: Move forward (without drawing a line)
 * +: Turn right
 * -: Turn left
 * [: Save current location
 * ]: Restore previous location
 */
public class Generator 
{
	private static String current = "";
	private static String translationF;
	private static String translationG;
	
	public static void Begin(String init, String translationFF, String translationGG)
	{
		current = init;
		translationF = translationFF;
		translationG = translationGG;
	}
	
	public static String Read(int iterations)
	{
		for(int n = 0; n < iterations; n++)
		{
			StringBuffer next = new StringBuffer();
			for (int i = 0; i < current.length(); i++) 
			{
				char c = current.charAt(i);
			
				//Production rule F --> FG	
				if (c == 'F')
					next.append(translationF);
				//Production rule G --> F	
				else if (c == 'G')
					next.append(translationG);
			}		
			current = next.toString();	
		}
		return current;
	}
}
