/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Events;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;


public class GuildMemberJoin extends ListenerAdapter{
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event){
		
		Date currentDate = new Date();
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("current date:" +dateformat.format(currentDate));
		Main.todaysdate = dateformat.format(currentDate);
		
		Date currentTime = new Date();
		
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
		Main.nowtime = "``[" + timeformat.format(currentTime) + "]`` ";
		Main.lastmemberjointime = event.getMember().getTimeCreated().toString();
		Main.lastmemberjoindate = Main.lastmemberjointime.substring(0, 10);
		
		
		try{
			
			String url = "jdbc:mysql://sazuo.de/CCBot?serverTimezone=UTC";
			String username = "ccbot";
			String password = "zs4GCaL";
			Connection conn = DriverManager.getConnection(url,username,password);
			java.sql.Statement stmt = conn.createStatement();
			
			
			
			@SuppressWarnings("unused")
			String effectivename = event.getMember().getEffectiveName();
			String userid = event.getMember().getId();
			String useridCheck = "select * from userinformations WHERE userids ='" + userid + "'";
			ResultSet rs = stmt.executeQuery(useridCheck);
			
			if(rs.next()) {
				int completed = 0;
				System.out.println(Main.nowtime +"User is in the database -> figuring out usernumber");				
				int usernum = 0;
				ResultSet rss = conn.createStatement().executeQuery("SELECT usernumber FROM userinformations WHERE userids =" + userid);
				while (rss.next()) {
					usernum = rs.getInt("usernumber");
					System.out.println("usernumber = " + usernum);
					
				}
				if(usernum == 0) {
					completed = 1;
					int usernumnew = usernum + 1;
					event.getGuild().addRoleToMember(userid, event.getGuild().getRoleById("706161082871578644")).complete();
					event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"The user gets registered again: <@" + userid + "> (-0)"
							+ " -> he gets warning role").queue();
					System.out.println(Main.nowtime +"The user gets registered again with the id " + userid + " (-0)");
					String update1 = "update userinformations set usernumber='"+ usernumnew + "' where userids=" + userid;
					try {
						stmt.executeUpdate(update1);
						System.out.println("updated sucessfully");
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				
				if(usernum == 1) {
					completed = 1;
					int usernumnew = usernum + 1;
					event.getUser().openPrivateChannel().queue((channel) -> {channel.sendMessage("Uh oh! Seems like you were banned twice and got unbanned twice."
							+ " This account will stay banned. If you feel this was done in a error, please contact the administration team"
							+ " or send <@358900543365709824> a DM. Have a nice day.").queue((message) -> {
									event.getGuild().ban(userid, 0, "Banned by the automatic system to prevent double unbans").queue();
								});
							});
					event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"user <@" + userid + "> gets banned (-1)").queue();
					EmbedBuilder info = new EmbedBuilder();
					info.setColor(0xff3923);
					info.setTitle(":information_source: Info");
					info.setDescription(Main.nowtime +"The user <@" + userid + "> (id: ``" + userid
							+ "``) got unbanned twice. Please dont unban people 2 times!");
					event.getGuild().getTextChannelById("704064412499050548").sendMessage(info.build()).queue();
					info.clear();
					String update1 = "update userinformations set usernumber='"+ usernumnew + "' where userids=" + userid;
					try {
						stmt.executeUpdate(update1);
						System.out.println("updated sucessfully");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(usernum == 2) {
					completed = 1;
					event.getUser().openPrivateChannel().queue((channel) -> {channel.sendMessage("Uh oh! Seems like you were banned twice and got unbanned twice."
							+ " This account will stay banned. If you feel this was done in a error, please contact the administration team"
							+ " or send <@358900543365709824> a DM. Have a nice day.").queue((message) -> { 
												event.getGuild().ban(userid, 0, "Banned by the automatic system to prevent double unbans. WARN: this user (id: " + userid
							+ ") was alredy unbanned 3 times! This is the 3rd time! Is someone trying to unban this person multiple times? Please check!").queue();
							});
						});
					event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"user with id " + userid + " gets banned (-2)").queue();
					System.out.println("user with id " + userid + "gets banned (-2)");
					EmbedBuilder eb = new EmbedBuilder();
					eb.setDescription(Main.nowtime +"<:watchout:743802105336430632> User <@" +userid + "> ( id: ``" + userid
							+ "``) was unbanned 3 times! This is the 3rd time he gets banned! Is someone trying to unban this person multiple times? Please check!");
					event.getGuild().getTextChannelById("704064412499050548").sendMessage(eb.build()).queue();
					
				}
				if(usernum == 3) {
					completed = 1;
					event.getUser().openPrivateChannel().queue((channel) -> {channel.sendMessage("Uh oh! Seems like you got auto banned."
							+ " This account will stay banned. If you feel this was done in a error, please contact the administration team"
							+ " or send <@358900543365709824> a DM. Have a nice day.").queue((message) -> { 
												event.getGuild().ban(userid, 0, "Banned for: autoban when he joins again (usernum 3)").queue();
							});
						});
					event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"user with id " + userid + " gets banned (-2)").queue();
					System.out.println("user with id " + userid + "gets banned (-3)");
					EmbedBuilder eb = new EmbedBuilder();
					eb.setDescription(Main.nowtime +" User with id " + userid + " got auto banned (a helper/admin set that the user gets auto banned on join");
					event.getGuild().getTextChannelById("704064412499050548").sendMessage(eb.build()).queue();
					String update1 = "update userinformations set usernumber='2' where userids=" + userid;
					try {
						stmt.executeUpdate(update1);
						System.out.println("updated sucessfully");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(usernum == 5) {
					completed = 1;
					event.getUser().openPrivateChannel().queue((channel) -> {channel.sendMessage("Lucky you are a VIP member and wont get banned after"
							+ " unbans! Enjoy your rest! <a:herzneucat:729416851443941478>").queue();});	
					System.out.println(Main.nowtime +"user with id " + userid + " is a vip and nothing will happen (-5)");
				}
				if(completed == 0){
					EmbedBuilder eb = new EmbedBuilder();
					eb.setDescription(":information_source: There's a member with a wrong usernumber! His usernumber is " + usernum+ "Please change to 1,2,3 or 5!");
					event.getGuild().getTextChannelById("704064412499050548").sendMessage(eb.build()).queue();
				}
				completed = 0;

			}

			else {
				System.out.println(Main.nowtime +"User is not in the database -> adding him");
				int clan = 0;
				int begbonus = 100;
				int diamonds = 0;
				int dailybonus = 0;
				int coins = 1000;
				int usernum = 0;
				int tickets = 0;
				int messages = 0;
				int overalltickets = 0;
				String sql = "insert into userinformations"
							+" (userids, usernumber,  tickets, overalltickets, messages, datejoin, accountcreated, coins, diamonds, dailybonus, begbonus, clan)"
							+" values ('" + userid + "', '" + usernum + "', '" + tickets + "', '" + overalltickets + "', '"+ messages + "', '"
							+ Main.todaysdate + "', '" + Main.lastmemberjoindate + "', '" + coins + "', '" + diamonds +"', '" + dailybonus +"', '" + begbonus +"', '" + clan + "')";
				stmt.executeUpdate(sql);
				event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"The user gets registered for the first time: <@" + userid + ">").queue();
				
				System.out.println(Main.nowtime +"Insert complete");
			}
			
			Date currentDate1 = new Date();
			SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
			String time1 = dateformat1.format(currentDate1);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = null;
			try {
				date1 = format.parse(time1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			Date date2 = null;
			try {
				date2 = format.parse(Main.lastmemberjoindate);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			long mindtime = 432000000;
			long difference = date1.getTime() - date2.getTime(); 
			System.out.println(difference / 86400000 + " Days old account");
			if (difference < mindtime) {
				event.getUser().openPrivateChannel().queue((channel) -> {channel.sendMessage("Uh oh! Seems like your account is under 5 days old! "
						+ " This account will stay banned. If you feel this was done in a error, please contact the administration team"
						+ " or send <@358900543365709824> a DM. Have a nice day.").queue((message) -> {
								event.getGuild().ban(userid, 0, "Banned with the reason: Account under 5 days old").queue();
							});
						});
				event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"The user with the id <@" + userid + ">"
						+ " just got banned. His account was under 5 days old").queue();
				String update1 = "update userinformations set usernumber='2' where userids=" + userid;
				try {
					stmt.executeUpdate(update1);
					System.out.println("updated sucessfully");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			
			
			conn.close();
			} catch(Exception e){
				event.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e.toString()).queue();
			}
		}
}
