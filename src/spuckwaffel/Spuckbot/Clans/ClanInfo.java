/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Clans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class ClanInfo extends ListenerAdapter 
{

	int joindone = 0;
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		try {
				String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
				String username = "ccbot";
				String password = "zs4GCaL";
				Connection conn = DriverManager.getConnection(url,username,password);
				java.sql.Statement stmt = conn.createStatement();
				if(args[0].equalsIgnoreCase(Main.prefix + "claninfo")) {
					if(args.length >= 2) {
						int clanid = 0;
						String clanname = "";
						clanname = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
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
							long clanmoney = 0;
							String clanfoundname = "";
							String clandescription = "";
							int membercount = 0;
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
							String clanadmin1 = "";
							String clanadmin2 = "";
							String clanadmin3 = "";	
							float clanmultiplier = 1;
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
								clanmoney = rsclansfound.getLong("clanmoney");
								clanadmin1 = rsclansfound.getString("clanadmin1");
								clanadmin2 = rsclansfound.getString("clanadmin2");
								clanadmin3 = rsclansfound.getString("clanadmin3");
								clanmultiplier = rsclansfound.getFloat("multiplier");
								
							}
							eb.setTitle("Clan found! Found clan " + clanfoundname + "!");
							eb.setColor(0x3dfc03);
							if(clandescription == null) {
								clandescription = "No description found!";
							
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
							if(clanadmin1 != null) {
								clanadmin1 = "<@" + clanadmin1 + ">";
							}
							if(clanadmin2 != null) {
								clanadmin2 = ", <@" + clanadmin2 + ">";
							}
							if(clanadmin3 != null) {
								clanadmin3 = ", <@" + clanadmin3 + ">";
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
							String admincheck = "";
							admincheck = clanadmin1 + clanadmin2 + clanadmin3;
							if(admincheck =="") {
								admincheck = "**No admins found!**";
							}
							eb.setThumbnail("https://i.ibb.co/LpX0Q2Q/unnamed.png");
							eb.setAuthor("Clans ~ The war has started");
							eb.setDescription("**Information about this clan:** \n"
									+ "**Clan Balance: " + new DecimalFormat("#,###").format(clanmoney) + "**<:ccoin:753994248885633046> \n"
									+ "**Coin Multiplier: x" + clanmultiplier + "**<:ccoin:753994248885633046> \n"
									+ "**Clan description:** _" + clandescription + "_ \n"
									+ "**Membercount:** " + membercount + "/15 \n"
									+ "**Owner:** :crown:" + clanowner + ":crown: \n"
									+ "**Admins: **:military_medal:" + admincheck + ":military_medal: \n"
									+ "**Members:** " + member1 + member2 + member3 + member4 + member5 + member6 + member7 
									+ member8 + member9 + member10 + member11 + member12 + member13 + member14 + member15 + "\n"
									+ "**Clan state:** " + clanstate );
							eb.setFooter(event.getMember().getIdLong() + "");
							message.editMessage(eb.build()).queue();
						}
						else {
							eb.setTitle("No result found for: " + clanname);
							eb.setDescription("Double check spelling and try again");
							eb.setColor(0xff0000);
							message.editMessage(eb.build()).queue();						
						}
						
					}
					else {
						event.getChannel().sendMessage(":no_entry: Invalid syntax! Use !claninfo <clanname>").queue();
					}
				}
				
				
				conn.close();
				} catch(Exception e){
					System.out.println(e);
		}
	}
}

