/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Clans.ClanMine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class ClanMineUpgrades extends ListenerAdapter {
	
	
	
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
				if(args.length >= 2) {
					if(args[1].equalsIgnoreCase("upgrade") || args[1].equalsIgnoreCase("store") || args[1].equalsIgnoreCase("upgrades")) {
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
							int powerpick = 0;
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
								powerpick = claninfos.getInt("powerpick");
								
							}
							if(minelvl == 0) {
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Your clan doesnt have a mine! You can buy a mine at the clan store**");
								eb.setColor(0xff0000);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							else {
								long upgrademoney = 0;
								upgrademoney = minelvl * 250000;
								int upgrademaxminetime0 = 70 - maxminetime;
								int upgrademaxminetime = upgrademaxminetime0 * 300000;
								if(args.length == 2) {
									EmbedBuilder eb = new EmbedBuilder();
									eb.setTitle("<:stone:753996163270705193><:coal:753994248843427860><:iron_ingot:753994248659009547>CLAN MINE UPGRADES"
											+ "<:gold_ingot:753994248847622284><:diamond:753994248583512095><:emerald:753994248822718465>");
									eb.setDescription("Here you can upgrade your mine and the workers! \n Some items may wont be shown because you already purchased them! \n**Your clans balance: \n "
											+ new DecimalFormat("#,###").format(clanmoney) + "<:ccoin:749969019842461787>**");
									eb.addField(":one: Upgrade Mine level", "**Current lvl: " + minelvl + " \n"+ new DecimalFormat("#,###").format(upgrademoney) + "<:ccoin:749969019842461787>** \n"
											+ "Upgrade your mine level for better items! \n"
											+ "``!clanmine upgrade 1``", true);
									eb.addField(":two: Buy <:energy:754015147265359913>", "**You have: " + workerenergy + "<:energy:754015147265359913> \n 15,000 <:ccoin:749969019842461787>** \n"
											+ "Without <:energy:754015147265359913> the workers wont work! \n"
											+ "``!clanmine upgrade 2``", true);
									if(maxminetime <= 20) {
										eb.addField("~~:three: Get faster miners~~ Maxed!_", "~~**Current time: " + maxminetime + "min \n " 
												+ new DecimalFormat("#,###").format(upgrademaxminetime) + "<:ccoin:749969019842461787>**\n"
												+ "(mining speed increases by 5 min each) \n``!clanmine upgrade 3``~~ ", true);
									}
									
									if(maxminetime > 20) {
										eb.addField(":three: Get faster miners", "**Current time: " + maxminetime + "min \n " 
												+ new DecimalFormat("#,###").format(upgrademaxminetime) + "<:ccoin:749969019842461787>**\n"
												+ "(mining speed increases by 5 min each) \n``!clanmine upgrade 3`` ", true);
									}
									if(powerpick == 0) {
										eb.addField(":four: Buy <a:superaxe:756515873933754408>", "** 15,000,000<:ccoin:749969019842461787>** \n Buy this power pickaxe to get 1.5x items! \n"
												+ "``!clanmine upgrade 4``", true);
									}
									if(powerpick == 1) {
										eb.addField("~~:four: Buy <a:superaxe:756515873933754408>~~ _Sold!_", "~~** 15,000,000<:ccoin:749969019842461787>** \n Buy this power pickaxe to get 1.5x items! \n"
												+ "``!clanmine upgrade 4``~~ ", true);
									}
									eb.setColor(0x3dfc03);
									event.getChannel().sendMessage(eb.build()).queue();
								}
								if(args.length == 3) {
									if(args[2].equalsIgnoreCase("1")) {
										if(clanmoney < upgrademoney) {
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**You dont have enough money to upgrade the mine! You only have: " + new DecimalFormat("#,###").format(clanmoney) + "<:ccoin:749969019842461787>**");
											eb.setColor(0xff0000);
											event.getChannel().sendMessage(eb.build()).queue();
										}
										else {
											minelvl = minelvl + 1;
											clanmoney = clanmoney - upgrademoney;
											String update1 = "UPDATE clans SET clanmoney="+ clanmoney + " WHERE clanid=" + currentclan;
											String update2 = "UPDATE clans SET clanminelvl="+ minelvl + " WHERE clanid=" + currentclan;
											stmt.executeUpdate(update1);
											stmt.executeUpdate(update2);
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**Sucessfully upgraded your clans mine to level " + minelvl + "!** \n Your clan now has " + new DecimalFormat("#,###").format(clanmoney) + "<:ccoin:749969019842461787>");
											eb.setColor(0x3dfc03);
											event.getChannel().sendMessage(eb.build()).queue();
										}
									 }
									if(args[2].equalsIgnoreCase("2")) {
										if(clanmoney < 15000) {
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**You dont have enough money to buy <:energy:754015147265359913>! You only have: " + new DecimalFormat("#,###").format(clanmoney) + "<:ccoin:749969019842461787>**");
											eb.setColor(0xff0000);
											event.getChannel().sendMessage(eb.build()).queue();
										}
										else {
											clanmoney = clanmoney - 15000;
											workerenergy = workerenergy +1;
											String update1 = "UPDATE clans SET clanmoney="+ clanmoney + " WHERE clanid=" + currentclan;
											String update2 = "UPDATE clans SET workerenergy="+ workerenergy + " WHERE clanid=" + currentclan;
											stmt.executeUpdate(update1);
											stmt.executeUpdate(update2);
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**Sucessfully bought 1<:energy:754015147265359913>! \n You now have " + workerenergy + "<:energy:754015147265359913>**");
											eb.setColor(0x3dfc03);
											event.getChannel().sendMessage(eb.build()).queue();
										}
									}
									
									if(args[2].equalsIgnoreCase("3")) {
										if (clanmoney < upgrademaxminetime) {
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**You dont have enough money to buy faster miners! You only have: " + new DecimalFormat("#,###").format(clanmoney) + "<:ccoin:749969019842461787>**");
											eb.setColor(0xff0000);
											event.getChannel().sendMessage(eb.build()).queue();
										}
										else {
											if(maxminetime <= 20) {
												EmbedBuilder eb = new EmbedBuilder();
												eb.setDescription("**You cant buy faster miners anymore!**");
												eb.setColor(0xff0000);
												event.getChannel().sendMessage(eb.build()).queue();
											}
											else {
												clanmoney = clanmoney - upgrademaxminetime;
												maxminetime = maxminetime - 5;
												String update1 = "UPDATE clans SET clanmoney="+ clanmoney + " WHERE clanid=" + currentclan;
												String update2 = "UPDATE clans SET maxminetime="+ maxminetime + " WHERE clanid=" + currentclan;
												stmt.executeUpdate(update1);
												stmt.executeUpdate(update2);
												EmbedBuilder eb = new EmbedBuilder();
												eb.setDescription("**Sucessfully bought faster miners! \n Your miners now need only " + maxminetime + " min for mining**");
												eb.setColor(0x3dfc03);
												event.getChannel().sendMessage(eb.build()).queue();
											}
										}
									}
									if(args[2].equalsIgnoreCase("4")) {
										if(powerpick == 1) {
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**You already have this item! You cant buy it more than once!**");
											eb.setColor(0xff0000);
											event.getChannel().sendMessage(eb.build()).queue();
										}
										else {
											if(clanmoney < 15000000) {
												EmbedBuilder eb = new EmbedBuilder();
												eb.setDescription("**You dont have enough money to buy this item! You only have: " + new DecimalFormat("#,###").format(clanmoney) + "<:ccoin:749969019842461787>**");
												eb.setColor(0xff0000);
												event.getChannel().sendMessage(eb.build()).queue();
											}
											else {
												clanmoney = clanmoney - 15000000;
												powerpick = 1;
												String update1 = "UPDATE clans SET clanmoney="+ clanmoney + " WHERE clanid=" + currentclan;
												String update2 = "UPDATE clans SET powerpick="+ powerpick + " WHERE clanid=" + currentclan;
												stmt.executeUpdate(update1);
												stmt.executeUpdate(update2);
												EmbedBuilder eb = new EmbedBuilder();
												eb.setDescription("**Sucessfully bought the power pickaxe <a:superaxe:756515873933754408>!**");
												eb.setColor(0x3dfc03);
												event.getChannel().sendMessage(eb.build()).queue();
											}
										}
									}
								}
							}
						}
					}
				}
			}
			conn.close();
		}		catch (IndexOutOfBoundsException e1) {return;} catch (NullPointerException e2) {return;}  catch (SQLException e1) {
			event.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e1.toString()).queue(); }
	}

}
