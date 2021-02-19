/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Events;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class GuildMessageReactionAdd extends ListenerAdapter{
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		Date currentTime = new Date();
		
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
		Main.nowtime = "``[" + timeformat.format(currentTime) + "]`` ";
		Main.lastmemberjointime = event.getMember().getTimeCreated().toString();
		Main.lastmemberjoindate = Main.lastmemberjointime.substring(0, 10);
		
		if(event.getReactionEmote().getName().equals("\u270B") && 
				!event.getMember().getUser().equals(event.getJDA().getSelfUser())) {
			String id = event.getMember().getId();
			
			String timejoined = event.getMember().getTimeJoined().toString();
			String timejoined1 = timejoined.substring(0, 10);
			String timecreated = event.getMember().getTimeCreated().toString();
			String timecreated1 = timecreated.substring(0, 10);
			PreparedStatement ps;
			try {
					String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
					String username = "ccbot";
					String password = "zs4GCaL";
					Connection conn = DriverManager.getConnection(url,username,password);
					java.sql.Statement stmt = conn.createStatement();
					ps = conn.prepareStatement("SELECT userids FROM userinformations WHERE userids= " + id);
					ResultSet rs = ps.executeQuery();
					if (!rs.next()) {
						int clan = 0;
						int begbonus = 0;
						int dailybonus = 0;
						int diamonds = 0;
						int coins = 1000;
						int usernum = 0;
						int tickets = 0;
						int messages = 0;
						int overalltickets = 0;
						String sql = "insert into userinformations"
								+" (userids, usernumber, tickets, overalltickets, messages, datejoin, accountcreated, coins, diamonds, dailybonus, begbonus, clan)"
								+" values ('" + id + "', '" + usernum + "', '"  + tickets + "', '" + overalltickets + "', '"+ messages + "', '"
								+ timejoined1 + "', '" + timecreated1 + "', '" + coins + "', '" + diamonds+ "', '" + dailybonus +"', '" + begbonus +"', '" + clan +"')";
						try {
							stmt.executeUpdate(sql);
							System.out.println("Wait why is user " + id + " not in the database? Well now he is lol");
							event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"User" + id+ "is not in the database but in the server!"
									+ "This should happen! Now he is...").queue();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}									
					}
					conn.close();
					System.out.println("user who plays the customs is already in the database thats good!");
				}
			catch (NullPointerException e) {} catch (Exception e) {event.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e.toString()).queue();}

		}
	}
}
