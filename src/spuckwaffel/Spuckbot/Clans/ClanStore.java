/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Clans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class ClanStore extends ListenerAdapter {
	
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		try {
			String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
			String username = "ccbot";
			String password = "zs4GCaL";
			Connection conn = DriverManager.getConnection(url,username,password);
			java.sql.Statement stmt = conn.createStatement();
			
			if(args[0].equalsIgnoreCase(Main.prefix + "clanstore")) {
				if(args.length == 1) {
					String id = event.getAuthor().getId();
					int currentclan = 0;
					long mymoney = 0;
					int mydiamonds = 0;
					ResultSet rsinfos = stmt.executeQuery("select * from userinformations where userids =" + id);
					while (rsinfos.next()) {
						currentclan = rsinfos.getInt("clan");
						mymoney = rsinfos.getLong("coins");
						mydiamonds = rsinfos.getInt("diamonds");
					}
					if(currentclan == 0) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**You cant view the clan store! You are not in a clan!**");
						eb.setColor(0xff0000);
						event.getChannel().sendMessage(eb.build()).queue();
					}
					else {
						long clanmoney = 0;
						double clanmultiplier = 0;
						int clanxp = 0;
						double levelxp = 20;
						int clanlevel = 0;
						int easylvlxp = 0;
						double eachlvl = 1.5;
						ResultSet claninfos = stmt.executeQuery("select * from clans where clanid =" + currentclan);
						while (claninfos.next()) {
							clanmoney = claninfos.getLong("clanmoney");
							clanmultiplier = claninfos.getDouble("multiplier");
							clanxp = claninfos.getInt("clanxp");
						}
						
						//copy pasta this
						
						//clanxp is the xp the clan has
						//levelxp is by defauly 20, it will increase each level you have (levelxp = levelxp * eachlvl)
						//eachlvl is by defauly 1.5 (Each level needs 1.5 times more xp than the level before)
						//clanlevel will show you what clanlevel you are (makes sense huh? xd)
						
						if(clanxp >= 20) {
							while(clanxp >= levelxp){
								levelxp = levelxp * eachlvl;
								clanlevel = clanlevel + 1;
							}
						}
						else {
							clanlevel = 0;
						}
						easylvlxp = (int)levelxp;
						
						//until here for easy xp calculating
						
						EmbedBuilder eb = new EmbedBuilder();
						eb.setThumbnail("https://i.ibb.co/LpX0Q2Q/unnamed.png");
						eb.setTitle("CLAN STORE");
						eb.setDescription("See here some advanced stats or buy with **your** money stuff for your clan such as boosts or upgrades!");
						eb.addField("Clan Level: " + clanlevel, "**Experience: " + clanxp + "/" + easylvlxp + "\n "
								+ "<:ccoin:753994248885633046> Multiplier: " + clanmultiplier + "**", false);
						eb.addField("Your Balance: ", "**<:ccoin:753994248885633046>"+mymoney + " \n <:diamond:753994248583512095>" + mydiamonds + "**", true);
						eb.addField("Clan balance: ", "**<:ccoin:753994248885633046>"+clanmoney + "**", true);
						eb.addField("<:blank:743124455592820876>","**Store:** \n  <:blank:743124455592820876>", false);
						eb.setColor(0x3dfc03);
						event.getChannel().sendMessage(eb.build()).queue();
					}
				}
			}
			
			conn.close();
		}		catch (IndexOutOfBoundsException e1) {return;} catch (NullPointerException e2) {return;}  catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
}
