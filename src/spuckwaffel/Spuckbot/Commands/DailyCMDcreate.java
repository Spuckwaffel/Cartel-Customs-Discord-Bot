/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DailyCMDcreate extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if(args[0].equalsIgnoreCase("aaa")) {
			if(args.length > 2) {
				String name = event.getMember().getId();
				String question = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
				
				try {
					String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
					String username = "ccbot";
					String password = "zs4GCaL";
					Connection conn = DriverManager.getConnection(url,username,password);
					java.sql.Statement stmt = conn.createStatement();
					ResultSet rsinfos = stmt.executeQuery("select * from QOTD where num =0");
					String totalquestions = "";
					while (rsinfos.next()) {
						totalquestions = rsinfos.getString("askedby");
					}
					int totalques = Integer.parseInt(totalquestions) + 1;
					String update2 = "update QOTD set askedby=" + totalques +  " where num =0";
					stmt.executeUpdate(update2);
					String update1 = "insert into QOTD (num, question, askedby) values ('" + totalques + "', '" + question + "', '"+ name +"')";
					stmt.executeUpdate(update1);
					
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Sucessfully added the question!");
					eb.setDescription("You question was: " + question);
					eb.setColor(0x3dfc03);
					event.getChannel().sendMessage(eb.build()).queue();
				}
				catch (Exception e) {
					
				}
			}
		}
	}

}
