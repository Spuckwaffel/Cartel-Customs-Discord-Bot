/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Events;

import java.time.Instant;
import java.time.ZonedDateTime;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Dmmessagereceived extends ListenerAdapter{

	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if (!event.getAuthor().isBot()) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Private message received:");
			eb.setDescription("**From:** <@"+event.getAuthor().getId() + "> \n **Content:** "  + event.getMessage().getContentRaw());
			eb.setColor(0x3dfc03);
			eb.addField("User Details", event.getAuthor().getAsMention()+" ("+event.getAuthor().getName()+ ") " + event.getAuthor().getId(), false);
			eb.setTimestamp(Instant.from(ZonedDateTime.now()));
			event.getJDA().getGuildById("703751815077822564").getTextChannelById("744254088660779068").sendMessage(eb.build()).queue();
		}


	}
}
