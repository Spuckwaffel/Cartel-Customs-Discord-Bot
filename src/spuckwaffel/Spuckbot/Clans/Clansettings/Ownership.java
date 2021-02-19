/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Clans.Clansettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class Ownership extends ListenerAdapter {
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		try {
			String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
			String username = "ccbot";
			String password = "zs4GCaL";
			Connection conn = DriverManager.getConnection(url,username,password);
			java.sql.Statement stmt = conn.createStatement();
			
			
			if(args[0].equalsIgnoreCase(Main.prefix + "clansettings")) {
				
				
				
				if(args[1].equalsIgnoreCase("ownership")) {
					String issuerid = event.getAuthor().getId();
					int currentclan = 0;
					String clanowner = "";
					String mentioneduser = event.getMessage().getMentionedMembers().get(0).getUser().getId();
					ResultSet issuerinfos = stmt.executeQuery("select * from userinformations where userids =" + issuerid);
					while (issuerinfos.next()) {
						currentclan = issuerinfos.getInt("clan");
					}
					if(currentclan == 0) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**You cant change the settings of a clan! You are not in a clan!**");
						eb.setColor(0xff0000);
						event.getChannel().sendMessage(eb.build()).queue();
					}
					else {
						
						ResultSet claninfos = stmt.executeQuery("select * from clans where clanid ='" + currentclan + "'");
						while (claninfos.next()) {
							clanowner = claninfos.getString("clanowner");
							
						}
						if(!issuerid.contains(clanowner)) {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setTitle("Clan settings");
							eb.setDescription("<:watchout:743802105336430632> **You are not the clan Owner. You cant do this!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
						else {
							
							int completed1 = 0;
							int mentionedusersclan = 0;

							try {						
								ResultSet mentioneduserinfos = stmt.executeQuery("select * from userinformations where userids =" + mentioneduser);
								while (mentioneduserinfos.next()) {
									mentionedusersclan = mentioneduserinfos.getInt("clan");
								}
								if(mentionedusersclan != currentclan) {
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("**<:watchout:743802105336430632> This member is not in your clan!** \n You can only promote people who are in your clan!");
									eb.setColor(0xff0000);
									event.getChannel().sendMessage(eb.build()).queue();
								}
								else {
									String update1 = "UPDATE clans SET clanowner ='"+mentioneduser +"' WHERE clanid=" +currentclan;
									stmt.executeUpdate(update1);
									completed1 = 1;
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("Member <@" + mentioneduser + "> is now the clan owner!");
									eb.setColor(0x3dfc03);
									event.getChannel().sendMessage(eb.build()).queue();
								}								
							} catch(IndexOutOfBoundsException e) {
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**<:watchout:743802105336430632> Wrong arguments!** \n use ``!clansettings ownership @user`` (provide user as mention)");
								eb.setColor(0xff0000);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							
						}						
					}

				}
				
			}
			
			
			conn.close();
		}
		catch (Exception e){
			event.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e.toString()).queue();
		}
	}
}
