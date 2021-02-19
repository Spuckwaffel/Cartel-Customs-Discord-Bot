/*
 * From: Spuckwaffel#5000
 */

//testing only, but got shut down due to ToS

/*package spuckwaffel.Spuckbot.Events;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class ChangeName extends ListenerAdapter 
{
	   private final ScheduledExecutorService scheduler =
			     Executors.newScheduledThreadPool(1);
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if(args[0].equalsIgnoreCase(Main.prefix + "testt")) {
			if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				//String xaii =event.getGuild().getMemberById("475306962297552916").getNickname();
				System.out.println("lol");
				 Runnable canceller = () -> {
						String[] bewertungen = {
								"🍏",
								"🍎",
								"🍐",
								"🍎",
								"🍊",
								"🍋",
								"🍌",
								"🍉",
								"🍇",
								"🍓",
								"🍈",
								"🍒",
								"🍑",
								"🍍",
								"🥥",
								"🥝",
								"🍅",
								"🥑",
								"🥦",
								"🥒",
								"🌶️",
								"🌽",
								"🥕",
								"🥔",
								"🍠",
								"🥐",
								"🍞",
								"🥖",
								"🥨",
								"🧀",
								"🥚",
								"🍳",
								"🥞",
								"🥓",
								"🥩",
								"🍗",
								"🍖",
								"🌭",
								"🍔",
								"🍟",
								"🍕",
								"🥪",
								"🥙",
								"🌮",
								"🌯",
								"🥗",
								"🥘",
								"🥫",
								"🍝",
								"🍜",
								"🍲",
								"🍛",
								"🍣",
								"🍱",
								"🥟",
								"🍤",
								"🍙",
								"🍚",
								"🍘",
								"🍥",
								"🥠",
								"🍢",
								"🍡",
								"🍧",
								"🍨",
								"🍦",
								"🥧",
								"🍰",
								"🎂",
								"🍮",
								"🍭",
								"🍬",
								"🍫",
								"🍿",
								"🍩",
								"🍪",
								"🌰",
								"🥜",
								"🍯",
								""
								
						};
						Random rand = new Random();
						int number = rand.nextInt(bewertungen.length);
						System.out.println("s");
						event.getGuild().retrieveMemberById(475306962297552916l).queue((member) -> 
						{
							member.modifyNickname("xaii " + bewertungen[number]).queue();
						});
						//event.getGuild().getMemberById(475306962297552916l).modifyNickname("test").queue(sucess -> {System.out.println("a");}, error -> {System.out.println("b");});
							System.out.println("a");
							//event.getGuild().getMemberById("475306962297552916").modifyNickname(xaii +  " " + bewertungen[number1]).queue();
					   
					   
				   		};
				     ScheduledFuture<?> beeperHandle =
				    	       scheduler.scheduleWithFixedDelay(canceller, 0, 1500, TimeUnit.MILLISECONDS);
			}
			  
		}
	}
}
*/
