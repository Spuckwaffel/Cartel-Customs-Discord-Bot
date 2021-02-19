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

public class ClanCreate extends ListenerAdapter {

	
	int diamonds = 0;
	String clanname = "";
	int creationdone = 0;
	int clannum = 0;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		try {
			String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
			String username = "ccbot";
			String password = "zs4GCaL";
			Connection conn = DriverManager.getConnection(url,username,password);
			java.sql.Statement stmt = conn.createStatement();
			
			if(args[0].equalsIgnoreCase(Main.prefix + "createclan")) {
				if(args.length >= 2) {
					diamonds = 0;
					clanname = "";
					clannum = 0;
					String id = event.getAuthor().getId();
					int currentclan = 0;
					clanname = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
					ResultSet rsinfos = stmt.executeQuery("select * from userinformations where userids =" + id);
					while (rsinfos.next()) {
						currentclan = rsinfos.getInt("clan");
						diamonds = rsinfos.getInt("diamonds");
						
					}
					if(currentclan != 0) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**You cant create a clan a clan! You are already in a clan!**");
						eb.setColor(0xff0000);
						event.getChannel().sendMessage(eb.build()).queue();
					}
					else {
						int entrys = 0;
						ResultSet getclans = stmt.executeQuery("select count(*) from clans");
						 while (getclans.next()) {
						 entrys = getclans.getInt(1);
						 }
						 clannum = entrys + 1;
						 if(diamonds >= 10) {
							 	int doublename = 0;
								ResultSet getclans1 = stmt.executeQuery("select * from clans where clanname= '" + clanname + "'");
								 while (getclans1.next()) {
								 doublename = getclans1.getInt("clanid");
								 }
								 if(doublename ==0) {
									 EmbedBuilder eb = new EmbedBuilder();
									 eb.setFooter(event.getMember().getIdLong() + "");
									 eb.setAuthor("Clans ~ The war has begun");
									 eb.setDescription("**Are you sure you want to create the Clan " + clanname + "?** \n This cannot be undone \n "
									 		+ "After reacting, please wait a few seconds.");
									 eb.setColor(0xfcba03);
									 Message message =event.getChannel().sendMessage(eb.build()).complete();
									 message.addReaction("✅").queue();
									 message.addReaction("⛔").queue();
									 creationdone = 0;
								 }
								 else {
										EmbedBuilder eb = new EmbedBuilder();
										eb.setDescription("<:watchout:743802105336430632> **A clan with this name already exists!**");
										eb.setColor(0xff0000);
										event.getChannel().sendMessage(eb.build()).queue();
								 }
							 
							 
							 
						 }
						 else {
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("<:watchout:743802105336430632> **You need at least 10<:diamond:753994248583512095> to create a clan!**");
								eb.setColor(0xff0000);
								event.getChannel().sendMessage(eb.build()).queue();
						 }
					}
				}
				else {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setDescription("<:watchout:743802105336430632> **Please provide a name!** \n use ``!createclan <name>``");
					eb.setColor(0xff0000);
					event.getChannel().sendMessage(eb.build()).queue();
				}
			 }
			
			conn.close();
			} catch(Exception e){
				System.out.println(e);
		}
	}
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://xx/CCBot?serverTimezone=UTC", "ccbot", "zs4GCaL")) {
			if (event.getChannel().retrieveMessageById(event.getMessageId()).complete().getEmbeds().get(0).getAuthor().getName().equals("Clans ~ The war has begun")) {
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
					if(creationdone == 0) {
						creationdone = 1;
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**Creating clan, please wait <a:loading:743801566451990570>**");
						eb.setColor(0xfcba03);
						Message message = event.getChannel().sendMessage(eb.build()).complete();	
						
						String update1 = "UPDATE userinformations set clan='" + clannum +"' where userids=" + event.getMember().getUser().getId();
						stmt.executeUpdate(update1);
						diamonds = diamonds - 10;
						String update2 = "UPDATE userinformations set diamonds='" + diamonds + "' where userids=" + event.getMember().getUser().getId();
						stmt.executeUpdate(update2);

						int clanid = clannum;
						String clanname1 = clanname;
						int membercount = 1;
						String member1 = event.getMember().getUser().getId();
						String clanstate = "closed";
						String clandescription = "This is a new clan!";
						String clanowner = event.getMember().getUser().getId();
						String claninvite = null;
						long clanmoney = 0;
						long miningtime = 1000;
						
						
						String sql = "insert into clans"
								+ " (clanid, clanname, membercount, member1, clanstate, clandescription, clanowner, claninvite, clanmoney, miningtime)"
								+ " values ('" + clanid + "', '" + clanname1 + "', '" + membercount + "', '" + member1 + "', '" + clanstate  + "', '" + clandescription  + "', '" +  clanowner  + "', '" + claninvite + "', '" + clanmoney + "', '" + miningtime + "')";
						stmt.executeUpdate(sql);
						
						eb.setTitle("Successfully created your Clan " + clanname + "!");
						eb.setDescription("**You can view your clan info via** ``!claninfo " + clanname + "``\n"
								+ "Your clan is closed by default, so nobody can join. To change this setting and other settings, use ``!clansettings``");
						eb.setColor(0x3dfc03);
						message.editMessage(eb.build()).queue();
					}
				}
				
				if (event.getReactionEmote().getName().equals("⛔")) {
					if(creationdone == 0) {
						creationdone = 1;
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**You didnt create a clan**");
						eb.setColor(0xff0000);
						event.getChannel().sendMessage(eb.build()).queue();					
										
					}

				}
				
			}
			con.close();
		}		catch (IndexOutOfBoundsException e1) {return;} catch (NullPointerException e2) {return;}  catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
		
}

