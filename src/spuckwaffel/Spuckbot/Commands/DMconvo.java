/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Commands;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class DMconvo extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if(args[0].equalsIgnoreCase(Main.prefix + "dm")) { 
			 if (args.length >= 3) {
				 Main.dmmember = args[1].toString();
				 if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					 if(event.getAuthor().getId().equals("358900543365709824")) {
						 String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
						 try {
							 event.getMessage().getMentionedMembers().get(0).getUser().openPrivateChannel().queue((channel) -> {
							 channel.sendMessage(reason).queue();
							 });
						 Main.dmmessage = reason;
							EmbedBuilder eb = new EmbedBuilder();
							eb.setTitle("Private message sent + received:");
							eb.setDescription("**Sent to: " + Main.dmmember + " \n Content: **" + Main.dmmessage);
							eb.setColor(0x3dfc03);
							event.getJDA().getGuildById("703751815077822564").getTextChannelById("744254088660779068").sendMessage(eb.build()).queue();	
						 }
						 catch(IndexOutOfBoundsException e) {
							 event.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e.toString()).queue();
						 }
					 }
				 }
			 }
		 }
	}
}
