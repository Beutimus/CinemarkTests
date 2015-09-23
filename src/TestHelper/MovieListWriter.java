package TestHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.*;
import org.junit.Assert;

import Movie.MovieInfo;

public class MovieListWriter {

	public static void newWrite(List<MovieInfo> list)
	{
		// Date format objects
		SimpleDateFormat dayOnly = new SimpleDateFormat("EEE, MMM d yyyy");
		SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm a");
		
		// File information
		String fileName = "C:\\" + System.currentTimeMillis() + ".txt";
		RandomAccessFile file = null;
		
		try
		{		
			// First build the JSON objects
			JSONObject allMovieInfo = new JSONObject();
		
			JSONArray movieList = new JSONArray();
		
			// All of the movie information
			for(MovieInfo movie: list)
			{
				JSONObject movieJSON = new JSONObject();
				movieJSON.put("MovieName", movie.getName());
				movieJSON.put("ImageURL", movie.getURL());
				
				List<Date> dates = movie.getMovieTimes();
				
				movieJSON.put("Show Date", dayOnly.format(dates.get(0)));
				
				JSONArray dateList = new JSONArray();
				
				for(Date date: dates)
				{
					dateList.put(timeOnly.format(date));
				}
				
				movieJSON.put("ShowTimes", dateList);
				
				movieList.put(movieJSON);
			}
		
			allMovieInfo.put("MovieInfo", movieList);
			
			String allMovieInfoString = allMovieInfo.toString(3);
			
			System.out.println(allMovieInfo.toString());
			
			// Setup File information
			file = new RandomAccessFile(fileName,"rw");
						
			FileChannel outChannel = file.getChannel();
					
			ByteBuffer buf = ByteBuffer.allocate(allMovieInfoString.length() + 5);
			buf.clear();
			
			buf.put(allMovieInfoString.getBytes());
			
			buf.flip();
			
			while (buf.hasRemaining()) {
				outChannel.write(buf);
			}
			
			outChannel.force(true);
			file.close();
		}
		catch(JSONException e)
		{
			e.printStackTrace();
			Assert.assertFalse("JSON Exception", true);
		}
		catch(Exception e)
		{
			;
		}
	}
	
	public static void writeMovies(List<MovieInfo> list)
	{
	// Output the list of movies
			String leftCurly = new String("{");
			String rightCurly = new String("}");
			String leftBracket = new String("[");
			String rightBracket = new String("]");
			String comma = new String(",");
			String spacer = new String("   ");
			String newLine = new String (System.getProperty("line.separator"));
			String movieInfo = new String("\"MovieInfo\": ");
			String movieName = new String("\"MovieName\": ");
			String imgURL = new String("\"ImageURL\": ");
			String date = new String("\"Show Date\": ");
			String quote = new String("\"");
			String showTimes = new String("\"Show Times\": ");
			
			String fileName = "C:\\" + System.currentTimeMillis() + ".txt";
			
			SimpleDateFormat dayOnly = new SimpleDateFormat("EEE, MMM d yyyy");
			SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm a");
			
			RandomAccessFile file = null;
			
			try
			{					
				file = new RandomAccessFile(fileName,"rw");
			
				FileChannel outChannel = file.getChannel();
			
				ByteBuffer buf = ByteBuffer.allocate(1000);
				buf.clear();
				
				// Begin main JSON section
				buf.put(leftCurly.getBytes());
				buf.put(newLine.getBytes());
				buf.put(spacer.getBytes());
				buf.put(movieInfo.getBytes());
				buf.put(leftBracket.getBytes());
				buf.put(newLine.getBytes());
				
				buf.flip();
				
				while (buf.hasRemaining()) {
					outChannel.write(buf);
				}
				
				buf.clear();
				
				// Output each movie's information
				for(int i = 1; i < list.size(); i++)
				{
					MovieInfo info = list.get(i);
					
					// Begin movie information
					buf.put(spacer.getBytes());				
					buf.put(leftCurly.getBytes());
					buf.put(newLine.getBytes());
					
					buf.put(spacer.getBytes());
					buf.put(spacer.getBytes());					
					buf.put(movieName.getBytes());
					buf.put(quote.getBytes());
					buf.put(info.getName().getBytes());
					buf.put(quote.getBytes());
					buf.put(comma.getBytes());
					buf.put(newLine.getBytes());
					
					buf.put(spacer.getBytes());
					buf.put(spacer.getBytes());				
					buf.put(imgURL.getBytes());
					buf.put(quote.getBytes());
					buf.put(info.getURL().getBytes());
					buf.put(quote.getBytes());
					buf.put(comma.getBytes());
					buf.put(newLine.getBytes());
					
					// Begin outputting date information
					List<Date> dates = info.getMovieTimes();				
					
					buf.put(spacer.getBytes());
					buf.put(spacer.getBytes());
					buf.put(date.getBytes());
					
					buf.put(quote.getBytes());
					
					if (dates.size() > 0)
					{					
						buf.put(dayOnly.format(dates.get(0)).getBytes());
					}
					
					buf.put(quote.getBytes());
					buf.put(comma.getBytes());
					
					buf.put(newLine.getBytes());
					
					// Show time information
					buf.put(spacer.getBytes());
					buf.put(spacer.getBytes());
					buf.put(showTimes.getBytes());
					buf.put(leftBracket.getBytes());
					buf.put(newLine.getBytes());
					
					for(int showTimeIndex = 0; showTimeIndex < dates.size(); showTimeIndex++)
					{
						buf.put(spacer.getBytes());
						buf.put(spacer.getBytes());
						buf.put(spacer.getBytes());
						buf.put(showTimes.getBytes());
						buf.put(quote.getBytes());
						buf.put(timeOnly.format(dates.get(showTimeIndex)).getBytes());
						buf.put(quote.getBytes());
						
						if (showTimeIndex < dates.size() - 1)
						{
							buf.put(comma.getBytes());
						}
						
						buf.put(newLine.getBytes());
					}
					
					buf.put(spacer.getBytes());
					buf.put(spacer.getBytes());
					buf.put(rightBracket.getBytes());
					buf.put(newLine.getBytes());
					
					// End movie information
					buf.put(spacer.getBytes());
					buf.put(rightCurly.getBytes());
					
					if (i < list.size() - 1)
					{
						buf.put(comma.getBytes());
					}
					
					buf.put(newLine.getBytes());
					
					buf.flip();
					
					while (buf.hasRemaining()) {
						outChannel.write(buf);
					}
					
					buf.clear();
				}
				
				buf.put(spacer.getBytes());
				buf.put(rightBracket.getBytes());
				buf.put(newLine.getBytes());
				buf.put(rightCurly.getBytes());
				buf.flip();
				outChannel.write(buf);
				
				buf.clear();

				outChannel.force(true);
				file.close();
			}
			catch (FileNotFoundException e)
			{
				System.out.println(fileName + "\n" + e.getMessage());
				e.printStackTrace();
				Assert.assertTrue(false);
			}
			catch (IOException e)
			{
				System.out.println(fileName + "\n" + e.getMessage());
				e.printStackTrace();
				Assert.assertTrue(false);
			}

	}
}
