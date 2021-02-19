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

public class Kick extends ListenerAdapter {
	
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
					if(args[1].equalsIgnoreCase("kick")) { 
						String issuerid = event.getAuthor().getId();
						String clanowner = "";
						String clanadmin1 = "";
						String clanadmin2 = "";
						String clanadmin3 = "";
						int personsclan = 0;
						int finished = 0;
						int membercount = 0;
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
								membercount = claninfos.getInt("membercount");
								member1 = claninfos.getString("member1");
								member2 = claninfos.getString("member2");
								member3 = claninfos.getString("member3");
								member4 = claninfos.getString("member4");
								member5 = claninfos.getString("member5");
								member6 = claninfos.getString("member6");
								member7 = claninfos.getString("member7");
								member8 = claninfos.getString("member8");
								member9 = claninfos.getString("member9");
								member10 = claninfos.getString("member10");
								member11 = claninfos.getString("member11");
								member12 = claninfos.getString("member12");
								member13 = claninfos.getString("member13");
								member14 = claninfos.getString("member14");
								member15 = claninfos.getString("member15");
								
							}
							if(!issuerid.contains(clanowner)) {
								EmbedBuilder eb = new EmbedBuilder();
								eb.setTitle("Clan settings");
								eb.setDescription("<:watchout:743802105336430632> **You are not the clan Owner. You cant do this!**");
								eb.setColor(0xff0000);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							else {
								if(mentioneduser == clanowner) {
									EmbedBuilder eb = new EmbedBuilder();
									eb.setTitle("Clan settings");
									eb.setDescription("**<:watchout:743802105336430632> You cant kick yourself!** \n You cant kick yourself/the owner!");
									eb.setColor(0xff0000);
									event.getChannel().sendMessage(eb.build()).queue();
								}
								else {
									if(clanadmin1 != null) {
										if(clanadmin1.contains(mentioneduser)) {
											clanadmin1 = clanadmin2;
											clanadmin2 = clanadmin3;
											clanadmin3 = null;
											
											String update1="update clans set clanadmin1="+clanadmin1 +" where clanid=" +currentclan;
											String update2="update clans set clanadmin2="+clanadmin2 +" where clanid=" +currentclan;
											String update3="update clans set clanadmin3="+clanadmin3 +" where clanid=" +currentclan;
											stmt.executeUpdate(update1);
											stmt.executeUpdate(update2);
											stmt.executeUpdate(update3);
										}							
									}
									
									if(clanadmin2 != null) {
										if(clanadmin2.contains(mentioneduser)) {
											clanadmin2 = clanadmin3;
											clanadmin3 = null;
											
											String update2="update clans set clanadmin2="+clanadmin2 +" where clanid=" +currentclan;
											String update3="update clans set clanadmin3="+clanadmin3 +" where clanid=" +currentclan;
											stmt.executeUpdate(update2);
											stmt.executeUpdate(update3);
										}							
									}
									
									if(clanadmin3 != null) {
										if(clanadmin3.contains(mentioneduser)) {
											clanadmin3 = null;
											
											String update3="update clans set clanadmin3="+clanadmin3 +" where clanid=" +currentclan;
											stmt.executeUpdate(update3);
										}							
									}
									

									if(member1 != null) {
									if(member1.contains(mentioneduser)) {
										member1 = member2;
										member2 = member3;
										member3 = member4;
										member4 = member5;
										member5 = member6;
										member6 = member7;
										member7 = member8;
										member8 = member9;
										member9 = member10;
										member10 = member11;
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update1="update clans set member1="+member1 +" where clanid=" +currentclan;
										String update2="update clans set member2="+member2 +" where clanid=" +currentclan;
										String update3="update clans set member3="+member3 +" where clanid=" +currentclan;
										String update4="update clans set member4="+member4 +" where clanid=" +currentclan;
										String update5="update clans set member5="+member5 +" where clanid=" +currentclan;
										String update6="update clans set member6="+member6 +" where clanid=" +currentclan;
										String update7="update clans set member7="+member7 +" where clanid=" +currentclan;
										String update8="update clans set member8="+member8 +" where clanid=" +currentclan;
										String update9="update clans set member9="+member9 +" where clanid=" +currentclan;
										String update10="update clans set member10="+member10 +" where clanid=" +currentclan;
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update1);
										stmt.executeUpdate(update2);
										stmt.executeUpdate(update3);
										stmt.executeUpdate(update4);
										stmt.executeUpdate(update5);
										stmt.executeUpdate(update6);
										stmt.executeUpdate(update7);
										stmt.executeUpdate(update8);
										stmt.executeUpdate(update9);
										stmt.executeUpdate(update10);
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
										
									}	
									}
									if(member2 != null) {
									if(member2.contains(mentioneduser)) {
										member2 = member3;
										member3 = member4;
										member4 = member5;
										member5 = member6;
										member6 = member7;
										member7 = member8;
										member8 = member9;
										member9 = member10;
										member10 = member11;
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update2="update clans set member2="+member2 +" where clanid=" +currentclan;
										String update3="update clans set member3="+member3 +" where clanid=" +currentclan;
										String update4="update clans set member4="+member4 +" where clanid=" +currentclan;
										String update5="update clans set member5="+member5 +" where clanid=" +currentclan;
										String update6="update clans set member6="+member6 +" where clanid=" +currentclan;
										String update7="update clans set member7="+member7 +" where clanid=" +currentclan;
										String update8="update clans set member8="+member8 +" where clanid=" +currentclan;
										String update9="update clans set member9="+member9 +" where clanid=" +currentclan;
										String update10="update clans set member10="+member10 +" where clanid=" +currentclan;
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update2);
										stmt.executeUpdate(update3);
										stmt.executeUpdate(update4);
										stmt.executeUpdate(update5);
										stmt.executeUpdate(update6);
										stmt.executeUpdate(update7);
										stmt.executeUpdate(update8);
										stmt.executeUpdate(update9);
										stmt.executeUpdate(update10);
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member3 != null) {
									if(member3.contains(mentioneduser)) {
										member3 = member4;
										member4 = member5;
										member5 = member6;
										member6 = member7;
										member7 = member8;
										member8 = member9;
										member9 = member10;
										member10 = member11;
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update3="update clans set member3="+member3 +" where clanid=" +currentclan;
										String update4="update clans set member4="+member4 +" where clanid=" +currentclan;
										String update5="update clans set member5="+member5 +" where clanid=" +currentclan;
										String update6="update clans set member6="+member6 +" where clanid=" +currentclan;
										String update7="update clans set member7="+member7 +" where clanid=" +currentclan;
										String update8="update clans set member8="+member8 +" where clanid=" +currentclan;
										String update9="update clans set member9="+member9 +" where clanid=" +currentclan;
										String update10="update clans set member10="+member10 +" where clanid=" +currentclan;
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update3);
										stmt.executeUpdate(update4);
										stmt.executeUpdate(update5);
										stmt.executeUpdate(update6);
										stmt.executeUpdate(update7);
										stmt.executeUpdate(update8);
										stmt.executeUpdate(update9);
										stmt.executeUpdate(update10);
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member4 != null) {
									if(member4.contains(mentioneduser)) {
										member4 = member5;
										member5 = member6;
										member6 = member7;
										member7 = member8;
										member8 = member9;
										member9 = member10;
										member10 = member11;
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update4="update clans set member4="+member4 +" where clanid=" +currentclan;
										String update5="update clans set member5="+member5 +" where clanid=" +currentclan;
										String update6="update clans set member6="+member6 +" where clanid=" +currentclan;
										String update7="update clans set member7="+member7 +" where clanid=" +currentclan;
										String update8="update clans set member8="+member8 +" where clanid=" +currentclan;
										String update9="update clans set member9="+member9 +" where clanid=" +currentclan;
										String update10="update clans set member10="+member10 +" where clanid=" +currentclan;
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update4);
										stmt.executeUpdate(update5);
										stmt.executeUpdate(update6);
										stmt.executeUpdate(update7);
										stmt.executeUpdate(update8);
										stmt.executeUpdate(update9);
										stmt.executeUpdate(update10);
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member5 != null) {
									if(member5.contains(mentioneduser)) {
										member5 = member6;
										member6 = member7;
										member7 = member8;
										member8 = member9;
										member9 = member10;
										member10 = member11;
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update5="update clans set member5="+member5 +" where clanid=" +currentclan;
										String update6="update clans set member6="+member6 +" where clanid=" +currentclan;
										String update7="update clans set member7="+member7 +" where clanid=" +currentclan;
										String update8="update clans set member8="+member8 +" where clanid=" +currentclan;
										String update9="update clans set member9="+member9 +" where clanid=" +currentclan;
										String update10="update clans set member10="+member10 +" where clanid=" +currentclan;
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update5);
										stmt.executeUpdate(update6);
										stmt.executeUpdate(update7);
										stmt.executeUpdate(update8);
										stmt.executeUpdate(update9);
										stmt.executeUpdate(update10);
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member6 != null) {
									if(member6.contains(mentioneduser)) {
										member6 = member7;
										member7 = member8;
										member8 = member9;
										member9 = member10;
										member10 = member11;
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update6="update clans set member6="+member6 +" where clanid=" +currentclan;
										String update7="update clans set member7="+member7 +" where clanid=" +currentclan;
										String update8="update clans set member8="+member8 +" where clanid=" +currentclan;
										String update9="update clans set member9="+member9 +" where clanid=" +currentclan;
										String update10="update clans set member10="+member10 +" where clanid=" +currentclan;
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update6);
										stmt.executeUpdate(update7);
										stmt.executeUpdate(update8);
										stmt.executeUpdate(update9);
										stmt.executeUpdate(update10);
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member7 != null) {
									if(member7.contains(mentioneduser)) {;
										member7 = member8;
										member8 = member9;
										member9 = member10;
										member10 = member11;
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update7="update clans set member7="+member7 +" where clanid=" +currentclan;
										String update8="update clans set member8="+member8 +" where clanid=" +currentclan;
										String update9="update clans set member9="+member9 +" where clanid=" +currentclan;
										String update10="update clans set member10="+member10 +" where clanid=" +currentclan;
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update7);
										stmt.executeUpdate(update8);
										stmt.executeUpdate(update9);
										stmt.executeUpdate(update10);
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									
									if(member8 != null) {
									if(member8.contains(mentioneduser)) {
										member8 = member9;
										member9 = member10;
										member10 = member11;
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update8="update clans set member8="+member8 +" where clanid=" +currentclan;
										String update9="update clans set member9="+member9 +" where clanid=" +currentclan;
										String update10="update clans set member10="+member10 +" where clanid=" +currentclan;
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update8);
										stmt.executeUpdate(update9);
										stmt.executeUpdate(update10);
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member9 != null) {
									if(member9.contains(mentioneduser)) {
										member9 = member10;
										member10 = member11;
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update9="update clans set member9="+member9 +" where clanid=" +currentclan;
										String update10="update clans set member10="+member10 +" where clanid=" +currentclan;
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update9);
										stmt.executeUpdate(update10);
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member10 != null) {
									if(member10.contains(mentioneduser)) {
										member10 = member11;
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update10="update clans set member10="+member10 +" where clanid=" +currentclan;
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update10);
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member11 != null) {
									if(member11.contains(mentioneduser)) {
										member11 = member12;
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update11="update clans set member11="+member11 +" where clanid=" +currentclan;
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update11);
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member12 != null) {
									if(member12.contains(mentioneduser)) {
										member12 = member13;
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update12="update clans set member12="+member12 +" where clanid=" +currentclan;
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update12);
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member13 != null) {
									if(member13.contains(mentioneduser)) {
										member13 = member14;
										member14 = member15;
										member15 = null;
										
										String update13="update clans set member13="+member13 +" where clanid=" +currentclan;
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update13);
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member14 != null) {
									if(member14.contains(mentioneduser)) {
										member14 = member15;
										member15 = null;
										
										String update14="update clans set member14="+member14 +" where clanid=" +currentclan;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update14);
										stmt.executeUpdate(update15);
									}	
									}
									if(member15 != null) {
									if(member15.contains(mentioneduser)) {
										member15 = null;
										String update15="update clans set member15="+member15 +" where clanid=" +currentclan;
										stmt.executeUpdate(update15);
									}	
									}
									membercount = membercount - 1;
									String update1="update clans set membercount="+membercount +" where clanid=" +currentclan;
									stmt.executeUpdate(update1);
									String update = "update userinformations set clan =0 where userids="+mentioneduser;
									stmt.executeUpdate(update);
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("<@" + mentioneduser + "> got kicked from the clan");
									eb.setColor(0xfcba03);
									event.getChannel().sendMessage(eb.build()).queue();
									
								}
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
