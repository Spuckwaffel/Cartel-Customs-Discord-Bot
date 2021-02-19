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

public class Admin extends ListenerAdapter {
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
				
				if(args.length==3) {
					String issuerid = event.getAuthor().getId();
					String clanowner = "";
					String clanadmin1 = "";
					String clanadmin2 = "";
					String clanadmin3 = "";
					String mentioneduser = event.getMessage().getMentionedMembers().get(0).getUser().getId();
					int currentclan = 0;
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
							clanadmin1 = claninfos.getString("clanadmin1");
							clanadmin2 = claninfos.getString("clanadmin2");
							clanadmin3 = claninfos.getString("clanadmin3");
							
						}
						if(!issuerid.contains(clanowner)) {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setTitle("Clan settings");
							eb.setDescription("<:watchout:743802105336430632> **You are not the clan Owner. You cant do this!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
						else {
							if(args[1].equals("promote")) {
								try {
									int completed2 = 0;
									int completed1 = 0;
									int mentionedusersclan = 0;
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
										if(clanadmin1 != null) {
											if(clanadmin1.equals(mentioneduser)) {
												EmbedBuilder eb = new EmbedBuilder();
												eb.setDescription("**<:watchout:743802105336430632> This member is already a admin! You cant promote him** ");
												eb.setColor(0xff0000);
												event.getChannel().sendMessage(eb.build()).queue();
												completed2 = 1;
											}
										}
										
										if(clanadmin2 != null) {
											if(clanadmin2.equals(mentioneduser)) {
												EmbedBuilder eb = new EmbedBuilder();
												eb.setDescription("**<:watchout:743802105336430632> This member is already a admin! You cant promote him** ");
												eb.setColor(0xff0000);
												event.getChannel().sendMessage(eb.build()).queue();
												completed2 = 1;
											}
										}
										
										if(clanadmin3 != null) {
											if(clanadmin3.equals(mentioneduser)) {
												EmbedBuilder eb = new EmbedBuilder();
												eb.setDescription("**<:watchout:743802105336430632> This member is already a admin! You cant promote him** ");
												eb.setColor(0xff0000);
												event.getChannel().sendMessage(eb.build()).queue();
												completed2 = 1;
											}
										}
										if (completed2 == 0) {
											if(completed1 == 0) {
												if(clanadmin1==null) {
													completed1 = 1;
													clanadmin1 = mentioneduser;
													String update1 = "UPDATE clans SET clanadmin1 ='"+mentioneduser +"' WHERE clanid=" +currentclan;
													stmt.executeUpdate(update1);
													EmbedBuilder eb = new EmbedBuilder();
													eb.setDescription("**Member <@" + mentioneduser + "> is now a admin!**");
													eb.setColor(0x3dfc03);
													event.getChannel().sendMessage(eb.build()).queue();
												}												
											}
											if(completed1 == 0) {
												if(clanadmin2==null) {
													completed1 = 1;
													clanadmin2 = mentioneduser;
													String update1 = "UPDATE clans SET clanadmin2 ='"+mentioneduser +"' WHERE clanid=" +currentclan;
													stmt.executeUpdate(update1);
													EmbedBuilder eb = new EmbedBuilder();
													eb.setDescription("**Member <@" + mentioneduser + "> is now a admin!**");
													eb.setColor(0x3dfc03);
													event.getChannel().sendMessage(eb.build()).queue();
												}												
											}
											if(completed1 == 0) {
												if(clanadmin3==null) {
													completed1 = 1;
													clanadmin3 = mentioneduser;
													String update1 = "UPDATE clans SET clanadmin3 ='"+mentioneduser +"' WHERE clanid=" +currentclan;
													stmt.executeUpdate(update1);
													EmbedBuilder eb = new EmbedBuilder();
													eb.setDescription("**Member <@" + mentioneduser + "> is now a admin!**");
													eb.setColor(0x3dfc03);
													event.getChannel().sendMessage(eb.build()).queue();
												}												
											}	
										}
									}
								}catch(IndexOutOfBoundsException e) {
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("**<:watchout:743802105336430632> Wrong arguments!** \n use ``!clansettings <promote/demote> @user`` (provide user as mention)");
									eb.setColor(0xff0000);
									event.getChannel().sendMessage(eb.build()).queue();
								}
							}
							if(args[1].equals("demote")) {
								try {
									int completed2 = 0;
									int completed1 = 0;
									int mentionedusersclan = 0;
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
										if(clanadmin1.contains(mentioneduser)) {
											completed1 = 1;
											clanadmin1 = clanadmin2;
											clanadmin2 = clanadmin3;
											clanadmin3 = null;
											String update1="update clans set clanadmin1="+clanadmin1 +" where clanid=" +currentclan;
											String update2="update clans set clanadmin2="+clanadmin2 +" where clanid=" +currentclan;
											String update3="update clans set clanadmin3="+clanadmin3 +" where clanid=" +currentclan;
											stmt.executeUpdate(update1);
											stmt.executeUpdate(update2);
											stmt.executeUpdate(update3);
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**Member <@" + mentioneduser + "> got demoted**");
											eb.setColor(0xffc800);
											event.getChannel().sendMessage(eb.build()).queue();
										}
										if(clanadmin2.contains(mentioneduser)) {
											completed1 = 1;
											clanadmin2 = clanadmin3;
											clanadmin3 = null;
											String update2="update clans set clanadmin2="+clanadmin2 +" where clanid=" +currentclan;
											String update3="update clans set clanadmin3="+clanadmin3 +" where clanid=" +currentclan;
											stmt.executeUpdate(update2);
											stmt.executeUpdate(update3);
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**Member <@" + mentioneduser + "> got demoted**");
											eb.setColor(0xffc800);
											event.getChannel().sendMessage(eb.build()).queue();
											
										}
										if(clanadmin3.contains(mentioneduser)) {
											completed1 = 1;
											clanadmin3 = null;
											String update3="update clans set clanadmin3="+clanadmin3 +" where clanid=" +currentclan;
											stmt.executeUpdate(update3);
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**Member <@" + mentioneduser + "> got demoted**");
											eb.setColor(0xffc800);
											event.getChannel().sendMessage(eb.build()).queue();
											
										}
										if(completed1 != 1) {
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**<:watchout:743802105336430632> You cant demote the user <@" + mentioneduser + ">, he's not a admin!**");
											eb.setColor(0xff0000);
											event.getChannel().sendMessage(eb.build()).queue();
										}
									}
								}catch(IndexOutOfBoundsException e) {
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("**<:watchout:743802105336430632> Wrong arguments!** \n use ``!clansettings <promote/demote> @user`` (provide user as mention)");
									eb.setColor(0xff0000);
									event.getChannel().sendMessage(eb.build()).queue();
								} catch (NullPointerException e) {}
							}
						}
					}
				}
			 }
			
			
			conn.close();
		}
		catch (Exception e){
			System.out.println(e);
		}
	}

}
