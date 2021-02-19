/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Commands;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class Commands2 extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		//whitelisted channels
		final Boolean blacklistchannel1 = event.getChannel().getId().equals("703774725460983829"); // #looking-for
		final Boolean blacklistchannel2 = event.getChannel().getId().equals("716746467523690646"); // #closed-looking-for
		final Boolean blacklistchannel3 = event.getChannel().getId().equals("766325192397946910"); // #looking-for-creative
		
		
		//blacklisted words (wildcard)
		final Boolean bsolo = event.getMessage().getContentRaw().contains("solo");
		final Boolean bduo = event.getMessage().getContentRaw().contains("duo");
		final Boolean btrio = event.getMessage().getContentRaw().contains("trio");
		final Boolean bsquad = event.getMessage().getContentRaw().contains("squad");
		final Boolean bsolo1 = event.getMessage().getContentRaw().contains("Solo");
		final Boolean bduo1 = event.getMessage().getContentRaw().contains("Duo");
		final Boolean btrio1 = event.getMessage().getContentRaw().contains("Trio");
		final Boolean bsquad1 = event.getMessage().getContentRaw().contains("Squad");
		final Boolean b1v1 = event.getMessage().getContentRaw().contains("1v1");
		final Boolean b2v2 = event.getMessage().getContentRaw().contains("2v2");
		final Boolean b3v3 = event.getMessage().getContentRaw().contains("3v3");
		final Boolean b4v4 = event.getMessage().getContentRaw().contains("4v4");
		final Boolean barena = event.getMessage().getContentRaw().contains("arena");
		final Boolean bcustoms = event.getMessage().getContentRaw().contains("customs");
		final Boolean rg = event.getMessage().getContentRaw().contains("rg");
		final Boolean zonewars = event.getMessage().getContentRaw().contains("wars");
		final Boolean boxfights = event.getMessage().getContentRaw().contains("boxfights");
		
		String[] bewertungen = {
				"0",
				"1",
				"2",
				"3",
				"4",
				"5",
				"6",
				"7",
				"8",
				"9",
				"10",
				"11"
				
		};
		
		
		if(args[0].equalsIgnoreCase(Main.prefix + "10")) {
			if (args.length >= 2) {
				if (event.getMember().hasPermission(Permission.MESSAGE_MENTION_EVERYONE)) {
					Random rand = new Random();
					int number = rand.nextInt(bewertungen.length);
					event.getChannel().sendMessage(args[1] + " ist " + bewertungen[number] + "/10").queue();
				}
			}
		}
		
		if(bsolo || bduo || btrio || bsquad || b1v1 || b2v2 || b3v3 || b4v4 ||bsolo1 || bduo1 || btrio1 || bsquad1) {
			if (!event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
				if(blacklistchannel1 || blacklistchannel2 || blacklistchannel3) {
					//if someone sends a message in the blacklisted channel nothing happens
				}
				else {
					event.getAuthor().openPrivateChannel().queue(channel -> {channel.sendMessage(event.getAuthor().getAsMention() +" If you look for mates, use <#703774725460983829>!"
							+ "\nIf you look for Creative matches, go to <#766325192397946910>!").queue();});
				}
			}
		}
		
		if(bsolo || bduo || btrio || bsquad ||bsolo1 || bduo1 || btrio1 || bsquad1 || barena || bcustoms) {
			if (!event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
				if(event.getChannel().getId().equals("766325192397946910")) {
					event.getMessage().delete().queue();
					event.getAuthor().openPrivateChannel().queue(channel -> {channel.sendMessage(event.getAuthor().getAsMention() +" If you look for mates, use <#703774725460983829>!").queue();});
					
				}
			}
		}
		if(b1v1 || b2v2 || b3v3 || b4v4 || rg || zonewars || boxfights) {
			if (!event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
				if(blacklistchannel1 || blacklistchannel2) {
					event.getMessage().delete().queue();
					event.getAuthor().openPrivateChannel().queue(channel -> {channel.sendMessage(event.getAuthor().getAsMention() +" If you look for Creative matches, go to <#766325192397946910>!").queue();});
				}
			}
		}
		    
		if(args[0].equalsIgnoreCase(Main.prefix +"ban")) {
			if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
				if (args.length >= 3) {
					String name = event.getMember().getEffectiveName() +"#"+ event.getAuthor().getDiscriminator();
					String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
					try {
						event.getMessage().getMentionedMembers().get(0).getUser().openPrivateChannel().queue((channel) -> {
							try {
							channel.sendMessage("Uh oh! Looks like you've been banned. You can appeal for a unban here: https://discord.gg/DtAWPYt . The reason for this ban was: ``"
									+ reason + "``. If you feel this was done in a error, please contact a administrator. The person who banned you was " + name).queue((message) -> {
										try {
											event.getGuild().ban(event.getMessage().getMentionedMembers().get(0), 0, "Banned with the reason: " + reason + " | Banned by " + name).queue();
											EmbedBuilder eb = new EmbedBuilder();
											eb.setDescription("**Banned the user <a:tickle:737328378109231184>**");
											eb.setColor(0x3dfc03);
											event.getChannel().sendMessage(eb.build()).queue();
										}
										catch(HierarchyException e) {
											EmbedBuilder warn = new EmbedBuilder();
											warn.setColor(0xff0000);
											warn.setTitle("I dont have the permission to ban this user!");
											event.getChannel().sendMessage(warn.build()).queue();
										}
									});								
							}
							catch(ErrorResponseException e) {
								
							}

					
						});						
					}
					catch(IndexOutOfBoundsException e) {
						EmbedBuilder warn = new EmbedBuilder();
						warn.setColor(0xff0000);
						warn.setTitle("Either you didnt @ the person or there was a unknown error!");
						event.getChannel().sendMessage(warn.build()).queue();
					}

					try {
					//event.getGuild().ban(event.getMessage().getMentionedMembers().get(0), 7, reason).queue();				
					}
					catch (HierarchyException e){
						if (e.toString().startsWith("net.dv8tion.jda.api.exceptions.HierarchyException: Can't modify a member")) {
							event.getChannel().sendMessage("ERROR: Can't modify a member with higher or equal highest role than yourself!").queue();							
						}

					}

				}
				
				
			}
		}

		if(args[0].equalsIgnoreCase("/help")) {
			EmbedBuilder help = new EmbedBuilder();
			help.setColor(0x64eb34);
			help.setThumbnail("https://cdn.discordapp.com/avatars/742784158543118476/171ce2a29afccf2a7a89fdb6b5bbe0dd.png?size=256");
			help.setTitle("Help | CC Bot <a:herzneucat:729416851443941478>", "https://twitter.com/Cartel_Customs");
			help.setDescription
					 ("Prefix is ``!`` only the help command is ``/``\n "
					+ "Made by <@358900543365709824> \n "
					+ "A list of commands: \n"
					+ "**!dbcommands** \n -gets the database commands *Needs ban member permission*\n"
					+ "**!funcommands** \n -gets the fun commands *Needs no permission* \n"
					+ "**!gamblecommands ** \n -gets the commands for gambling *Needs no permission* \n"
					+ "**!helpercommands** \n -gets the helper commands **Needs ban member permission** \n"
					+ "**!admincommands** \n -gets the admin commands **Needs admin permission** \n"
					+ "for more information, @ <@358900543365709824>");
			help.setFooter("last startup: " + Main.todaysdate + ", " + Main.todaystime);
			
			event.getChannel().sendMessage(help.build()).queue();
		}
		if(args[0].equalsIgnoreCase(Main.prefix +"dbcommands")) {
			if(event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
			EmbedBuilder help = new EmbedBuilder();
					help.setColor(0x64eb34);
					help.setThumbnail("https://cdn.discordapp.com/avatars/742784158543118476/171ce2a29afccf2a7a89fdb6b5bbe0dd.png?size=256");
					help.setTitle("Help | CC Bot <a:herzneucat:729416851443941478>", "https://twitter.com/Cartel_Customs");
					help.setDescription("Database Commands: \n"
							 	+ "**!ticketq** \n -gets your tickets and quota *Needs ban member permissions*\n"
							 	+ "**!memberinfo @User** \n -gets the info panel about a user *Needs ban member permission* \n"
							 	+ "**!userow <ID> <number>** \n -updates the usernumber of a member *Needs ban member permission* \n"
							 	+ "for more information, @ <@358900543365709824>");
						help.setFooter("last startup: " + Main.todaysdate + ", " + Main.todaystime);
			
					event.getChannel().sendMessage(help.build()).queue();
				}				
			}
		
		
		if(args[0].equalsIgnoreCase(Main.prefix +"funcommands")) {
			if(event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
			EmbedBuilder help = new EmbedBuilder();
					help.setColor(0x64eb34);
					help.setThumbnail("https://cdn.discordapp.com/avatars/742784158543118476/171ce2a29afccf2a7a89fdb6b5bbe0dd.png?size=256");
					help.setTitle("Help | CC Bot <a:herzneucat:729416851443941478>", "https://twitter.com/Cartel_Customs");
					help.setDescription("Fun Commands: \n"
							 	+ "**!10 @user** \n -gives your question a rating  *Needs mention everyone permission*\n"
							 	+ "**!pp @User** \n -gets gets a users pp size *Needs mention everyone permission* \n"
							 	+ "**!8ball <ID>** \n -gets a true answer to a question *Needs no permission* \n"
							 	+ "for more information, @ <@358900543365709824>");
						help.setFooter("last startup: " + Main.todaysdate + ", " + Main.todaystime);
			
					event.getChannel().sendMessage(help.build()).queue();
				}				
			}
		
		if(args[0].equalsIgnoreCase(Main.prefix +"gamblecommands")) {
			EmbedBuilder help = new EmbedBuilder();
					help.setColor(0x64eb34);
					help.setThumbnail("https://cdn.discordapp.com/avatars/742784158543118476/171ce2a29afccf2a7a89fdb6b5bbe0dd.png?size=256");
					help.setTitle("Help | CC Bot <a:herzneucat:729416851443941478>", "https://twitter.com/Cartel_Customs");
					help.setDescription("Gamble Commands: \n"
							 	+ "**!store ** \n -here you can see what you can buy  *Needs no permission*\n"
							 	+ "**!buy <number> ** \n -buys a item from the store  *Needs no permission*\n"
							 	+ "**!bal (@User)** \n -check your balance or from other users *Needs no permission* \n"
							 	+ "**!give @user <amount>** \n -gives a user coins *Needs no permission* \n"
							 	+ "**!slots <amount> ** \n -play slots with your coins *Needs no permission* \n"
							 	+ "**!higherlower <amount>** \n -play higherlower with cour coins *Needs no permission* \n"
							 	+ "**!bet <amount>** \n -bet with your coins *Needs no permission* \n"
							 	+ "**!beg** \n -begs for coins *Needs no permission* \n"
							 	+ "**!daily** \n -begs for coins *Needs no permission* \n"
							 	+ "for more information, @ <@358900543365709824>");
						help.setFooter("last startup: " + Main.todaysdate + ", " + Main.todaystime);
			
					event.getChannel().sendMessage(help.build()).queue();				
			}
		
		if(args[0].equalsIgnoreCase(Main.prefix +"helpercommands")) {
			if(event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
			EmbedBuilder help = new EmbedBuilder();
					help.setColor(0x64eb34);
					help.setThumbnail("https://cdn.discordapp.com/avatars/742784158543118476/171ce2a29afccf2a7a89fdb6b5bbe0dd.png?size=256");
					help.setTitle("Help | CC Bot <a:herzneucat:729416851443941478>", "https://twitter.com/Cartel_Customs");
					help.setDescription("Helper Commands: \n"
							 	+ "**!c <number>** \n -clears messages *Needs manage messages permission*\n"
							 	+ "**!ban @User** \n -bans a user + writes the user a dm *Needs ban member permission* \n"
							 	+ "for more information, @ <@358900543365709824>");
						help.setFooter("last startup: " + Main.todaysdate + ", " + Main.todaystime);
			
					event.getChannel().sendMessage(help.build()).queue();
				}				
			}
		
		if(args[0].equalsIgnoreCase(Main.prefix +"admincommands")) {
			if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			EmbedBuilder help = new EmbedBuilder();
					help.setColor(0x64eb34);
					help.setThumbnail("https://cdn.discordapp.com/avatars/742784158543118476/171ce2a29afccf2a7a89fdb6b5bbe0dd.png?size=256");
					help.setTitle("Help | CC Bot <a:herzneucat:729416851443941478>", "https://twitter.com/Cartel_Customs");
					help.setDescription("Admin Commands: \n"
							 	+ "**!resettickets** \n -resets weekly tickets *Needs admin permission*\n"
							 	+ "**!fillitup** \n -dont use it pls *needs admin permission* \n"
							 	+ "**!fillitup1** \n -dont use it pls *needs admin permission* \n"
							 	+ "**!embed** \n -gives you a embed structure *needs admin permission* \n"
							 	+ "**!sendmessages** \n -gives you 2 sendmessage structures *needs admin permission* \n"
							 	+ "**!args** \n -gives you a args structure *needs admin permission* \n"
							 	+ "**!updatememberf <9 arguments>** \n -fully updates a member *needs admin permission* \n"
							 	+ "**!updatetickets ID <number>** \n -updates weekly tickets *Needs admin permission*\n"
							 	+ "**!updateoveralltickets ID <number>** \n -resets weekly tickets *Needs admin permission*\n"
							 	+ "**!updatemessages** \n -updates total messages a user sent *Needs admin permission*\n"
							 	+ "**!adduserr <10 arguments>** \n -adds a user to the database *Needs admin permission*\n"
							 	+ "**!ping** \n -gets the bots ping *Needs admin permission*\n"
							 	+ "**!say <words>** \n -ok this is not a secret lol *Needs admin permission*\n"
							 	
							 	+ "for more information, @ <@358900543365709824>");
						help.setFooter("last startup: " + Main.todaysdate + ", " + Main.todaystime);
			
					event.getChannel().sendMessage(help.build()).queue();
				}				
			}

		
		if(args[0].equalsIgnoreCase("moin")) {
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("hi").queue();
		}
		
		if(args[0].equalsIgnoreCase(Main.prefix +"lstjointime")) {
			event.getChannel().sendMessage(Main.lastmemberjoindate).queue();
		}

		
		
		if(args[0].equalsIgnoreCase(Main.prefix + "c")) {
			if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
				if (args.length < 2) {
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle("Specify amount to delete");
					error.setDescription("Usage:" + Main.prefix + "clear (number of messages)");
					event.getChannel().sendMessage(error.build()).queue();
				}
				else {
					try {
						List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])+ 1).complete();
						event.getChannel().deleteMessages(messages).queue();					
					}
					catch (IllegalArgumentException e) {
						if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
							// too many messages
							EmbedBuilder error = new EmbedBuilder();
							error.setColor(0xff3923);
							error.setTitle("Too many messages selected!");
							error.setDescription("You can delete between 1-99 messages at one time");
							event.getChannel().sendMessage(error.build()).queue();
						}
						else {
							//too old
							EmbedBuilder error = new EmbedBuilder();
							error.setColor(0xff3923);
							error.setTitle("Selected messages are older than 2 weeks!");
							error.setDescription("You cant delete messages older than 2 weeks");
							event.getChannel().sendMessage(error.build()).queue();
						}
					}

				}				
			}
			else {
				//event.getChannel().sendMessage("no").queue();
			}

		}
	}
	
	
}
