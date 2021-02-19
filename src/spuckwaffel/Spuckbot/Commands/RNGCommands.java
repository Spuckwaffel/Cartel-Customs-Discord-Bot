package spuckwaffel.Spuckbot.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class RNGCommands extends ListenerAdapter
{
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		try {
			String mentionuser = event.getMember().getEffectiveName();			
		}
		catch (Exception e) {
			
		}

		
		Date currentTime = new Date();
		
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
		Main.nowtime = "``[" + timeformat.format(currentTime) + "]`` ";
		
		try {
			String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
			String username = "ccbot";
			String password = "zs4GCaL";
			Connection conn = DriverManager.getConnection(url,username,password);
			java.sql.Statement stmt = conn.createStatement();
			
			if(args[0].equalsIgnoreCase(Main.prefix + "setcoins")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					if(args.length == 3) {
						String issuerid = event.getAuthor().getId();
						String update = "update userinformations set coins='"+ args[2] + "' where userids=" + args[1];
						
						try {
							stmt.executeUpdate(update);
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("<a:tickle:737328378109231184> Member coins got updated for "+ args[1] + " (<@" +args[1] + ">) -> " + args[2]);
							eb.setColor(0x3dfc03);
							event.getChannel().sendMessage(eb.build()).queue();
							event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"coins got updated for "+ args[1] + " (<@" +args[1] + ">) issued by " + issuerid+ " -> " + args[2]).queue();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else {
						event.getChannel().sendMessage(":warning: Use !setcoins <ID> <coins>").queue();
					}
				}
			}
			if(args[0].equalsIgnoreCase(Main.prefix + "setdiamonds")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					if(args.length == 3) {
						String issuerid = event.getAuthor().getId();
						String update = "update userinformations set diamonds='"+ args[2] + "' where userids=" + args[1];
						
						try {
							stmt.executeUpdate(update);
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("<a:tickle:737328378109231184> Member diamonds got updated for "+ args[1] + " (<@" +args[1] + ">) -> " + args[2]);
							eb.setColor(0x3dfc03);
							event.getChannel().sendMessage(eb.build()).queue();
							event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"diamonds got updated for "+ args[1] + " (<@" +args[1] + ">) issued by " + issuerid+ " -> " + args[2]).queue();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else {
						event.getChannel().sendMessage(":warning: Use !setdiamonds <ID> <coins>").queue();
					}
				}
			}
			
			
			if(args[0].equalsIgnoreCase(Main.prefix + "bal")) {
				if(args.length == 1) {
						String mentionuser = event.getMember().getEffectiveName();
						long coins = 0;
						int diamonds = 0;
					    ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+event.getAuthor().getId());
					    while (rs.next()) {
					    coins =rs.getLong("coins");
					    diamonds = rs.getInt("diamonds");
					    EmbedBuilder eb = new EmbedBuilder();
					    eb.setDescription("**"+ mentionuser +"'s Balance:\n <:ccoin:753994248885633046>" + new DecimalFormat("#,###").format(coins) + "  \n <:diamond:753994248583512095>" + new DecimalFormat("#,###").format(diamonds) +" **");
					    eb.setColor(0x3dfc03);
					    event.getChannel().sendMessage(eb.build()).queue();
				 }					
				}

			 }
			if(args[0].equalsIgnoreCase(Main.prefix + "give")) {
				//event.getChannel().sendMessage("working").queue();
				if(args.length == 3) {
					long givecoins = -1;
					//event.getChannel().sendMessage("args are 3").queue();
					try {
						givecoins = Long.parseLong(args[2]);
					} catch (NumberFormatException e1) {
						event.getChannel().sendMessage(":no_entry: Use !slots <coins>`").queue();
						return;
					}
					//event.getChannel().sendMessage(givecoins + "").queue();
					String id = event.getMessage().getMentionedMembers().get(0).getId();
					if(givecoins < 0) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("You cant give someone negative <:ccoin:753994248885633046>!");
						eb.setColor(0xfc0303);
						event.getChannel().sendMessage(eb.build()).queue();
					}
					if(givecoins == 0) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("You cant give someone 0 <:ccoin:753994248885633046>!");
						eb.setColor(0xfc0303);
						event.getChannel().sendMessage(eb.build()).queue();
					}
					if(givecoins > 0){
						if(event.getAuthor().getId().contains(id)) {
							EmbedBuilder eb1 = new EmbedBuilder();
							eb1.setDescription("**You cant give yourself money!**");
							eb1.setColor(0xfc0303);
							event.getChannel().sendMessage(eb1.build()).queue();
						}
						else {

							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**Sending money to <@" + id + ">** <a:loading:743801566451990570>");
							eb.setColor(0x3dfc03);
							Message msg =event.getChannel().sendMessage(eb.build()).complete();
							//event.getChannel().sendMessage("oi whattt").queue();
							long coins = 0;
							long hascoins = 0;
							long sendercoins = 0;
							long gettercoins = 0;
							long coins2 = 0;
							//event.getChannel().sendMessage(id + " is the recipies id").queue();
							//event.getChannel().sendMessage(givecoins + "coins arw you giving").queue();
						    ResultSet rs = conn.createStatement().executeQuery("SELECT coins FROM userinformations WHERE userids = "+event.getAuthor().getId());
						    while (rs.next()) {
						    	coins =rs.getLong("coins");

						    }
						    	hascoins = coins-givecoins;
						    	sendercoins = coins-givecoins;					    
						    	//event.getChannel().sendMessage(coins + " does the sender have").queue();
						    	//event.getChannel().sendMessage(hascoins + "will you have after the sending").queue();
						    	//event.getChannel().sendMessage(sendercoins + "will you have after sending").queue();
						    	ResultSet rs1 = conn.createStatement().executeQuery("SELECT coins FROM userinformations WHERE userids = "+id);
						    	while (rs1.next()) {
						    		coins2 = rs1.getLong("coins");
						    		
						    		
						    	}
						    	gettercoins = coins2 + givecoins;
						    	//event.getChannel().sendMessage(coins2 + "does the getter have").queue();
						    	//event.getChannel().sendMessage(gettercoins + "will the user get").queue();
						    		if(hascoins < 0) {
						    			eb.setDescription("You only have "+ new DecimalFormat("#,###").format(coins) +" <:ccoin:753994248885633046>!");
						    			eb.setColor(0xfc0303);
						    			msg.editMessage(eb.build()).queue();
						    		}
						    		else {
						    			String update1 = "update userinformations set coins='"+ sendercoins + "' where userids=" + event.getAuthor().getId();
						    			String update2 = "update userinformations set coins='"+ gettercoins + "' where userids=" + id;
						    			
						    			try {
						    				stmt.executeUpdate(update2);
						    				stmt.executeUpdate(update1);
						    				eb.setDescription("**Sucessfully gave <@" + id + "> " + new DecimalFormat("#,###").format(givecoins) + " <:ccoin:753994248885633046>!** \n"
						    						+ "You have now " + new DecimalFormat("#,###").format(sendercoins) + " <:ccoin:753994248885633046>");
						    				eb.setColor(0x3dfc03);
						    				msg.editMessage(eb.build()).queue();
						    				EmbedBuilder eb1 = new EmbedBuilder();
						    				eb1.setDescription("<@" + event.getAuthor().getId() +" >**Sucessfully gave <@" + id + "> " + new DecimalFormat("#,###").format(givecoins) + "<:ccoin:749969019842461787!** \n"
						    						+ "You have now " + new DecimalFormat("#,###").format(sendercoins) + "<:ccoin:753994248885633046>");
						    				eb1.setColor(0x3dfc03);
						    				event.getGuild().getTextChannelById("749958496241451049").sendMessage(eb1.build()).queue();
						    			} catch (Exception e) {
						    				e.printStackTrace();
						    			}
						    		}					    	
						    	
						}
					    
					}
				}
				else {
					event.getChannel().sendMessage("please use !give @user <coins>").queue();					
				}

			}
			if(args[0].equalsIgnoreCase(Main.prefix + "bal")) { 
				if(args.length == 2) {
					long coins = 0;
					int diamonds = 0;
					String id = event.getMessage().getMentionedMembers().get(0).getId();
					ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
					while (rs.next()) {
						coins =rs.getLong("coins");
						diamonds = rs.getInt("diamonds");
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**<@" +id +">'s Balance:\n <:ccoin:753994248885633046>" + new DecimalFormat("#,###").format(coins) + " \n <:diamond:753994248583512095>"+ diamonds + "**");
						eb.setColor(0x3dfc03);
						event.getChannel().sendMessage(eb.build()).queue();			
					}

				}
			}
			
			if(args[0].equalsIgnoreCase(Main.prefix + "store")) {
				EmbedBuilder store = new EmbedBuilder();
				String id = event.getAuthor().getId();
				long coins = 0;
				int diamonds = 0;
				ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
				while (rs.next()) {
					coins =rs.getLong("coins");
					diamonds= rs.getInt("diamonds");
				}
				store.setThumbnail("https://i.ibb.co/pxX6sKp/dubles.png");
				store.setTitle("<:ccoin:753994248885633046><:diamond:753994248583512095><:ccoin:753994248885633046>Store<:ccoin:753994248885633046><:diamond:753994248583512095><:ccoin:753994248885633046>");
				store.setDescription("**Your balance:\n<:ccoin:753994248885633046>" + new DecimalFormat("#,###").format(coins) + "\n<:diamond:753994248583512095>" + new DecimalFormat("#,###").format(diamonds) + "**");
				store.addField("","**Convert <:diamond:753994248583512095> to <:ccoin:753994248885633046>!**\n<:blank:743124455592820876>",false);
				store.addField(":one: 50 000 <:ccoin:753994248885633046>", "**for 1**<:diamond:753994248583512095>", true);
				store.addField(":two: 150 000 <:ccoin:753994248885633046>", "**for 3**<:diamond:753994248583512095>", true);
				store.addField(":three: 500 000 <:ccoin:753994248885633046>", "**for 10**<:diamond:753994248583512095>", true);
				store.addField(":four: 1 500 000 <:ccoin:753994248885633046>", "**for 30**<:diamond:753994248583512095>", true);
				store.addField(":five: 2 500 000 <:ccoin:753994248885633046>", "**for 50**<:diamond:753994248583512095>", true);
				store.addField(":six: 5 000 000 <:ccoin:753994248885633046>", "**for 100**<:diamond:753994248583512095>", true);
				store.addField("","**Convert <:ccoin:753994248885633046> to <:diamond:753994248583512095>!**\n<:blank:743124455592820876>",false);
				store.addField(":seven: 1<:diamond:753994248583512095>", "**for 55 000** <:ccoin:753994248885633046>", true);
				store.addField(":eight: 3<:diamond:753994248583512095>", "**for 165 000** <:ccoin:753994248885633046>", true);
				store.addField(":nine: 10<:diamond:753994248583512095>", "**for 550 000** <:ccoin:753994248885633046>", true);
				store.addField(":keycap_ten: 30<:diamond:753994248583512095>", "**for 1 650 000** <:ccoin:753994248885633046>", true);
				store.addField("<:11:749343286614818916> 50<:diamond:753994248583512095>", "**for 2 750 000** <:ccoin:753994248885633046>", true);
				store.addField("<:12:749343308798754826> 100<:diamond:753994248583512095>", "**for 5 500 000** <:ccoin:753994248885633046>", true);
				store.addField("","**<a:rainbow:723187469037600849><a:rainbow:723187469037600849> EPIC EVENT<a:rainbow:723187469037600849><a:rainbow:723187469037600849>!**\n<:blank:743124455592820876>",false);
				store.addField("<:13:749343332706156657> 1 10€ PSC", "**for 500**<:diamond:753994248583512095> (thats 27 500 000 <:ccoin:753994248885633046> :flushed:)", true);
				store.addField("To buy a item, do !buy <number>","For example: !buy 8 -> I buy 3<:diamond:753994248583512095> for 165 000<:ccoin:753994248885633046>",false);
				store.setColor(0x3dfc03);
				event.getChannel().sendMessage(store.build()).queue();
			 }
			
			if(args[0].equalsIgnoreCase(Main.prefix + "buy")) {
				if(args.length == 2) {
					String id = event.getAuthor().getId();
					int completed = 0;
					long coins = 0;
					int diamonds = 0;
					int buynumber = Integer.parseInt(args[1]);
					if(buynumber == 1) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(diamonds >=1) {
							diamonds = diamonds - 1;
							coins = coins + 50000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 50000<:ccoin:753994248885633046>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:diamond:753994248583512095>! You only have " + diamonds + "<:diamond:753994248583512095>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 2) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(diamonds >=3) {
							diamonds = diamonds - 3;
							coins = coins + 150000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 150000<:ccoin:753994248885633046>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:diamond:753994248583512095>! You only have " + diamonds + "<:diamond:753994248583512095>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 3) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(diamonds >=10) {
							diamonds = diamonds - 10;
							coins = coins + 500000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 500000<:ccoin:753994248885633046>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:diamond:753994248583512095>! You only have " + diamonds + "<:diamond:753994248583512095>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 4) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(diamonds >=30) {
							diamonds = diamonds - 30;
							coins = coins + 1500000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 1500000<:ccoin:753994248885633046>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:diamond:753994248583512095>! You only have " + diamonds + "<:diamond:753994248583512095>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 5) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(diamonds >=50) {
							diamonds = diamonds - 50;
							coins = coins + 2500000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 2500000<:ccoin:753994248885633046>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:diamond:753994248583512095>! You only have " + diamonds + "<:diamond:753994248583512095>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 6) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(diamonds >=100) {
							diamonds = diamonds - 100;
							coins = coins + 5000000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 5000000<:ccoin:753994248885633046>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:diamond:753994248583512095>! You only have " + diamonds + "<:diamond:753994248583512095>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 7) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(coins >=55000) {
							diamonds = diamonds + 1;
							coins = coins - 55000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 1<:diamond:753994248583512095>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:ccoin:753994248885633046>! You only have " + coins + "<:ccoin:753994248885633046>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
						
					}
					if(buynumber == 8) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(coins >=165000) {
							diamonds = diamonds + 3;
							coins = coins - 165000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 3<:diamond:753994248583512095>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:ccoin:753994248885633046>! You only have " + coins + "<:ccoin:753994248885633046>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 9) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(coins >=550000) {
							diamonds = diamonds + 10;
							coins = coins - 550000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 10<:diamond:753994248583512095>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:ccoin:753994248885633046>! You only have " + coins + "<:ccoin:753994248885633046>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 10) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(coins >=1650000) {
							diamonds = diamonds + 30;
							coins = coins - 1650000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 30<:diamond:753994248583512095>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:ccoin:753994248885633046>! You only have " + coins + "<:ccoin:753994248885633046>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 11) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(coins >=2750000) {
							diamonds = diamonds + 50;
							coins = coins - 2750000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 50<:diamond:753994248583512095>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:ccoin:753994248885633046>! You only have " + coins + "<:ccoin:753994248885633046>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 12) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(coins >=5500000) {
							diamonds = diamonds + 100;
							coins = coins - 5500000;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							String update1 = "update userinformations set coins='"+ coins +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								stmt.executeUpdate(update1);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 100<:diamond:753994248583512095>!**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:ccoin:753994248885633046>! You only have " + coins + "<:ccoin:753994248885633046>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(buynumber == 13) {
						completed = 1;
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							coins =rs.getLong("coins");
							diamonds= rs.getInt("diamonds");
						}
						if(diamonds >=500) {
							diamonds = diamonds - 500;
							String update = "update userinformations set diamonds='"+ diamonds +"' where userids=" + id;
							
							try {
								stmt.executeUpdate(update);
								EmbedBuilder eb = new EmbedBuilder();
								eb.setDescription("**Sucessfully bought 1 10€ PAYSAFECARD!<a:rainbow:723187469037600849>**");
								eb.setColor(0x3dfc03);
								event.getChannel().sendMessage(eb.build()).queue();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						else {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**You dont have enough <:diamond:753994248583512095>! You only have " + diamonds + "<:diamond:753994248583512095>!**");
							eb.setColor(0xff0000);
							event.getChannel().sendMessage(eb.build()).queue();
						}
					}
					if(completed ==0) {
						event.getChannel().sendMessage("Wrong number! Use only numbers 1-13!").queue();
					}
				}
				else {
					event.getChannel().sendMessage("Wrong usage! Use !buy <number>").queue();
				}
				 
			 }
			
			conn.close();
		} catch (NullPointerException e1) {} catch (Exception e) {
			event.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e.toString()).queue();
		} 
	}


}
