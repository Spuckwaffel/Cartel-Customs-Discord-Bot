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

public class Daily extends ListenerAdapter {
	
	@SuppressWarnings("deprecation")
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
         if (e.getAuthor().isBot() && !e.getAuthor().equals(e.getJDA().getSelfUser())|| e.getAuthor().isFake()) {
			return;
		}
		String[] args = e.getMessage().getContentRaw().split(" ");
		if (args[0].equalsIgnoreCase("!daily")) {
			try (Connection con = DriverManager.getConnection("jdbc:mysql://xx/CCBot?serverTimezone=UTC", "ccbot", "zs4GCaL")) {
			      ResultSet rs = con.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+e.getAuthor().getId());
			      Calendar cal = Calendar.getInstance();
			      cal.add(Calendar.DAY_OF_MONTH, -1);
			      while (rs.next())
			      {
			        long coins = rs.getLong("coins");
			        long daily = rs.getLong("dailybonus");
			        if (daily < cal.getTimeInMillis()) {
				        int rand = new Random().nextInt((3000) + 1000);
				        long newbux = coins + rand;
				        if (coins != -1337) {
				        	con.prepareStatement("UPDATE userinformations SET coins = "+newbux+" WHERE userids = "+e.getMember().getUser().getId()).execute();
				        }
				        con.prepareStatement("UPDATE userinformations SET dailybonus = "+Calendar.getInstance().getTimeInMillis()+" WHERE userids = "+e.getMember().getUser().getId()).execute();
				        e.getChannel().sendMessage("You claimed your daily reward of **"+rand+"**<:ccoin:753994248885633046>").queue();
			        } else {
			        	Calendar claimed = Calendar.getInstance();
			        	claimed.setTimeInMillis(daily);
			        	long mins = ChronoUnit.MINUTES.between(cal.toInstant(), claimed.toInstant());
			        	int hours = 0;
			        	while (mins >= 60) {
			        		hours++;
			        		mins = mins - 60;
			        	}
			        	e.getChannel().sendMessage("You have already claimed your daily reward for today! "
			        			+ "You can claim another one in **"+hours+"** hours and **"+mins+"** minutes!").queue();
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

