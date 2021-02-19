package spuckwaffel.Spuckbot.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class ShowBan extends ListenerAdapter
{
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if(args[0].equalsIgnoreCase(Main.prefix + "showban")) {
			 if(args.length == 2) {
				 
					event.getGuild().retrieveBanById(args[1]).queue(ban -> event.getChannel().sendMessage("Reason: " +ban.getReason()).queue(), error -> event.getChannel().sendMessage("This user never got banned!").queue());					 
				 

				 
			 }
		 }
	}

}
