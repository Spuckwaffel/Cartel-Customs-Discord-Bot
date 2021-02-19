/*
 * From: Spuckwaffel#5000
 */

//removed due to ToS
/*package spuckwaffel.Spuckbot.Events;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class TextChange extends ListenerAdapter 
{
	   private final ScheduledExecutorService scheduler =
			     Executors.newScheduledThreadPool(1);
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if(args[0].equalsIgnoreCase(Main.prefix + "test2")) {
			if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				Message message = event.getChannel().sendMessage("yeet").complete();
				String[] bewertungen = {
						"ƪ(˘⌣˘)┐","ƪ(˘⌣˘)ʃ","┌(˘⌣˘)ʃ"
				};
				Runnable canceller = () -> {
					String st1 = "ƪ(˘⌣˘)┐";
					String st2 = "┌(˘⌣˘)ʃ";
					try {
						message.editMessage(":one::zero::zero::zero::zero::zero::zero::zero::zero::one:").queue();
						Thread.sleep(1500);
						message.editMessage(":zero::one::zero::zero::zero::zero::zero::zero::one::zero:").queue();
						Thread.sleep(1500);
						message.editMessage(":zero::zero::one::zero::zero::zero::zero::one::zero::zero:").queue();
						Thread.sleep(1500);
						message.editMessage(":zero::zero::zero::one::zero::zero::one::zero::zero::zero:").queue();
						Thread.sleep(1500);
						message.editMessage(":zero::zero::zero::zero::one::one::zero::zero::zero::zero:").queue();
						Thread.sleep(1500);
						message.editMessage(":zero::zero::zero::one::zero::zero::one::zero::zero::zero:").queue();
						Thread.sleep(1500);
						message.editMessage(":zero::zero::one::zero::zero::zero::zero::one::zero::zero:").queue();
						Thread.sleep(1500);
						message.editMessage(":zero::one::zero::zero::zero::zero::zero::zero::one::zero:").queue();
						Thread.sleep(1500);
						message.editMessage(":one::zero::zero::zero::zero::zero::zero::zero::zero::one:").queue();
						Thread.sleep(1500);
						message.editMessage(":zero::zero::zero::zero::zero::zero::zero::zero::zero::zero:").queue();
						
					}catch (InterruptedException e) {
						System.out.println(e);
					}
					
				};
			     ScheduledFuture<?> beeperHandle =
			    	       scheduler.scheduleWithFixedDelay(canceller, 0, 1500, TimeUnit.MILLISECONDS);
			}
		}
	}
	
}
*/