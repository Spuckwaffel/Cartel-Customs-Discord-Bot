package spuckwaffel.Spuckbot.Commands;


import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Slots extends ListenerAdapter {

	public String getSlot(User m) {
		int rand = new Random().nextInt(10) + 1;
		if (rand == 1 || rand == 2) {
			return ":cherries:";
		} else if (rand == 3 || rand == 4) {
			return ":apple:";
		}  else if (rand == 5 || rand == 6) {
			return ":lemon:";
		}  else if (rand == 7 || rand == 8) {
			return ":pineapple:";
		}  else if (rand == 9 || rand == 10) {
			return ":watermelon:";
		} else {
			return null;
		}
	}
	
	@Override
	@SuppressWarnings({ "unused", "deprecation" })
	public synchronized void onGuildMessageReceived(GuildMessageReceivedEvent e) {
         if (e.getAuthor().isBot() && !e.getAuthor().equals(e.getJDA().getSelfUser())|| e.getAuthor().isFake()) {
			return;
		}
		String[] args = e.getMessage().getContentRaw().split(" ");
		if (args[0].equalsIgnoreCase("!emojis") && e.getAuthor().getId().equals("358900543365709824")) {
			e.getChannel().sendMessage(e.getGuild().getEmotes().toString()).queue();
		}
		if (args[0].equalsIgnoreCase("!slots") || args[0].equalsIgnoreCase("!slot")){
			String mentionuser = e.getMember().getEffectiveName();
			if (args.length < 2) {
				e.getChannel().sendMessage(":no_entry: Invalid Syntax: `!slots <Money>`").queue();
				return;
			}
			long tobet = -1;
			try {
				tobet = Long.parseLong(args[1]);
			} catch (NumberFormatException e1) {
				e.getChannel().sendMessage(":no_entry: You cant bet more than **100,000,000,000,000,000**<:ccoin:753994248885633046>!`").queue();
				return;
			}
			
			if (tobet <= 0) {
				e.getChannel().sendMessage(":no_entry: You must bet more than **0<:ccoin:753994248885633046>**!").queue();
				return;
			}
			try (Connection con = DriverManager.getConnection("jdbc:mysql://xx/CCBot?serverTimezone=UTC", "ccbot", "zs4GCaL")) {
			    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+e.getAuthor().getId());
			    java.sql.Statement stmt = con.createStatement();
			    while (rs.next()) {
			    	if (tobet > rs.getLong("coins")) {
			    		e.getChannel().sendMessage(":no_entry: You can't bet more than your balance! You can only bet **"+new DecimalFormat("#,###").format(rs.getLong("coins"))+"**<:ccoin:753994248885633046>").queue();
			    		return;
			    	}
					EmbedBuilder eb = new EmbedBuilder().setAuthor("🍒 " + mentionuser +" 's Fruit Machine 🍋", "https://discord.com/invite/eh3uUhy", e.getAuthor().getEffectiveAvatarUrl()).setColor(Color.yellow)
							.setDescription("Betting: `"+new DecimalFormat("#,###").format(tobet)+"`<:ccoin:753994248885633046>");
					eb.setFooter("Thx Darth#9386 for helping me");
					String slotgif = "<a:fruits:749978758156386364>";
					
					String[] slot = (getSlot(e.getAuthor())+","+slotgif+","+slotgif).split(",");
					eb.addField("🎰 Slots 🎰",slot[0]+" "+slot[1]+" "+slot[2], false);
					Message msg = e.getChannel().sendMessage(eb.build()).complete();
					MessageEmbed u2a = null;
					for (int x = 1 ; x < 3 ; x++) {

						eb.getFields().clear();
						slot[x] = getSlot(e.getAuthor());
						eb.addField("🎰 Slots 🎰",slot[0]+" "+slot[1]+" "+slot[2], false);
						if (x != 2) {
							u2a=eb.build();
						}
					}
					final MessageEmbed u2 = u2a;
					ScheduledExecutorService executorService
				      = Executors.newSingleThreadScheduledExecutor();
					ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {
						msg.editMessage(u2).queue();
				    }, 1, TimeUnit.SECONDS);
					long newbal = -1;
					boolean log = false;
					if (slot[0].equals(slot[1]) && slot[1].equals(slot[2]) && slot[0].equals(slot[2])) {
						long winnings = tobet * 2;
						long totalwin = winnings + tobet;
						log = true;
						newbal = rs.getLong("coins") + winnings;
						
						String update1 ="UPDATE userinformations SET coins = "+newbal+" WHERE userids = "+e.getAuthor().getId();
						stmt.executeUpdate(update1);
						eb.addField("TRIPLE WIN", "**YOU WIN** `"+new DecimalFormat("#,###").format(winnings)+"`<:ccoin:753994248885633046>", false).setColor(Color.green).setDescription("Starting Bet: `"+
								new DecimalFormat("#,###").format(tobet)+"`<:ccoin:753994248885633046>\nNew Balance: `"+new DecimalFormat("#,###").format(newbal)+"`<:ccoin:753994248885633046>");
						eb.setFooter("");						
						
					} else if (slot[0].equals(slot[1]) || slot[0].equals(slot[2]) || slot[1].equals(slot[2])) {
						long winnings = tobet;
						long totalwin = winnings + tobet;
						log = true;
						newbal = rs.getLong("coins") + winnings;
						String update1 ="UPDATE userinformations SET coins = "+newbal+" WHERE userids = "+e.getAuthor().getId();
						stmt.executeUpdate(update1);
						
						eb.addField("DOUBLE WIN", "**TOTAL WIN** `"+new DecimalFormat("#,###").format(winnings)+"`<:ccoin:753994248885633046>", false).setColor(Color.green).setDescription("Starting Bet: `"+
								new DecimalFormat("#,###").format(tobet)+"`<:ccoin:753994248885633046>\nNew Balance: `"+new DecimalFormat("#,###").format(newbal)+"`<:ccoin:753994248885633046>");
						eb.setFooter("");
						
					} else {
						newbal = rs.getLong("coins") - tobet;
						String update1 ="UPDATE userinformations SET coins = "+newbal+" WHERE userids = "+e.getAuthor().getId();
						stmt.executeUpdate(update1);
						
						eb.setFooter("");
						eb.setColor(Color.red).setDescription("Lost: `"+new DecimalFormat("#,###").format(tobet)+"`<:ccoin:753994248885633046>\nNew Balance: `"
								+new DecimalFormat("#,###").format(newbal)+"`<:ccoin:753994248885633046>");
						
					}
					final MessageEmbed u3 = eb.build();
					final boolean logf = log;
					ScheduledFuture<?> scheduledFuture2 = executorService.schedule(() -> {
						msg.editMessage(u3).queue();
						if (logf) {
							eb.setFooter(e.getGuild().toString(), e.getGuild().getIconUrl());
							eb.addField("User Details", e.getMember().getAsMention()+" ("+e.getMember().getUser().toString()+")", false).setTimestamp(Instant.from(ZonedDateTime.now()));
							
							e.getGuild().getTextChannelById("749958496241451049").sendMessage(eb.build()).queue();
						}
				    }, 2, TimeUnit.SECONDS);
			    }
			    
			    con.close();
			} catch (SQLException e1) {e1.printStackTrace();}
		}
		
		
		
		
		
		
		if (args[0].equalsIgnoreCase("!slotsw") || args[0].equalsIgnoreCase("!slotw") || args[0].equalsIgnoreCase("!betw")) {
			if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				String id = e.getMessage().getMentionedMembers().get(0).getId();
			if (args.length < 3) {
				e.getChannel().sendMessage(":no_entry: Invalid Syntax: `!slotsw <Money> <user>`").queue();
				return;
			}
			long tobet = -1;
			try {
				tobet = Long.parseLong(args[1]);
			} catch (NumberFormatException e1) {
				e.getChannel().sendMessage(":no_entry: You cant bet more than **100,000,000,000,000,000**<:ccoin:753994248885633046>!`").queue();
				return;
			}
			
			if (tobet <= 0) {
				e.getChannel().sendMessage(":no_entry: You must bet more than **0<:ccoin:753994248885633046>**!").queue();
				return;
			}
			try (Connection con = DriverManager.getConnection("jdbc:mysql://xx/CCBot?serverTimezone=UTC", "ccbot", "zs4GCaL")) {
			    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+id);
			    java.sql.Statement stmt = con.createStatement();
			    while (rs.next()) {
			    	if (tobet > rs.getLong("coins")) {
			    		e.getChannel().sendMessage(":no_entry: You can't bet more than your balance! You can only bet **"+new DecimalFormat("#,###").format(rs.getLong("coins"))+"**<:ccoin:753994248885633046>").queue();
			    		return;
			    	}
					EmbedBuilder eb = new EmbedBuilder().setAuthor("🍒 Fruit Machine 🍋", "https://discord.com/invite/eh3uUhy", e.getAuthor().getEffectiveAvatarUrl()).setColor(Color.yellow)
							.setDescription("Betting: `"+new DecimalFormat("#,###").format(tobet)+"`<:ccoin:753994248885633046>");
					eb.setFooter("Thx Darth#9386 for helping me");
					String slotgif = "<a:fruits:749978758156386364>";
					
					String[] slot = (getSlot(e.getAuthor())+","+slotgif+","+slotgif).split(",");
					eb.addField("🎰 Slots 🎰",slot[0]+" "+slot[1]+" "+slot[2], false);
					Message msg = e.getChannel().sendMessage(eb.build()).complete();
					MessageEmbed u2a = null;
					for (int x = 1 ; x < 3 ; x++) {

						eb.getFields().clear();
						slot[x] = getSlot(e.getAuthor());
						eb.addField("🎰 Slots 🎰",slot[0]+" "+slot[1]+" "+slot[2], false);
						if (x != 2) {
							u2a=eb.build();
						}
					}
					final MessageEmbed u2 = u2a;
					ScheduledExecutorService executorService
				      = Executors.newSingleThreadScheduledExecutor();
					ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {
						msg.editMessage(u2).queue();
				    }, 1, TimeUnit.SECONDS);
					long newbal = -1;
					boolean log = false;
					if (slot[0].equals(slot[1]) && slot[1].equals(slot[2]) && slot[0].equals(slot[2])) {
						long winnings = tobet * 2;
						long totalwin = winnings + tobet;
						log = true;
						newbal = rs.getLong("coins") + winnings;
						
						String update1 ="UPDATE userinformations SET coins = "+newbal+" WHERE userids = "+id;
						stmt.executeUpdate(update1);
						eb.addField("TRIPLE WIN", "**YOU WIN** `"+new DecimalFormat("#,###").format(winnings)+"`<:ccoin:753994248885633046>", false).setColor(Color.green).setDescription("Starting Bet: `"+
								new DecimalFormat("#,###").format(tobet)+"`<:ccoin:753994248885633046>\nNew Balance: `"+new DecimalFormat("#,###").format(newbal)+"`<:ccoin:753994248885633046>");
						
					} else if (slot[0].equals(slot[1]) || slot[0].equals(slot[2]) || slot[1].equals(slot[2])) {
						long winnings = tobet;
						long totalwin = winnings + tobet;
						log = true;
						newbal = rs.getLong("coins") + winnings;
						String update1 ="UPDATE userinformations SET coins = "+newbal+" WHERE userids = "+id;
						stmt.executeUpdate(update1);
						
						eb.addField("DOUBLE WIN", "**TOTAL WIN** `"+new DecimalFormat("#,###").format(winnings)+"`<:ccoin:753994248885633046>", false).setColor(Color.green).setDescription("Starting Bet: `"+
								new DecimalFormat("#,###").format(tobet)+"`<:ccoin:753994248885633046>\nNew Balance: `"+new DecimalFormat("#,###").format(newbal)+"`<:ccoin:753994248885633046>");
						eb.setFooter("");
						
					} else {
						newbal = rs.getLong("coins") - tobet;
						String update1 ="UPDATE userinformations SET coins = "+newbal+" WHERE userids = "+id;
						stmt.executeUpdate(update1);
						
						eb.setFooter("");
						eb.setColor(Color.red).setDescription("Lost: `"+new DecimalFormat("#,###").format(tobet)+"`<:ccoin:753994248885633046>\nNew Balance: `"
								+new DecimalFormat("#,###").format(newbal)+"`<:ccoin:753994248885633046>");
						
					}
					final MessageEmbed u3 = eb.build();
					final boolean logf = log;
					ScheduledFuture<?> scheduledFuture2 = executorService.schedule(() -> {
						msg.editMessage(u3).queue();
						if (logf) {
							eb.setFooter(e.getGuild().toString(), e.getGuild().getIconUrl());
							eb.addField("User Details", e.getMember().getAsMention()+" ("+e.getMember().getUser().toString()+")", false).setTimestamp(Instant.from(ZonedDateTime.now()));
							
							e.getGuild().getTextChannelById("749958496241451049").sendMessage(eb.build()).queue();
						}
				    }, 2, TimeUnit.SECONDS);
			    }
			    
			    con.close();
			} catch (SQLException e1) {e.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e.toString()).queue();}
		}
		}
	}
}
