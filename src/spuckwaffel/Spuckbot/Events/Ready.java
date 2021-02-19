/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Events;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class Ready extends ListenerAdapter {

	@Override
	public void onReady(ReadyEvent event) {
		EmbedBuilder uselessstats = new EmbedBuilder();
		uselessstats.setTitle("The bot woke up! Here are some useless stats");
		
		Date currentDate = new Date();
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

		Main.todaysdate = dateformat.format(currentDate);
		
		Date currentTime = new Date();
		
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
		uselessstats.setDescription("current date:" +dateformat.format(currentDate) + "\n"
								+ "current time:" +timeformat.format(currentTime));
		uselessstats.setColor(0x7ffc03);
		event.getJDA().getGuildById("703751815077822564").getTextChannelById("743438841293045800").sendMessage(uselessstats.build()).queue();	
		Main.todaystime = timeformat.format(currentTime);
	}
}
