/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Random;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Beg extends ListenerAdapter {
	
	@SuppressWarnings("deprecation")
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
         if (e.getAuthor().isBot() && !e.getAuthor().equals(e.getJDA().getSelfUser())|| e.getAuthor().isFake()) {
			return;
		}
		String[] args = e.getMessage().getContentRaw().split(" ");
		if (args[0].equalsIgnoreCase("!beg")) {
			try (Connection con = DriverManager.getConnection("jdbc:mysql://xx/CCBot?serverTimezone=UTC", "ccbot", "zs4GCaL")) {
			      ResultSet rs = con.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+e.getAuthor().getId());
			      Calendar cal = Calendar.getInstance();
			      cal.add(Calendar.MINUTE, -2);
			      while (rs.next())
			      {
			        long coins = rs.getLong("coins");
			        long daily = rs.getLong("begbonus");
			        if (daily < cal.getTimeInMillis()) {
				        int rand = new Random().nextInt((200) + 200);
				        long newbux = coins + rand;
				        if (coins != -1337) {
				        	con.prepareStatement("UPDATE userinformations SET coins = "+newbux+" WHERE userids = "+e.getMember().getUser().getId()).execute();
				        }
				        con.prepareStatement("UPDATE userinformations SET begbonus = "+Calendar.getInstance().getTimeInMillis()+" WHERE userids = "+e.getMember().getUser().getId()).execute();
				        e.getChannel().sendMessage("Man you really dont have a lot money. Anyways here get **"+rand+"**<:ccoin:753994248885633046>").queue();
			        } else {
			        	Calendar claimed = Calendar.getInstance();
			        	claimed.setTimeInMillis(daily);
			        	long secs = ChronoUnit.SECONDS.between(cal.toInstant(), claimed.toInstant());
			        	int mins = 0;
			        	while (secs >= 60) {
			        		mins++;
			        		secs = secs - 60;
			        	}
			        	e.getChannel().sendMessage("Bro stop begging all the time! "
			        			+ "You can beg again in **"+mins+"** minutes and **"+secs+"** seconds!").queue();
			        }
			      }
			      rs.close();
			      con.close();
			} catch (SQLException e1) {
				e.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e1.toString()).queue();
			}
		}
	}

}
