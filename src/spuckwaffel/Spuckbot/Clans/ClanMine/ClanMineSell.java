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
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class ClanMineSell extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		try {
			String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
			String username = "ccbot";
			String password = "zs4GCaL";
			Connection conn = DriverManager.getConnection(url,username,password);
			java.sql.Statement stmt = conn.createStatement();
			if(args[0].equalsIgnoreCase(Main.prefix + "clanmine")) {
				if(args.length == 3) {
					if(args[1].equalsIgnoreCase("sell")) {
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
							long clanmoney = 0;
							int completed = 0;
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
								clanmoney = claninfos.getLong("clanmoney");
								
								
							}
							if(minelvl == 0) {
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Your clan doesnt have a mine! You can buy a mine at the clan store**");
								eb.setColor(0xff0000);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							
							else {
								if(args[2].equalsIgnoreCase("all")) {
									completed = 1;
									totalitems = stone + coal + iron + gold + diamond + emerald;
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("**Selling " + totalitems +" Items <a:loading:743801566451990570>\n"
												+ "<:stone:753996163270705193>" + stone + "\n"
												+ "<:coal:753994248843427860>" + coal + "\n"
												+ "<:iron_ingot:753994248659009547>" + iron + "\n"
												+ "<:gold_ingot:753994248847622284>" + gold + "\n"
												+ "<:diamond:753994248583512095>"+ diamond + "\n"
												+ "<:emerald:753994248822718465>"+ emerald + "**");
									eb.setColor(0xffb300);
									Message message =event.getChannel().sendMessage(eb.build()).complete();
									long moneygot = stone * 1000 + coal * 7500 + iron * 20000 + gold * 50000 + diamond * 80000 + emerald * 250000;
									clanmoney = clanmoney + moneygot;
									String update1 = "UPDATE clans SET clanmoney=" + clanmoney + " WHERE clanid=" + currentclan;
									String update2 = "UPDATE clans SET stone=0 WHERE clanid="+ currentclan;
									String update3 = "UPDATE clans SET coal=0 WHERE clanid="+ currentclan;
									String update4 = "UPDATE clans SET iron=0 WHERE clanid="+ currentclan;
									String update5 = "UPDATE clans SET gold=0 WHERE clanid="+ currentclan;
									String update6 = "UPDATE clans SET diamond=0 WHERE clanid="+ currentclan;
									String update7 = "UPDATE clans SET emerald=0 WHERE clanid="+ currentclan;
									stmt.executeUpdate(update1);
									stmt.executeUpdate(update2);
									stmt.executeUpdate(update3);
									stmt.executeUpdate(update4);
									stmt.executeUpdate(update5);
									stmt.executeUpdate(update6);
									stmt.executeUpdate(update7);
									eb.setDescription("**:white_check_mark: Sold all items for " + new DecimalFormat("#,###").format(moneygot) + "<:ccoin:753994248885633046>** \n"
											+ "Sold items: \n **<:stone:753996163270705193>" + stone + "\n"
											+ "<:coal:753994248843427860>" + coal + "\n"
											+ "<:iron_ingot:753994248659009547>" + iron + "\n"
											+ "<:gold_ingot:753994248847622284>" + gold + "\n"
											+ "<:diamond:753994248583512095>"+ diamond + "\n"
											+ "<:emerald:753994248822718465>"+ emerald + "**");
									eb.setColor(0x3dfc03);
									message.editMessage(eb.build()).queue();
									
								}
								if(args[2].equalsIgnoreCase("stone")) {
									completed = 1;
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("**Selling " + stone + "<:stone:753996163270705193>** <a:loading:743801566451990570>");
									eb.setColor(0xffb300);
									Message message =event.getChannel().sendMessage(eb.build()).complete();
									long moneygot = stone * 1000;
									clanmoney = clanmoney + moneygot;
									String update1 = "UPDATE clans SET clanmoney=" + clanmoney + " WHERE clanid=" + currentclan;
									String update2 = "UPDATE clans SET stone=0 WHERE clanid="+ currentclan;
									stmt.executeUpdate(update1);
									stmt.executeUpdate(update2);
									eb.setDescription("**:white_check_mark: Sold all stone for " + new DecimalFormat("#,###").format(moneygot) + "<:ccoin:753994248885633046>**");
									eb.setColor(0x3dfc03);
									message.editMessage(eb.build()).queue();
								}
								if(args[2].equalsIgnoreCase("coal")) {
									completed = 1;
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("**Selling " + coal + "<:coal:753994248843427860>** <a:loading:743801566451990570>");
									eb.setColor(0xffb300);
									Message message =event.getChannel().sendMessage(eb.build()).complete();
									long moneygot = coal * 7500;
									clanmoney = clanmoney + moneygot;
									String update1 = "UPDATE clans SET clanmoney=" + clanmoney + " WHERE clanid=" + currentclan;
									String update2 = "UPDATE clans SET coal=0 WHERE clanid="+ currentclan;
									stmt.executeUpdate(update1);
									stmt.executeUpdate(update2);
									eb.setDescription("**:white_check_mark: Sold all coal for " + new DecimalFormat("#,###").format(moneygot) + "<:ccoin:753994248885633046>**");
									eb.setColor(0x3dfc03);
									message.editMessage(eb.build()).queue();
								}
								if(args[2].equalsIgnoreCase("iron")) { 
									completed = 1;
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("**Selling " + iron + "<:iron_ingot:753994248659009547>** <a:loading:743801566451990570>");
									eb.setColor(0xffb300);
									Message message =event.getChannel().sendMessage(eb.build()).complete();
									long moneygot = iron * 20000;
									clanmoney = clanmoney + moneygot;
									String update1 = "UPDATE clans SET clanmoney=" + clanmoney + " WHERE clanid=" + currentclan;
									String update2 = "UPDATE clans SET iron=0 WHERE clanid="+ currentclan;
									stmt.executeUpdate(update1);
									stmt.executeUpdate(update2);
									eb.setDescription("**:white_check_mark: Sold all iron for " + new DecimalFormat("#,###").format(moneygot) + "<:ccoin:753994248885633046>**");
									eb.setColor(0x3dfc03);
									message.editMessage(eb.build()).queue();
								}
								if(args[2].equalsIgnoreCase("gold")) { 
									completed = 1;
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("**Selling " + gold + "<:diamond:753994248583512095>** <a:loading:743801566451990570>");
									eb.setColor(0xffb300);
									Message message =event.getChannel().sendMessage(eb.build()).complete();
									long moneygot = gold * 50000;
									clanmoney = clanmoney + moneygot;
									String update1 = "UPDATE clans SET clanmoney=" + clanmoney + " WHERE clanid=" + currentclan;
									String update2 = "UPDATE clans SET gold=0 WHERE clanid="+ currentclan;
									stmt.executeUpdate(update1);
									stmt.executeUpdate(update2);
									eb.setDescription("**:white_check_mark: Sold all gold for " + new DecimalFormat("#,###").format(moneygot) + "<:ccoin:753994248885633046>**");
									eb.setColor(0x3dfc03);
									message.editMessage(eb.build()).queue();
								}
								if(args[2].equalsIgnoreCase("diamond")) {
									completed = 1;
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("**Selling " + diamond + "<:diamond:753994248583512095>** <a:loading:743801566451990570>");
									eb.setColor(0xffb300);
									Message message =event.getChannel().sendMessage(eb.build()).complete();
									long moneygot = diamond * 80000;
									clanmoney = clanmoney + moneygot;
									String update1 = "UPDATE clans SET clanmoney=" + clanmoney + " WHERE clanid=" + currentclan;
									String update2 = "UPDATE clans SET diamond=0 WHERE clanid="+ currentclan;
									stmt.executeUpdate(update1);
									stmt.executeUpdate(update2);
									eb.setDescription("**:white_check_mark: Sold all diamonds for " + new DecimalFormat("#,###").format(moneygot) + "<:ccoin:753994248885633046>**");
									eb.setColor(0x3dfc03);
									message.editMessage(eb.build()).queue();
								}
								if(args[2].equalsIgnoreCase("emerald")) {
									completed = 1;
									EmbedBuilder eb = new EmbedBuilder();
									eb.setDescription("**Selling " + emerald + "<:emerald:753994248822718465>** <a:loading:743801566451990570>");
									eb.setColor(0xffb300);
									Message message =event.getChannel().sendMessage(eb.build()).complete();
									long moneygot = emerald * 250000;
									clanmoney = clanmoney + moneygot;
									String update1 = "UPDATE clans SET clanmoney=" + clanmoney + " WHERE clanid=" + currentclan;
									String update2 = "UPDATE clans SET emerald=0 WHERE clanid="+ currentclan;
									stmt.executeUpdate(update1);
									stmt.executeUpdate(update2);
									eb.setDescription("**:white_check_mark: Sold all emeralds for " + new DecimalFormat("#,###").format(moneygot) + "<:ccoin:753994248885633046>**");
									eb.setColor(0x3dfc03);
									message.editMessage(eb.build()).queue();
								}
								if(completed != 1) {
									
								}
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
