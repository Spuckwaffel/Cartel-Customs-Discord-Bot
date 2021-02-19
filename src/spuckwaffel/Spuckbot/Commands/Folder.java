/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class Folder extends ListenerAdapter {
	
	@SuppressWarnings("deprecation")
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
         if (e.getAuthor().isBot() && !e.getAuthor().equals(e.getJDA().getSelfUser())|| e.getAuthor().isFake()) {
			return;
		}
		String[] args = e.getMessage().getContentRaw().split(" ");
		if (args[0].equalsIgnoreCase("!listfolder")) {
			if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				if(args.length == 2) {
					String collects = "";
					String[] pathnames;
					int itemnum = 1;
					File f = new File(args[1]);
					pathnames = f.list();
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Found these files:");
					String jars = "";
			        for (String pathname : pathnames) {
			            // Print the names of files and directories
			            jars = jars + " "+ itemnum + ". " +pathname +"\n";
			            itemnum ++;
			        }
			        eb.setDescription("```nim\n"+ jars + "```");
			        eb.setColor(0x3dfc03);
			        e.getChannel().sendMessage(eb.build()).queue();
			        
					Path start = Paths.get(args[1]);
					try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
					    List<String> collect = stream
					        .map(String::valueOf)
					        .sorted()
					        .collect(Collectors.toList());
					    
					    //collect.forEach(System.out::println);
					    collects = collect.toString();
					    collects = collects.replace(", ", "\n");
					    
						
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		if (args[0].equalsIgnoreCase("!copyf")) {
			if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				if(args.length == 2) {
					File file = new File(args[1]);
					e.getMessage().delete().queue();
					e.getAuthor().openPrivateChannel().queue(channel -> {channel.sendFile(file, AttachmentOption.SPOILER).queue();});
				}
			}
		}
		
	}

}
