package co.applebloom.apps.signage.video;

import java.util.Comparator;
import java.util.Random;

public class VideoItemComparator implements Comparator<VideoItem>
{
	public int compare( VideoItem vi1, VideoItem vi2 )
	{
		return Integer.compare( vi1.getPlayCount(), vi2.getPlayCount() + new Random().nextInt( 10 ) );
	}
}
