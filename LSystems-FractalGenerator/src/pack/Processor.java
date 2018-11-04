package pack;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.text.SimpleDateFormat;


public class Processor 
{	
	private static int len = 20;
	private static List<Double> matrixX = new ArrayList<Double>();
	private static List<Double> matrixY = new ArrayList<Double>();
	private static double PI = Math.PI;
	private static double drawAngle = PI/2;
	private static double angle = PI/2;
    private static double translationX = 0;
    private static double translationY = 0;
	
	public static void start()
	{
		String init = "";
		String translationF = "";
		String translationG = "";
		int iterations = 0;
		try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) 
		{
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    int i = 1;
		    while (line != null) 
		    {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        if(i == 1)
		        {
		        	try
		        	{
		        		iterations = Integer.parseInt(line.split(",")[0].substring(line.split(",")[0].indexOf("=")+1));
		        		int a = Integer.parseInt(line.split(",")[1].substring(line.split(",")[1].indexOf("=")+1));
		        		angle = (a*PI)/180;
		        	}
		        	catch(Exception e)
		        	{
		        		e.printStackTrace();
		        	}
		        }
		        else if(i == 2)
		        {
		        	init = line.substring(line.indexOf("=")+1);
		        }
		        else if(i == 3)
		        {
		        	translationF = line.substring(line.indexOf("=")+1);
		        }
		        else if(i == 4)
		        {
		        	translationG = line.substring(line.indexOf("=")+1);
		        }
		        i++;
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
        Generator.Begin(init, translationF, translationG);
        next(iterations);
	}
	
	public static void next(int iterations)
	{		
		String sentence = Generator.Read(iterations);
		//next.append("FF+[+F-F-F]-[-F+F+F]");	
		//next.append("F+F-F+F+F-F+F+F-F+F+F-F");
		//System.out.println(sentence);
		draw(sentence);
	}
	 
	public static void draw(String sentence)
	{
		StringBuffer svg = new StringBuffer();
		svg.append("<?xml version=\"1.0\" standalone=\"no\"?> \n");
		svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"> \n");
		translationX = 300;
		translationY = 300;
		for (int i = 0; i < sentence.length(); i++) 
		{
			//double angle = ThreadLocalRandom.current().nextDouble(-PI/2, PI/2);
			char c = sentence.charAt(i);
			if (c == 'F') 
			{
				double originX = translationX;
				double originY = translationY;
				double destinyX = (-(Math.cos(drawAngle) * len) + originX);
				double destinyY = (-(Math.sin(drawAngle) * len) + originY);
				svg.append("\t  <line x1 = \""+ originX +"\" y1 = \""+ originY +"\" x2 = \""+ destinyX +"\" y2 = \""+ destinyY +"\" stroke = \"black\" stroke-width = \"1\"/> \n");
				translationX = destinyX;
				translationY = destinyY;
			}
			else if (c == 'G') 
			{
				double originX = translationX;
				double originY = translationY;
				double destinyX = (-(Math.cos(drawAngle) * len) + originX);
				double destinyY = (-(Math.sin(drawAngle) * len) + originY);
				svg.append("\t  <line x1 = \""+ originX +"\" y1 = \""+ originY +"\" x2 = \""+ destinyX +"\" y2 = \""+ destinyY +"\" stroke = \"black\" stroke-width = \"0\"/> \n");
				translationX = destinyX;
				translationY = destinyY;
			}
			else if (c == '+') 
			{
				drawAngle -= angle;
			}
			else if (c == '-') 
			{
				drawAngle += angle;
			}
			else if (c == '[') 
			{
				push();
			}
			else if (c == ']') 
			{
				pop();
			}
		}
		
		svg.append("<\\svg> \n");
		
		//createFile
		String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime());

		String data = "Test data";
		FileOutputStream out;
		try 
		{
			out = new FileOutputStream("GeneratedFractal@" + timeStamp + ".svg");
			out.write(svg.toString().getBytes());
			out.close();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void push() 
	{
		matrixX.add(translationX);
		matrixY.add(translationY);
	}
	
	public static void pop()
	{
		translationX = matrixX.remove(matrixX.size()-1);
		translationY = matrixY.remove(matrixY.size()-1);
	}
}
