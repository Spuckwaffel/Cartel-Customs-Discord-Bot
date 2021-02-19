/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Commands;


import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HigherLower extends ListenerAdapter {
	
	public static Map<Member, Long> betcooldown = new HashMap<>();	
	public static Map<Member, Long> highlowcooldown = new HashMap<>();	
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split(" ");

		if (args[0].equalsIgnoreCase("!hl") || args[0].equalsIgnoreCase("!higherlower") || args[0].equalsIgnoreCase("!highlow") || args[0].equalsIgnoreCase("!higherorlower")) {
			long tobet = -1;
			try {
				tobet = Long.parseLong(args[1]);
			} catch (ArrayIndexOutOfBoundsException e1) {
				e.getChannel().sendMessage("Invalid Syntax: `!hl <amount>`").queue();
				return;
			} catch (NumberFormatException e2) {
				e.getChannel().sendMessage("Invalid Syntax: `!hl <amount>`").queue();
				return;
			}
			try (Connection con = DriverManager.getConnection("jdbc:mysql://xx/CCBot?serverTimezone=UTC", "ccbot", "zs4GCaL")) {
				ResultSet canBet = con.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "
    					+e.getAuthor().getId());
				java.sql.Statement stmt = con.createStatement();
				long bux = -1;
    			while (canBet.next()) {
    				bux = canBet.getLong("coins");
    				if (tobet > bux && bux != -1337) {
    					if (bux > 0) {
    						e.getChannel().sendMessage("You don't have <:ccoin:753994248885633046>"+tobet+" to bet! You can only bet a maximum of <:ccoin:753994248885633046>"+bux).queue();
    					} else {
    						e.getChannel().sendMessage("You don't have any <:ccoin:753994248885633046> to bet!").queue();
    					}
    					betcooldown.put(e.getMember(), System.currentTimeMillis());
    					return;
    				}
    				if (tobet < 10) {
    					e.getChannel().sendMessage("You must bet <:ccoin:753994248885633046>10 or more!").queue();
    					betcooldown.put(e.getMember(), System.currentTimeMillis());
    					return;
    				}
    				if (bux != -1337) {
    					long newbux = bux - tobet;
    					String update1 = "UPDATE userinformations SET coins = "+newbux+" WHERE userids = "+e.getMember().getUser().getId();
    					stmt.executeUpdate(update1);
    				}
    				EmbedBuilder eb = new EmbedBuilder();
    				eb.setColor(Color.yellow);
    				eb.setAuthor("Casino ~ Higher or Lower", null, e.getAuthor().getEffectiveAvatarUrl());
    				eb.setDescription("__**Instructions**__\nReact with :arrow_up: for Upper\nReact with :arrow_down: for Lower"
    						+ "\nReact with :moneybag: to Cashout\n:newspaper: **Info** The minimum is 1, and the maximum is 100"
    						+ "\n:bulb: **Tip** You won't get any money unless you cashout, even if you lose!");
    				e.getChannel().sendMessage(eb.build()).queue();
    				eb.setFooter(e.getAuthor().getId(), null);
    				eb.setDescription("*Starting Bet: <:ccoin:753994248885633046>*"+args[1]+"\n**Cashout Prize: <:ccoin:753994248885633046>**"+args[1]+"");{
						eb.setDescription("*Starting Bet: <:ccoin:753994248885633046>*"+args[1]+"\n**Cashout Prize: <:ccoin:753994248885633046>**"+args[1]+"");
					}
    				int rand = new Random().nextInt(100);
    				rand++;
    				eb.addField("Higher or Lower: ", ""+rand, false);
    				Message msg = e.getChannel().sendMessage(eb.build()).complete();
    				msg.addReaction("⬆").queue();
    				msg.addReaction("⬇").queue();
    				msg.addReaction("💰").queue();
     			}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
		
		
	}
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent e) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://xx/CCBot?serverTimezone=UTC", "ccbot", "zs4GCaL")) {
			if (e.getChannel().retrieveMessageById(e.getMessageId()).complete().getEmbeds().get(0).getAuthor().getName().equals("Casino ~ Higher or Lower")) {
				if (!e.getMember().equals(e.getGuild().getMemberById(e.getChannel().retrieveMessageById(e.getMessageId()).complete().getEmbeds().get(0).getFooter().getText()))
					&& !e.getMember().getUser().isBot()) {
					e.getTextChannel().removeReactionById(e.getMessageId(), e.getReaction().getReactionEmote().getName(), e.getMember().getUser()).queue();
					con.close();
					return;
				}
				ResultSet canBet = con.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "
    					+e.getMember().getUser().getId());
				java.sql.Statement stmt = con.createStatement();
				long bux = -1;
    			while (canBet.next()) {
    				bux = canBet.getLong("coins");
    			}
				if (e.getMember().getUser().isBot()) {
					con.close();
					return;
				}
				MessageEmbed oldeb = e.getChannel().retrieveMessageById(e.getMessageId()).complete().getEmbeds().get(0);
				int num = oldeb.getFields().size() - 1;
				try {
					num = Integer.parseInt(e.getChannel().retrieveMessageById(e.getMessageId()).complete().getEmbeds().get(0).getFields().get(num).getValue());
				} catch (IndexOutOfBoundsException e1) {con.close(); return;} catch (NumberFormatException e2) { con.close();return;}
				int rand = new Random().nextInt(101);
				EmbedBuilder eb = new EmbedBuilder(oldeb);
				eb.addField("Higher or Lower:", ""+rand, false);
				if (!e.getMember().getUser().isBot()) {
					//e.getChannel().sendMessage(newnum+" - newnum\n`"+e.getReactionEmote().getName()+"`").queue();
				}
				if (e.getReactionEmote().getName().equals("💰") || eb.getFields().size() >= 6) {
					if (eb.getFields().size() <= 2) {
						e.getTextChannel().removeReactionById(e.getMessageId(), e.getReaction().getReactionEmote().getName(), e.getMember().getUser()).queue();
						e.getChannel().sendMessage(e.getMember().getAsMention()+", You must have at least one guess before cashing out!").complete().delete().queueAfter(15, TimeUnit.SECONDS);
						return;
					}
					long cashout = Long.parseLong(oldeb.getDescription().split("\n")[1].replace("**Cashout Prize: <:ccoin:753994248885633046>**", ""));
					if (cashout > 3000 && eb.getFields().size() <= 4) {
						e.getChannel().sendMessage("You must have 3+ guesses to cash out prizes over <:ccoin:753994248885633046>3000!").queue();
						return;
					}
					long newbux = bux + cashout;
					if (bux != -1337) {
						String update2="UPDATE userinformations SET coins = "+newbux+" WHERE userids = "+e.getMember().getUser().getId();
						stmt.executeUpdate(update2);
					}
					long winnings = cashout - Long.parseLong(oldeb.getDescription().split("\n")[0].replace("*Starting Bet: <:ccoin:753994248885633046>*", ""));
					eb.addField("Successfully Cashed Out <:ccoin:753994248885633046>"+winnings, "New Balance: <:ccoin:753994248885633046>"+newbux, false);
					eb.setColor(Color.green);
					e.getChannel().retrieveMessageById(e.getMessageId()).complete().editMessage(eb.build()).queue();
					EmbedBuilder log = new EmbedBuilder().setAuthor(e.getMember().getEffectiveName()+" used !higherlower", null, e.getUser().getEffectiveAvatarUrl()).setTimestamp(Instant.from(ZonedDateTime.now()));
    				log.setDescription(e.getMember().getAsMention()+" cashed out **<:ccoin:753994248885633046>"+cashout+"**").setColor(Color.green)
    				.addField(e.getMember().getEffectiveName()+" New Balance", "<:ccoin:753994248885633046>**"+newbux+"**", true)
    				.setFooter(e.getGuild().toString(), e.getGuild().getIconUrl()).setTimestamp(Instant.from(ZonedDateTime.now()));
    				e.getGuild().getTextChannelById("749958496241451049").sendMessage(log.build()).queue();
					con.close();
					return;
					
				}
				if (rand < num && e.getReactionEmote().getName().equals("⬇") || rand > num && e.getReactionEmote().getName().equals("⬆") 
						|| rand == num && e.getReactionEmote().getName().equals("⬇") || rand == num && e.getReactionEmote().getName().equals("⬆")) {
					long start = Long.parseLong(oldeb.getDescription().split("\n")[0].replace("*Starting Bet: <:ccoin:753994248885633046>*", ""));
					long cashout = Long.parseLong(oldeb.getDescription().split("\n")[1].replace("**Cashout Prize: <:ccoin:753994248885633046>**", ""));
					cashout = cashout + Math.abs(start / 2);
					
				} else {
					eb.addField("You Lose!", "You guessed wrong! Game Over!", false);
					eb.setColor(Color.red);
					e.getChannel().retrieveMessageById(e.getMessageId()).complete().editMessage(eb.build()).queue();
					con.close();
					return;
				}
				e.getChannel().retrieveMessageById(e.getMessageId()).complete().editMessage(eb.build()).queue();
			}
		} catch (IndexOutOfBoundsException e1) {return;} catch (NullPointerException e2) {return;} catch (SQLException e3) {
			e3.printStackTrace();
		}
	}
	
	@Override
	public void onMessageReactionRemove(MessageReactionRemoveEvent e) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://xx/CCBot?serverTimezone=UTC", "ccbot", "zs4GCaL")) {
			if (e.getChannel().retrieveMessageById(e.getMessageId()).complete().getEmbeds().get(0).getAuthor().getName().equals("Casino ~ Higher or Lower")) {
				if (!e.getMember().equals(e.getGuild().getMemberById(e.getChannel().retrieveMessageById(e.getMessageId()).complete().getEmbeds().get(0).getFooter().getText()))
					&& !e.getMember().getUser().isBot()) {
					e.getTextChannel().removeReactionById(e.getMessageId(), e.getReaction().getReactionEmote().getName(), e.getMember().getUser()).queue();
					con.close();
					return;
				}
				ResultSet canBet = con.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "
    					+e.getMember().getUser().getId());
				java.sql.Statement stmt = con.createStatement();
				long bux = -1;
    			while (canBet.next()) {
    				bux = canBet.getLong("coins");
    			}
				if (e.getMember().getUser().isBot()) {
					con.close();
					return;
				}
				MessageEmbed oldeb = e.getChannel().retrieveMessageById(e.getMessageId()).complete().getEmbeds().get(0);
				int num = oldeb.getFields().size() - 1;
				try {
					num = Integer.parseInt(e.getChannel().retrieveMessageById(e.getMessageId()).complete().getEmbeds().get(0).getFields().get(num).getValue());
				} catch (IndexOutOfBoundsException e1) {con.close(); return;} catch (NumberFormatException e2) { con.close();return;}
				int rand = new Random().nextInt(101);
				EmbedBuilder eb = new EmbedBuilder(oldeb);
				eb.addField("Higher or Lower:", ""+rand, false);
				if (!e.getMember().getUser().isBot()) {
					//e.getChannel().sendMessage(newnum+" - newnum\n`"+e.getReactionEmote().getName()+"`").queue();
				}
				if (e.getReactionEmote().getName().equals("💰") || eb.getFields().size() >= 6) {
					if (eb.getFields().size() <= 2) {
						return;
					}
					long start = Long.parseLong(oldeb.getDescription().split("\n")[0].replace("*Starting Bet: <:ccoin:753994248885633046>*", ""));
					long cashout = Long.parseLong(oldeb.getDescription().split("\n")[1].replace("**Cashout Prize: <:ccoin:753994248885633046>**", ""));
					if (eb.getFields().size() >= 6) {
						cashout = cashout + Math.abs(start / 2);
					}
					if (cashout > 3000 && eb.getFields().size() <= 4) {
						e.getChannel().sendMessage("You must have 3+ guesses to cash out prizes over <:ccoin:753994248885633046>3000!").queue();
						return;
					}
					long newbux = bux + cashout;
					if (bux != -1337) {
						String update3 = "UPDATE userinformations SET coins = "+newbux+" WHERE userids = "+e.getMember().getUser().getId();
						stmt.executeUpdate(update3);
					}
					long winnings = cashout - Long.parseLong(oldeb.getDescription().split("\n")[0].replace("*Starting Bet: <:ccoin:753994248885633046>*", ""));
					eb.addField("Successfully Cashed Out <:ccoin:753994248885633046>"+winnings, "New Balance: <:ccoin:753994248885633046>"+newbux, false);
					eb.setColor(Color.green);
					e.getChannel().retrieveMessageById(e.getMessageId()).complete().editMessage(eb.build()).queue();
					EmbedBuilder log = new EmbedBuilder().setAuthor(e.getMember().getEffectiveName()+" used !higherlower", null, e.getUser().getEffectiveAvatarUrl()).setTimestamp(Instant.from(ZonedDateTime.now()));
    				log.setDescription(e.getMember().getAsMention()+" cashed out **<:ccoin:753994248885633046>"+cashout+"**").setColor(Color.green)
    				.addField(e.getMember().getEffectiveName()+" New Balance", "<:ccoin:753994248885633046>**"+newbux+"**", true)
    				.setFooter(e.getGuild().toString(), e.getGuild().getIconUrl()).setTimestamp(Instant.from(ZonedDateTime.now()));
    				e.getGuild().getTextChannelById("749958496241451049").sendMessage(log.build()).queue();
					con.close();
					return;
					
				}
				if (rand < num && e.getReactionEmote().getName().equals("⬇") || rand > num && e.getReactionEmote().getName().equals("⬆") 
						|| rand == num && e.getReactionEmote().getName().equals("⬇") || rand == num && e.getReactionEmote().getName().equals("⬆")) {
					long start = Long.parseLong(oldeb.getDescription().split("\n")[0].replace("*Starting Bet: <:ccoin:753994248885633046>*", ""));
					long cashout = Long.parseLong(oldeb.getDescription().split("\n")[1].replace("**Cashout Prize: <:ccoin:753994248885633046>**", ""));
					cashout = cashout + Math.abs(start / 2);
					{
						eb.setDescription("*Starting Bet: <:ccoin:753994248885633046>*"+start+"\n**Cashout Prize: <:ccoin:753994248885633046>**"+cashout+"");
					}
					
				} else {
					eb.addField("You Lose!", "You guessed wrong! Game Over!\nNew Balance: <:ccoin:753994248885633046>"+bux, false);
					eb.setColor(Color.red);
					e.getChannel().retrieveMessageById(e.getMessageId()).complete().editMessage(eb.build()).queue();
					con.close();
					return;
				}
				e.getChannel().retrieveMessageById(e.getMessageId()).complete().editMessage(eb.build()).queue();
			}
		} catch (IndexOutOfBoundsException e1) {return;} catch (NullPointerException e2) {return;}  catch (SQLException e1) {
			e.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e1.toString()).queue();
		}
		
	}

}

