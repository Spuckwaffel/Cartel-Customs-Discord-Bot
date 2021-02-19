/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Clans.ClanMine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class ClanMineCollect extends ListenerAdapter {
	
	
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		try {
			String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
			String username = "ccbot";
			String password = "zs4GCaL";
			Connection conn = DriverManager.getConnection(url,username,password);
			java.sql.Statement stmt = conn.createStatement();
			if(args[0].equalsIgnoreCase(Main.prefix + "clanmine")) {
				if(args.length == 2) {
					if(args[1].equalsIgnoreCase("collect")) {
						String id = event.getAuthor().getId();
						int currentclan = 0;
						ResultSet rsinfos = stmt.executeQuery("select * from userinformations where userids =" + id);
						while (rsinfos.next()) {
							currentclan = rsinfos.getInt("clan");
							
						}
						if(currentclan == 0) {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You need to be in a clan to do this!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
						else {
							int maxminetime = 0;
							int minersworked = 0;
							long clanmoney = 0;
							int minelvl = 0;
							int workerenergy = 0;
							long minetime = 0;
							int havestone = 0;
							int havecoal = 0;
							int haveiron = 0;
							int havegold = 0;
							int havediamond = 0;
							int haveemerald = 0;
							
							ResultSet claninfos = stmt.executeQuery("select * from clans where clanid =" + currentclan);
							while (claninfos.next()) {
								minelvl = claninfos.getInt("clanminelvl");
								workerenergy = claninfos.getInt("workerenergy");
								minetime = claninfos.getLong("miningtime");
								clanmoney = claninfos.getLong("clanmoney");
								havestone = claninfos.getInt("stone");
								havecoal = claninfos.getInt("coal");
								haveiron = claninfos.getInt("iron");
								havegold = claninfos.getInt("gold");
								havediamond = claninfos.getInt("diamond");
								haveemerald = claninfos.getInt("emerald");
								minersworked = claninfos.getInt("minersworked");
								maxminetime = claninfos.getInt("maxminetime");
								
							}
							if(minelvl == 0) {
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Your clan doesnt have a mine! You can buy a mine at the clan store**");
								eb.setColor(0xff0000);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							else {
								double chancemultiplier = minelvl;
								
								//Default 95% stone 5%coal 0%others
								double slotsaviable = 100;
								double coal = 5;
								double iron = -3;
								double gold = -10.4;
								double diamond = -14;
								double emerald = -9.6;
								
								while(chancemultiplier > 1) {
									
									coal = coal + 1.5;
									
									if(minelvl >=5) {
										
										iron = iron + 1;
										
										if(minelvl >=15) {
											
											gold = gold + 0.8;
											
											
											if(minelvl >=30) {
												
												diamond = diamond + 0.5;
												
												if(minelvl >=50) {
													
													emerald = emerald + 0.2;
												}
											}
										}
									}
									
									chancemultiplier = chancemultiplier - 1;
									
								}
								if(emerald < 0) {
									emerald = 0;
								}							
								if(diamond < 0) {
									diamond = 0;
								}
								if(gold < 0) {
									gold = 0;
								}
								if(iron < 0) {
									iron = 0;
								}
								int emeraldfound = 0;
								int diamondfound = 0;
								int goldfound = 0;
								int ironfound = 0;
								int coalfound = 0;
								int stonefound = 0;
								
								if(slotsaviable > 0) {
									
									while(emerald > 0) {
										if(slotsaviable > 0) {
										slotsaviable = slotsaviable - 1;
										
										
										emeraldfound++;										
										}
										emerald = emerald - 1;
									}
									while(diamond > 0) {
										if(slotsaviable > 0) {
										slotsaviable = slotsaviable - 1;
										
										diamondfound++;
										}
										diamond = diamond - 1;
									}
									while(gold > 0) {
										if(slotsaviable > 0) {
										slotsaviable = slotsaviable - 1;
										
										goldfound++;
										}
										gold = gold - 1;
									}
									while(iron > 0) {
										if(slotsaviable > 0) {
										slotsaviable = slotsaviable - 1;
										
										ironfound++;
										}
										iron = iron - 1;
									}
									while(coal > 0) {
										if(slotsaviable > 0) {
										slotsaviable = slotsaviable - 1;
										coal = coal - 1;
										coalfound++;
										}
										coal = coal - 1;
									}
									
									while(slotsaviable >0) {
										stonefound++;
										slotsaviable = slotsaviable - 1;
									}
									
								}
								double subtraction = 100 / maxminetime;
								String percentage = "██████████";
								double percentagee = 0;
								String percentageest = "";
								String percentageest1 = "";
								String minerworking = "";
								long upgrademoney = 0;
								upgrademoney = minelvl * 250000;
								Calendar cal = Calendar.getInstance();
								cal.add(Calendar.MINUTE, -maxminetime);
					        	Calendar claimed = Calendar.getInstance();
					        	claimed.setTimeInMillis(minetime);
					        	long secs = ChronoUnit.SECONDS.between(cal.toInstant(), claimed.toInstant());
					        	int mins = 0;
					        	while (secs >= 60) {
					        		mins++;
					        		percentagee = percentagee + subtraction;
					        		secs = secs - 60;
					        	}
					        	percentagee = 100 - percentagee;
					        	percentageest = percentagee + "";
					        	percentageest1 = percentageest.substring(0, 4);
					        	
					        	if(percentagee > 90 && percentagee < 100) {
					        		percentage = "█████████░ ";
					        	}
					        	if(percentagee > 80 && percentagee < 90) {
					        		percentage = "████████░░ ";
					        	}
					        	if(percentagee > 70 && percentagee < 80) {
					        		percentage = "███████░░░ ";
					        	}
					        	if(percentagee > 60 && percentagee < 70) {
					        		percentage = "██████░░░░ ";
					        	}
					        	if(percentagee > 50 && percentagee < 60) {
					        		percentage = "█████░░░░░ ";
					        	}
					        	if(percentagee > 40 && percentagee < 50) {
					        		percentage = "████░░░░░░ ";
					        	}
					        	if(percentagee > 30 && percentagee < 40) {
					        		percentage = "███░░░░░░░ ";
					        	}
					        	if(percentagee > 20 && percentagee < 30) {
					        		percentage = "██░░░░░░░░ ";
					        	}
					        	if(percentagee > 10 && percentagee < 20) {
					        		percentage = "█░░░░░░░░░ ";
					        	}
					        	if(percentagee > 0 && percentagee < 10) {
					        		percentage = "░░░░░░░░░░ ";
					        	}
					        	
					        	
					        	if (minetime < cal.getTimeInMillis()) {
					        		
					        		if(minersworked == 1) {
					        			EmbedBuilder eb = new EmbedBuilder();
						        		eb.setDescription("**Were getting the items from the miners, please wait....**");
										eb.addField("You mined these items the last hour:",
												"**<:stone:753996163270705193>: " + stonefound + ", "
											+ "<:coal:753994248843427860>: "+ coalfound + ", \n "
											+ "<:iron_ingot:753994248659009547>: "+ ironfound + ", "
											+ "<:gold_ingot:753994248847622284>: "+ goldfound + ", \n"
											+ "<:diamond:753994248583512095>: " + diamondfound + ", "
											+ "<:emerald:753994248822718465>: "+ emeraldfound + "**", false);
										eb.addField("Your items currently:",
												"**<:stone:753996163270705193>: " + havestone + ", "
											+ "<:coal:753994248843427860>: "+ havecoal + ", \n "
											+ "<:iron_ingot:753994248659009547>: "+ haveiron + ", "
											+ "<:gold_ingot:753994248847622284>: "+ havegold + ", \n"
											+ "<:diamond:753994248583512095>: " + havediamond + ", "
											+ "<:emerald:753994248822718465>: "+ haveemerald + "**", false);
						        		eb.setColor(0xffc400);
						        		Message message =event.getChannel().sendMessage(eb.build()).complete();
						        		
						        		havestone = havestone + stonefound;
						        		havecoal = havecoal + coalfound;
						        		haveiron = haveiron + ironfound;
						        		havegold = havegold + goldfound;
						        		havediamond = havediamond + diamondfound;
						        		haveemerald = haveemerald + emeraldfound;
						        		String update1 = "UPDATE clans SET minersworked=0 WHERE clanid=" + currentclan;
										String update2 = "UPDATE clans SET stone=" +havestone + " WHERE clanid="+ currentclan;
										String update3 = "UPDATE clans SET coal=" +havecoal + " WHERE clanid="+ currentclan;
										String update4 = "UPDATE clans SET iron=" +haveiron + " WHERE clanid="+ currentclan;
										String update5 = "UPDATE clans SET gold=" +havegold + " WHERE clanid="+ currentclan;
										String update6 = "UPDATE clans SET diamond=" +havediamond + " WHERE clanid="+ currentclan;
										String update7 = "UPDATE clans SET emerald=" +haveemerald + " WHERE clanid="+ currentclan;
										stmt.executeUpdate(update1);
										stmt.executeUpdate(update2);
										stmt.executeUpdate(update3);
										stmt.executeUpdate(update4);
										stmt.executeUpdate(update5);
										stmt.executeUpdate(update6);
										stmt.executeUpdate(update7);
										eb.clearFields();
										eb.setDescription("**Got all items from the Miners!**");
										eb.addField("You mined these items the last hour:",
												"**<:stone:753996163270705193>: " + stonefound + ", "
											+ "<:coal:753994248843427860>: "+ coalfound + ", \n "
											+ "<:iron_ingot:753994248659009547>: "+ ironfound + ", "
											+ "<:gold_ingot:753994248847622284>: "+ goldfound + ", \n"
											+ "<:diamond:753994248583512095>: " + diamondfound + ", "
											+ "<:emerald:753994248822718465>: "+ emeraldfound + "**", false);
										eb.addField("Your items now:",
												"**<:stone:753996163270705193>: " + havestone + ", "
											+ "<:coal:753994248843427860>: "+ havecoal + ", \n "
											+ "<:iron_ingot:753994248659009547>: "+ haveiron + ", "
											+ "<:gold_ingot:753994248847622284>: "+ havegold + ", \n"
											+ "<:diamond:753994248583512095>: " + havediamond + ", "
											+ "<:emerald:753994248822718465>: "+ haveemerald + "**", false);
										eb.setColor(0x3dfc03);
										message.editMessage(eb.build()).queue();
										
						        	}
					        		else {
						        		EmbedBuilder eb = new EmbedBuilder();
						        		eb.setDescription("**You cant collect any items! Your miners didnt work!**");
						        		eb.setColor(0xff0000);
						        		event.getChannel().sendMessage(eb.build()).queue();
					        		}
					        		
					        	}
					        	else {
					        		minerworking = "**You cant collect your items! The miners are not done!** \n Done in: **"+mins+" m & "+secs+" s \n "+percentage + percentageest1 + "% done**";
					        		EmbedBuilder eb = new EmbedBuilder();
					        		eb.setDescription(minerworking);
					        		eb.setColor(0xff0000);
					        		event.getChannel().sendMessage(eb.build()).queue();
					        			
					        	}
							}
						}
					}
				}
			}
			conn.close();
		}		catch (IndexOutOfBoundsException e1) {return;} catch (NullPointerException e2) {return;}  catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
