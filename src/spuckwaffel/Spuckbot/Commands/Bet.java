/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class Bet extends ListenerAdapter {
	
	@SuppressWarnings("deprecation")
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
         if (event.getAuthor().isBot() && !event.getAuthor().equals(event.getJDA().getSelfUser())|| event.getAuthor().isFake()) {
			return;
		}
         try {
 			String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
 			String username = "ccbot";
 			String password = "zs4GCaL";
 			Connection conn = DriverManager.getConnection(url,username,password);
 			java.sql.Statement stmt = conn.createStatement();
 			
 			if(args[0].equalsIgnoreCase(Main.prefix + "bet")) { 
				 if(args.length <2) {
					 event.getChannel().sendMessage("Wrong usage! Use !buy <number>").queue();
					 
				 }
				 
				 else {
					 	int works = 1;
						long tobet = -1;
						try {
							tobet = Long.parseLong(args[1]);
						} catch (NumberFormatException e1) {
							works = 0;
							event.getChannel().sendMessage(":no_entry: You need to bet at least  **1**<:ccoin:753994248885633046>!").queue();
						}
						if (works == 1) {
							String id = event.getAuthor().getId();
							long coins = 0;
							ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
							while (rs.next()) {
								coins =rs.getLong("coins");
							}
							if (coins < tobet) {
								event.getChannel().sendMessage(":no_entry: You cant bet more than you have! You only have **" + coins +"<:ccoin:753994248885633046>!**").queue();
							}
							else {
								if (tobet < 1) {
									event.getChannel().sendMessage(":no_entry: You need to bet at least **1<:ccoin:753994248885633046>!**").queue();
								}
								else {
									int number1 =0;
									int number2 =0;
									number1 = new Random().nextInt(10);
									number2 = new Random().nextInt(10);
									EmbedBuilder eb = new EmbedBuilder();
									eb.setAuthor(event.getMember().getEffectiveName() + "'s Betting Game", null,event.getAuthor().getEffectiveAvatarUrl());
									eb.setDescription("Betting: ``" + tobet + "``<:ccoin:753994248885633046>");
									eb.addField("Your number", "``"+number1 + "``", false);
									eb.addField("Enemies number", "<a:loading:743801566451990570>", false);
									eb.setColor(0x03a7ff);
									Message message =event.getChannel().sendMessage(eb.build()).complete();
									eb.getFields().clear();
									eb.addField("Your number", "``"+number1 + "``", false);
									eb.addField("Enemies number", "``"+number2 + "``", false);
									if(number1 < number2) {
										long endmoney = coins - tobet;
										eb.setColor(0xff0303);
										eb.addField("Too bad!", "You lost **"+tobet+"**<:ccoin:753994248885633046>", false);
										eb.addField("You now have", "<:ccoin:753994248885633046>**" + endmoney + "**", false);
										String update = "update userinformations set coins='"+ endmoney +"' where userids=" + id;
										stmt.executeUpdate(update);
									}
									if(number1 == number2) {
										long endmoney = coins;
										eb.setColor(0xebeb34);
										eb.addField("Too bad!", "You tied with the bot!", false);
										eb.addField("You now have", "<:ccoin:753994248885633046>**" + endmoney + "**", false);
										String update = "update userinformations set coins='"+ endmoney +"' where userids=" + id;
										stmt.executeUpdate(update);
									}
									if(number1 > number2) {
										long endmoney = coins +tobet;
										eb.setColor(0x3dfc03);
										eb.addField("GG!", "You got **"+tobet+"**<:ccoin:753994248885633046>", false);
										eb.addField("You now have", "<:ccoin:753994248885633046>**" + endmoney + "**", false);
										String update = "update userinformations set coins='"+ endmoney +"' where userids=" + id;
										stmt.executeUpdate(update);
									}
									message.editMessage(eb.build()).queueAfter(1, TimeUnit.SECONDS);
									
								}
							}
							conn.close();
						}
						
				 }
			 }
         }
         catch (Exception e) {
        	 event.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e.toString()).queue();
         }
	}
	

}
