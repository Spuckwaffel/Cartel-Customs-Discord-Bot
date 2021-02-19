/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Clans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class ClanJoin extends ListenerAdapter {
	
	String clanstate1 = "open";
	int joindone = 0;
	int clan = 0;
	int member = 0;
	@SuppressWarnings("deprecation")
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
         if (event.getAuthor().isBot() && !event.getAuthor().equals(event.getJDA().getSelfUser())|| event.getAuthor().isFake()) {
			return;
		}
         
		String[] args = event.getMessage().getContentRaw().split(" ");
		if(args[0].equalsIgnoreCase(Main.prefix + "joinclan")) {
			if(args.length >= 2) {
			try {
				String id = event.getAuthor().getId();
				int currentclan = 0;
				String url = "jdbc:mysql://207.180.250.161/CCBot?serverTimezone=UTC";
				String username = "ccbot";
				String password = "zs4GCaL";
				Connection conn = DriverManager.getConnection(url,username,password);
				java.sql.Statement stmt = conn.createStatement();		
				ResultSet rsinfos = stmt.executeQuery("select * from userinformations where userids =" + id);
				while (rsinfos.next()) {
					currentclan = rsinfos.getInt("clan");
				}
				if(currentclan >0) {
					String clanname = "";
					ResultSet rsclans = stmt.executeQuery("select * from clans where clanid =" + currentclan);
					while (rsclans.next()) {
						clanname = rsclans.getString("clanname");
					}
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("You cant join a clan!");
					eb.setDescription("**You are already in a clan! Please leave your clan " + clanname+" to join a new clan!**");
					eb.setColor(0xff0000);
					event.getChannel().sendMessage(eb.build()).queue();
					
				}
				else {
					clanstate1 = "open";
					clan = 0;
					member = 0;
					int clanid = -1;
					String clanname = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Searching for Clan " + clanname);
					eb.setDescription("This may take a few seconds <a:loading:743801566451990570>");
					eb.setColor(0xffe600);
					Message message =event.getChannel().sendMessage(eb.build()).complete();
					ResultSet rsclanssearch = stmt.executeQuery("select * from clans where clanname ='" + clanname + "'");
					while (rsclanssearch.next()) {
						clanid = rsclanssearch.getInt("clanid");
					}
					if(clanid > 0) {
						String clanfoundname = "";
						String clandescription = "";
						int membercount = 0;
						String clanadmin1 = "";
						String clanadmin2 = "";
						String clanadmin3 = "";
						String clanowner = "";
						String member1 = "";
						String member2 = "";
						String member3 = "";
						String member4 = "";
						String member5 = "";
						String member6 = "";
						String member7 = "";
						String member8 = "";
						String member9 = "";
						String member10 = "";
						String member11 = "";
						String member12 = "";
						String member13 = "";
						String member14 = "";
						String member15 = "";
						String clanstate = "";
						clan = clanid;
						ResultSet rsclansfound = stmt.executeQuery("select * from clans where clanid ='" + clanid + "'");
						while (rsclansfound.next()) {
							clanfoundname = rsclansfound.getString("clanname");
							clandescription = rsclansfound.getString("clandescription");
							membercount = rsclansfound.getInt("membercount");
							member1 = rsclansfound.getString("member1");
							member2 = rsclansfound.getString("member2");
							member3 = rsclansfound.getString("member3");
							member4 = rsclansfound.getString("member4");
							member5 = rsclansfound.getString("member5");
							member6 = rsclansfound.getString("member6");
							member7 = rsclansfound.getString("member7");
							member8 = rsclansfound.getString("member8");
							member9 = rsclansfound.getString("member9");
							member10 = rsclansfound.getString("member10");
							member11 = rsclansfound.getString("member11");
							member12 = rsclansfound.getString("member12");
							member13 = rsclansfound.getString("member13");
							member14 = rsclansfound.getString("member14");
							member15 = rsclansfound.getString("member15");
							clanowner = rsclansfound.getString("clanowner");
							clanstate = rsclansfound.getString("clanstate");
							clanadmin1 = rsclansfound.getString("clanadmin1");
							clanadmin2 = rsclansfound.getString("clanadmin2");
							clanadmin3 = rsclansfound.getString("clanadmin3");
							
						}
						clanstate1 = clanstate;
						member = membercount;
						eb.setTitle("Clan found! Found clan " + clanfoundname + "!");
						eb.setColor(0x3dfc03);
						if(clandescription == null) {
							clandescription = "No description found!";
						
						}
						if(clanadmin1 != null) {
							clanadmin1 = "<@" + clanadmin1 + ">";
						}
						if(clanadmin2 != null) {
							clanadmin2 = ", <@" + clanadmin2 + ">";
						}
						if(clanadmin3 != null) {
							clanadmin3 = ", <@" + clanadmin3 + ">";
						}
						
						if(clanowner != null) {
							clanowner = "<@" + clanowner + ">";
						}
						if(member1 != null) {
							member1 = "<@" + member1 + ">";
						}
						if(member2 != null) {
							member2 = ", <@" + member2 + ">";
						}
						if(member3 != null) {
							member3 = ", <@" + member3 + ">";
						}
						if(member4 != null) {
							member4 = ", <@" + member4 + ">";
						}
						if(member5 != null) {
							member5 = ", <@" + member5 + ">";
						}
						if(member6 != null) {
							member6 = ", <@" + member6 + ">";
						}
						if(member7 != null) {
							member7 = ", <@" + member7 + ">";
						}
						if(member8 != null) {
							member8 = ", <@" + member8 + ">";
						}
						if(member9 != null) {
							member9 = ", <@" + member9 + ">";
						}
						if(member10 != null) {
							member10 = ", <@" + member10 + ">";
						}
						if(member11 != null) {
							member11 = ", <@" + member11 + ">";
						}
						if(member12 != null) {
							member12 = ", <@" + member12 + ">";
						}
						if(member13 != null) {
							member13 = ", <@" + member13 + ">";
						}
						if(member14 != null) {
							member14 = ", <@" + member14 + ">";
						}
						if(member15 != null) {
							member15 = ", <@" + member15 + ">";
						}
						
						//now exact the same but if it is
						
						if(clanowner == null) {
							clanowner = "No owner found!";
						}
						if(member1 == null) {
							member1 = "";
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
						if(member2 == null) {
							member2 = "";
						}
						if(member3 == null) {
							member3 = "";
						}
						if(member4 == null) {
							member4 = "";
						}
						if(member5 == null) {
							member5 = "";
						}
						if(member6 == null) {
							member6 = "";
						}
						if(member7 == null) {
							member7= "";
						}
						if(member8 == null) {
							member8= "";
						}
						if(member9 == null) {
							member9 = "";
						}
						if(member10 == null) {
							member10 = "";
						}
						if(member11 == null) {
							member11 = "";
						}
						if(member12 == null) {
							member12 = "";
						}
						if(member13 == null) {
							member13 = "";
						}
						if(member14 == null) {
							member14 = "";
						}
						if(member15 == null) {
							member15 = "";
						}
						String admincheck = "";
						admincheck = clanadmin1 + clanadmin2 + clanadmin3;
						if(admincheck =="") {
							admincheck = "**No admins found!**";
						}
						eb.setThumbnail("https://i.ibb.co/LpX0Q2Q/unnamed.png");
						eb.setAuthor("Clans ~ The war has started");
						eb.setDescription("**Information about this clan: \n"
								+ "Clan description:** _" + clandescription + "_ \n"
								+ "**Membercount:** " + membercount + "/15 \n"
								+ "**Owner:** :crown:" + clanowner + ":crown: \n"
								+ "**Admins: **:military_medal:" + admincheck + ":military_medal: \n"
								+ "**Members:** " + member1 + member2 + member3 + member4 + member5 + member6 + member7 
								+ member8 + member9 + member10 + member11 + member12 + member13 + member14 + member15 + "\n"
								+ "**Clan state:** " + clanstate + "\n \n"
								+ "**To join this clan, react with :white_check_mark:, if not, react with :no_entry:** \n"
								+ "After reacting, please wait a few seconds.");
						eb.setFooter(event.getMember().getIdLong() + "");
						message.editMessage(eb.build()).queue();
						message.addReaction("✅").queue();
						message.addReaction("⛔").queue();
						joindone = 0;
					}
					else {
						eb.setTitle("No result found for: " + clanname);
						eb.setDescription("Double check spelling and try again");
						eb.setColor(0xff0000);
						message.editMessage(eb.build()).queue();						
					}

				}
				
				
				
				
				conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}				
			}
			
			else {
				event.getChannel().sendMessage(":no_entry: Invalid syntax! Use !joinclan <clanname>").queue();
			}


		 }
	}
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://xx/CCBot?serverTimezone=UTC", "ccbot", "zs4GCaL")) {
			if (event.getChannel().retrieveMessageById(event.getMessageId()).complete().getEmbeds().get(0).getAuthor().getName().equals("Clans ~ The war has started")) {
				if (!event.getMember().equals(event.getGuild().getMemberById(event.getChannel().retrieveMessageById(event.getMessageId()).complete().getEmbeds().get(0).getFooter().getText()))
					&& !event.getMember().getUser().isBot()) {
					event.getTextChannel().removeReactionById(event.getMessageId(), event.getReaction().getReactionEmote().getName(), event.getMember().getUser()).queue();
					con.close();
					return;
					
				}
				if (event.getMember().getUser().isBot()) {
					con.close();
					return;
				}
				
				if (event.getReactionEmote().getName().equals("✅")) {
					java.sql.Statement stmt = con.createStatement();
					if(joindone == 0) {
						joindone = 1;
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**Joining the clan <a:loading:743801566451990570>**");
						eb.setColor(0xffe600);
						Message message =event.getChannel().sendMessage(eb.build()).complete();
						int updatedmembers = 0;
						updatedmembers = member + 1;
						if(clanstate1.contains("open")) {
						if (member == 1) {
							String update2 = "update clans set member2 =" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 2) {
							String update2 = "update clans set member3 =" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 3) {
							String update2 = "update clans set member4 =" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 4) {
							String update2 = "update clans set member5 =" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 5) {
							String update2 = "update clans set member6 =" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 6) {
							String update2 = "update clans set member7 =" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 7) {
							String update2 = "update clans set member8 =" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 8) {
							String update2 = "update clans set member9 =" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 9) {
							String update2 = "update clans set member10=" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");;
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 10) {
							String update2 = "update clans set member11=" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 11) {
							String update2 = "update clans set member12=" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 12) {
							String update2 = "update clans set member13=" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 13) {
							String update2 = "update clans set member14=" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							String update3 = "update clans set membercount =" + updatedmembers + " where clanid=" + clan;
							stmt.executeUpdate(update3);
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if (member == 14) {
							String update2 = "update clans set member15=" + event.getMember().getUser().getId() + " where clanid=" + clan;
							stmt.executeUpdate(update2);
							String update1 = "update userinformations set clan =" + clan + " where userids=" + event.getMember().getUser().getId();
							stmt.executeUpdate(update1);
							
							eb.setDescription("<@"+event.getMember().getUser().getId() +"> **Joined successfully the clan!**\n"
									+ "They now have " + updatedmembers + " members!");
							eb.setColor(0x15ff00);
							message.editMessage(eb.build()).queue();
						}
						if(member > 14) {
							eb.setDescription("**You cant join this clan: This clan is full!**");
							eb.setColor(0xff0000);
							message.editMessage(eb.build()).queue();
						}
						if(member < 0) {
							eb.setDescription("**A error occurred while trying to join the clan**");
							eb.setColor(0xff0000);
							message.editMessage(eb.build()).queue();
						}
						}
						if(clanstate1.contains("closed")) {
							eb.setDescription("**You cant join this clan! This clan is closed!**");
							eb.setColor(0xff0000);
							message.editMessage(eb.build()).queue();
						}
						if(clanstate1.contains("invite_only")) {
							eb.setDescription("**You cant join this clan! You can only join this clan if you have a invite!**\n"
									+ "You can use your invite with !joininvite <link>");
							eb.setColor(0xff0000);
							message.editMessage(eb.build()).queue();
						}
						
						
						
					}
				
					
				}
				
				if (event.getReactionEmote().getName().equals("⛔")) {
					if(joindone == 0) {
						joindone = 1;
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**You didnt join the clan**");
						eb.setColor(0xff0000);
						event.getChannel().sendMessage(eb.build()).queue();					
										
					}

				}


			}
			con.close();
		}
		catch (IndexOutOfBoundsException e1) {return;} catch (NullPointerException e2) {return;}  catch (SQLException e1) {
			e1.printStackTrace();
		}			
	}
}



