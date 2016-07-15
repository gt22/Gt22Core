package com.gt22.gt22core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;

public class FileUtils
{
	
	/**
	 * Create new BufferredReader for file
	 */
	public static BufferedReader createReader(File file) throws FileNotFoundException
	{
		return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	}
	
	/**
	 * Create new BufferredWriter for file
	 */
	public static BufferedWriter createWriter(File file) throws FileNotFoundException
	{
		return createWriter(file, true);
	}
	
	/**
	 * Create new BufferredWriter for file
	 * @param append file, or rewrite it using function wirte();
	 */
	public static BufferedWriter createWriter(File file, boolean append) throws FileNotFoundException
	{
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append)));
	}
	
	/**
	 * Crates file if it not created
	 * @param file
	 */
	public static void initFile(File file)
	{
		if(!file.canWrite())
		{
			try
			{
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			catch(IOException e)
			{
				throw new ReportedException(CrashReport.makeCrashReport(e, "Unable to generate structure config"));
			}
		}
	}
	
	/**
	 * Copy content of one file int another, files must exist, use {@link #initFile(File)}
	 * @param to
	 * @param from
	 * @throws IOException
	 */
	public static void copyFile(File to, File from) throws IOException
	{
		BufferedReader reader = createReader(from);
		BufferedWriter writer = createWriter(to);
		StringBuffer buffer = new StringBuffer("");
		while(true)
		{
			String temp = reader.readLine();
			if(temp == null)
			{
				break;
			}
			buffer.append(temp + "\n");
		}
		writer.write(buffer.toString());
	}
	
	/**
	 * Add line to file
	 * @param file
	 * @param line
	 */
	public static void addLine(File file, String line) throws IOException
	{
		addLine(file, line, true);
	}
	
	/**
	 * Add line to file
	 * @param file
	 * @param line
	 * @param breakLine - insert '\n' or not
	 */
	public static void addLine(File file, String line, boolean breakLine) throws IOException
	{
		BufferedWriter writer = createWriter(file);
		writer.write(line);
		if(breakLine)
		{
			writer.write('\n');
		}
		writer.close();
	}
	
	public static void deleteLine(File file, int linetodelete)
	{
		try
		{
			BufferedReader br= createReader(file);
			StringBuffer sb=new StringBuffer("");
			int linenumber=1;
			String line;
 
			while((line=br.readLine())!=null)
			{
				if(linenumber != linetodelete)
					sb.append(line+"\n");
				linenumber++;
			}
			br.close();
			BufferedWriter fw = createWriter(file, false);
			fw.write(sb.toString());
			fw.close();
		}
		catch (Exception e)
		{
			System.out.println("Something went horribly wrong: " + e.getMessage());
		}
	}
}
