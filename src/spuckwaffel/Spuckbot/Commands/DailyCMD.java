package spuckwaffel.Spuckbot.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class DailyCMD extends ListenerAdapter
{
	   private final ScheduledExecutorService scheduler =
			     Executors.newScheduledThreadPool(1);
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if(args[0].equalsIgnoreCase(Main.prefix + "qotd")) {
			   Runnable canceller = () -> {
				   try {
					String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
					String username = "ccbot";
					String password = "zs4GCaL";
					Connection conn = DriverManager.getConnection(url,username,password);
					java.sql.Statement stmt = conn.createStatement();					
					ResultSet rsinfos = stmt.executeQuery("select * from QOTD where num =0");					
					String question = "";
					String totalquestions = "";
					while (rsinfos.next()) {
						question = rsinfos.getString("question");
						totalquestions = rsinfos.getString("askedby");
					}
					ResultSet rsinfos1 = stmt.executeQuery("select * from QOTD where num =" + question);
					while (rsinfos1.next()) {
						Main.question = rsinfos1.getString("question");
						Main.askedby = rsinfos1.getString("askedby");
					}	
					int nextquestion = Integer.parseInt(question) + 1;
					String update1 = "update QOTD set question=" + nextquestion +  " where num =0";
					stmt.executeUpdate(update1);
					event.getChannel().sendMessage("this was question num " + question +"\nnext question is num " + nextquestion + "\ntotal " + totalquestions).queue();
					

					conn.close();
				   }
				   catch (Exception e) {
					   System.out.println(e);
				   }
				   String askedname = event.getJDA().getGuildById("753987748972724265").getMemberById(Main.askedby).getEffectiveName();
				   EmbedBuilder eb = new EmbedBuilder();
				   eb.setTitle(":question::grey_question: Question of the Day :grey_question::question:");
				   eb.setDescription(Main.question);
				   eb.setFooter("Asked by " + askedname).setTimestamp(Instant.from(ZonedDateTime.now()));;
				   eb.setColor(0x3dfc03);
				   event.getChannel().sendMessage(eb.build()).queue();
				   event.getChannel().sendMessage("<@&753988339622740018>").queue();
			   		};
			     @SuppressWarnings("unused")
				ScheduledFuture<?> beeperHandle =
			    	       scheduler.scheduleWithFixedDelay(canceller, 0, 60, TimeUnit.SECONDS);
		}
	}
}
