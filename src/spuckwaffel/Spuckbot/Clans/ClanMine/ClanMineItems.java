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

public class ClanMineItems extends ListenerAdapter {
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
					if(args[1].equalsIgnoreCase("items")) {
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
							int totalitems= 0;
							int minelvl = 0;
							int stone = 0;
							int coal = 0;
							int iron = 0;
							int gold = 0;
							int diamond = 0;
							int emerald = 0;
							ResultSet claninfos = stmt.executeQuery("select * from clans where clanid =" + currentclan);
							while (claninfos.next()) {
								minelvl = claninfos.getInt("clanminelvl");
								stone = claninfos.getInt("stone");
								coal = claninfos.getInt("coal");
								iron = claninfos.getInt("iron");
								gold = claninfos.getInt("gold");
								diamond = claninfos.getInt("diamond");
								emerald = claninfos.getInt("emerald");
								
								
							}
							if(minelvl == 0) {
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Your clan doesnt have a mine! You can buy a mine at the clan store**");
								eb.setColor(0xff0000);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							
							else {
								totalitems = stone + coal + iron + gold + diamond + emerald;
								long moneygot = stone * 1000 + coal * 7500 + iron * 20000 + gold * 50000 + diamond * 80000 + emerald * 250000;
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**You have " + new DecimalFormat("#,###").format(totalitems) +" Items \n"
											+ "<:stone:753996163270705193>" + new DecimalFormat("#,###").format(stone) + "\n"
											+ "<:coal:753994248843427860>" + new DecimalFormat("#,###").format(coal) + "\n"
											+ "<:iron_ingot:753994248659009547>" + new DecimalFormat("#,###").format(iron) + "\n"
											+ "<:gold_ingot:753994248847622284>" + new DecimalFormat("#,###").format(gold) + "\n"
											+ "<:diamond:753994248583512095>"+ new DecimalFormat("#,###").format(diamond) + "\n"
											+ "<:emerald:753994248822718465>"+ new DecimalFormat("#,###").format(emerald) + "**\n"
											+ "_Worth " + new DecimalFormat("#,###").format(moneygot) + "<:ccoin:753994248885633046>_");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
						}
					}
				}
			}
			conn.close();
		}		catch (IndexOutOfBoundsException e1) {return;} catch (NullPointerException e2) {return;}  catch (SQLException e1) {
			event.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e1.toString()).queue();
		}
	}
}
