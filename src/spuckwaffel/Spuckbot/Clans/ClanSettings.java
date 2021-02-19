/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Clans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class ClanSettings  extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		try {
			String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
			String username = "ccbot";
			String password = "zs4GCaL";
			Connection conn = DriverManager.getConnection(url,username,password);
			java.sql.Statement stmt = conn.createStatement();
			
			if(args[0].equalsIgnoreCase(Main.prefix + "clansettings")) {
				if(args.length == 1) {
					String id = event.getAuthor().getId();
					int currentclan = 0;
					ResultSet rsinfos = stmt.executeQuery("select * from userinformations where userids =" + id);
					while (rsinfos.next()) {
						currentclan = rsinfos.getInt("clan");
					}
					if(currentclan == 0) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**You cant change the settings of a clan! You are not in a clan!**");
						eb.setColor(0xff0000);
						event.getChannel().sendMessage(eb.build()).queue();
					}
					else {
						String clanstate = "";
						String clanowner = "";
							String clanadmin1 = "";
							String clanadmin2 = "";
							String clanadmin3 = "";		
							int membercount = 0;
						ResultSet rsclanssearch = stmt.executeQuery("select * from clans where clanid ='" + currentclan + "'");
						while (rsclanssearch.next()) {
							membercount = rsclanssearch.getInt("membercount");
							clanowner = rsclanssearch.getString("clanowner");
							clanadmin1 = rsclanssearch.getString("clanadmin1");
							clanadmin2 = rsclanssearch.getString("clanadmin2");
							clanadmin3 = rsclanssearch.getString("clanadmin3");
							clanstate = rsclanssearch.getString("clanstate");
							
						}
						if(!id.contains(clanowner)) {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setTitle("Clan settings");
							eb.setDescription("<:watchout:743802105336430632> **You are not the clan Owner. You cant do this!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
						else {
							if(clanadmin1 != null) {
								clanadmin1 = "<@" + clanadmin1 + ">";
							}
							if(clanadmin2 != null) {
								clanadmin2 = ", <@" + clanadmin2 + ">";
							}
							if(clanadmin3 != null) {
								clanadmin3 = ", <@" + clanadmin3 + ">";
							}
							if(clanadmin1 == null) {
								clanadmin1 = "";
							}
							if(clanadmin2 == null) {
								clanadmin2 = "";
							}
							if(clanadmin3 == null) {
								clanadmin3 = "";
							}
							String admincheck = "";
							admincheck = clanadmin1 + clanadmin2 + clanadmin3;
							if(admincheck =="") {
								admincheck = "**No admins found!**";
							}
							EmbedBuilder eb = new EmbedBuilder();
							eb.setTitle("Clan settings");
							eb.setThumbnail("https://i.ibb.co/LpX0Q2Q/unnamed.png");
							eb.setDescription("**Please select one Setting below to proceed:**");
							eb.addField("Join Status:", "**" + clanstate + "**\n``!clansettings joinstatus <open/closed/invite_only>``", false);
							eb.addField("Owner:", "<@" + clanowner + ">\n``!clansettings ownership @user``", false);
							eb.addField("Admins:", admincheck  + "\n ``!clansettings admin <promote/demote> @user``", false);
							eb.addField("Members:","**" + membercount + "/15** \n ``!clansettings kick @user``", false);
							eb.setColor(0x3dfc03);
							event.getChannel().sendMessage(eb.build()).queue();
							}
						}
					}
			}
			conn.close();
		} catch(ArrayIndexOutOfBoundsException e1) {} catch(Exception e){System.out.println(e);} 
	}
}
