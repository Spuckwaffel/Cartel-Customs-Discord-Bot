/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Clans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;

public class ClanLeave extends ListenerAdapter 
{
	int joindone = 0;
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		try {
				String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
				String username = "ccbot";
				String password = "zs4GCaL";
				Connection conn = DriverManager.getConnection(url,username,password);
				java.sql.Statement stmt = conn.createStatement();
				if(args[0].equalsIgnoreCase(Main.prefix + "leaveclan")) {
					String id = event.getAuthor().getId();
					int currentclan = 0;
					ResultSet rsinfos = stmt.executeQuery("select * from userinformations where userids =" + id);
					while (rsinfos.next()) {
						currentclan = rsinfos.getInt("clan");
					}
					if(currentclan == 0) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**You cant leave a clan! You are not in a clan!**");
						eb.setColor(0xff0000);
						event.getChannel().sendMessage(eb.build()).queue();
					}
					else {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setAuthor("Clans ~ The war has started!");
						eb.setDescription("**Are you sure you want to leave your clan?** \n"
								+ "**To leave this clan, react with :white_check_mark:, if not, react with :no_entry:**");
						eb.setColor(0xffb700);
						eb.setFooter(event.getMember().getIdLong() + "");
						Message message =event.getChannel().sendMessage(eb.build()).complete();
						message.addReaction("✅").queue();
						message.addReaction("⛔").queue();
						joindone = 0;
					}
				 }
				
				
				conn.close();
				} catch(Exception e){
					System.out.println(e);
			}
	}
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://xx/CCBot?serverTimezone=UTC", "ccbot", "zs4GCaL")) {
			if(event.getChannel().getId().equals("718409439144574977")) {
			if (event.getChannel().retrieveMessageById(event.getMessageId()).complete().getEmbeds().get(0).getAuthor().getName().equals("Clans ~ The war has started!")) {
				if (!event.getMember().equals(event.getGuild().getMemberById(event.getChannel().retrieveMessageById(event.getMessageId()).complete().getEmbeds().get(0).getFooter().getText()))
					&& !event.getMember().getUser().isBot()) {
					event.getTextChannel().removeReactionById(event.getMessageId(), event.getReaction().getReactionEmote().getName(), event.getMember().getUser()).queue();
					con.close();
					return;
					
				}
				if (event.getMember().getUser().isBot()) {
					con.close();
					return;
				}
				if (event.getReactionEmote().getName().equals("✅")) {
					java.sql.Statement stmt = con.createStatement();
					if(joindone == 0) {
						joindone = 1;
						int clanid = 0;
						ResultSet rsclanssearch = stmt.executeQuery("select * from userinformations where userids ='" + event.getMember().getUser().getId() + "'");
						while (rsclanssearch.next()) {
							clanid = rsclanssearch.getInt("clan");
							}
						EmbedBuilder eb = new EmbedBuilder();
						eb.setAuthor("Clans ~ The war has started!");
						eb.setDescription("Leaving Clan <a:loading:743801566451990570>");
						eb.setColor(0xffb700);
						Message message =event.getChannel().sendMessage(eb.build()).complete();
						int membercount = 0;
						String clanowner = "";
						String clanadmin1 = "";
						String clanadmin2 = "";
						String clanadmin3 = "";
						String member1 = "";
						String member2 = "";
						String member3 = "";
						String member4 = "";
						String member5 = "";
						String member6 = "";
						String member7 = "";
						String member8 = "";
						String member9 = "";
						String member10 = "";
						String member11 = "";
						String member12 = "";
						String member13 = "";
						String member14 = "";
						String member15 = "";
						ResultSet rsclansfound = stmt.executeQuery("select * from clans where clanid ='" + clanid + "'");
						while (rsclansfound.next()) {
							clanowner = rsclansfound.getString("clanowner");
							membercount = rsclansfound.getInt("membercount");
							member1 = rsclansfound.getString("member1");
							member2 = rsclansfound.getString("member2");
							member3 = rsclansfound.getString("member3");
							member4 = rsclansfound.getString("member4");
							member5 = rsclansfound.getString("member5");
							member6 = rsclansfound.getString("member6");
							member7 = rsclansfound.getString("member7");
							member8 = rsclansfound.getString("member8");
							member9 = rsclansfound.getString("member9");
							member10 = rsclansfound.getString("member10");
							member11 = rsclansfound.getString("member11");
							member12 = rsclansfound.getString("member12");
							member13 = rsclansfound.getString("member13");
							member14 = rsclansfound.getString("member14");
							member15 = rsclansfound.getString("member15");
							clanowner = rsclansfound.getString("clanowner");
							clanadmin1 = rsclansfound.getString("clanadmin1");
							clanadmin2 = rsclansfound.getString("clanadmin2");
							clanadmin3 = rsclansfound.getString("clanadmin3");
							}
						if(clanowner != null) {
							if(clanowner.contains(event.getMember().getUser().getId())) {
								
								eb.setDescription("**You cant leave the clan because you are the owner of this clan!**");
								eb.setColor(0xff0000);
								message.editMessage(eb.build()).queue();
								return;
							}							
						}
						if(clanadmin1 != null) {
							if(clanadmin1.contains(event.getMember().getUser().getId())) {
								clanadmin1 = clanadmin2;
								clanadmin2 = clanadmin3;
								clanadmin3 = null;
								
								String update1="update clans set clanadmin1="+clanadmin1 +" where clanid=" +clanid;
								String update2="update clans set clanadmin2="+clanadmin2 +" where clanid=" +clanid;
								String update3="update clans set clanadmin3="+clanadmin3 +" where clanid=" +clanid;
								stmt.executeUpdate(update1);
								stmt.executeUpdate(update2);
								stmt.executeUpdate(update3);
							}							
						}
						
						if(clanadmin2 != null) {
							if(clanadmin2.contains(event.getMember().getUser().getId())) {
								clanadmin2 = clanadmin3;
								clanadmin3 = null;
								
								String update2="update clans set clanadmin2="+clanadmin2 +" where clanid=" +clanid;
								String update3="update clans set clanadmin3="+clanadmin3 +" where clanid=" +clanid;
								stmt.executeUpdate(update2);
								stmt.executeUpdate(update3);
							}							
						}
						
						if(clanadmin3 != null) {
							if(clanadmin3.contains(event.getMember().getUser().getId())) {
								clanadmin3 = null;
								
								String update3="update clans set clanadmin3="+clanadmin3 +" where clanid=" +clanid;
								stmt.executeUpdate(update3);
							}							
						}

						if(member1 != null) {
						if(member1.contains(event.getMember().getUser().getId())) {
							member1 = member2;
							member2 = member3;
							member3 = member4;
							member4 = member5;
							member5 = member6;
							member6 = member7;
							member7 = member8;
							member8 = member9;
							member9 = member10;
							member10 = member11;
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update1="update clans set member1="+member1 +" where clanid=" +clanid;
							String update2="update clans set member2="+member2 +" where clanid=" +clanid;
							String update3="update clans set member3="+member3 +" where clanid=" +clanid;
							String update4="update clans set member4="+member4 +" where clanid=" +clanid;
							String update5="update clans set member5="+member5 +" where clanid=" +clanid;
							String update6="update clans set member6="+member6 +" where clanid=" +clanid;
							String update7="update clans set member7="+member7 +" where clanid=" +clanid;
							String update8="update clans set member8="+member8 +" where clanid=" +clanid;
							String update9="update clans set member9="+member9 +" where clanid=" +clanid;
							String update10="update clans set member10="+member10 +" where clanid=" +clanid;
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update1);
							stmt.executeUpdate(update2);
							stmt.executeUpdate(update3);
							stmt.executeUpdate(update4);
							stmt.executeUpdate(update5);
							stmt.executeUpdate(update6);
							stmt.executeUpdate(update7);
							stmt.executeUpdate(update8);
							stmt.executeUpdate(update9);
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
							
						}	
						}
						if(member2 != null) {
						if(member2.contains(event.getMember().getUser().getId())) {
							member2 = member3;
							member3 = member4;
							member4 = member5;
							member5 = member6;
							member6 = member7;
							member7 = member8;
							member8 = member9;
							member9 = member10;
							member10 = member11;
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update2="update clans set member2="+member2 +" where clanid=" +clanid;
							String update3="update clans set member3="+member3 +" where clanid=" +clanid;
							String update4="update clans set member4="+member4 +" where clanid=" +clanid;
							String update5="update clans set member5="+member5 +" where clanid=" +clanid;
							String update6="update clans set member6="+member6 +" where clanid=" +clanid;
							String update7="update clans set member7="+member7 +" where clanid=" +clanid;
							String update8="update clans set member8="+member8 +" where clanid=" +clanid;
							String update9="update clans set member9="+member9 +" where clanid=" +clanid;
							String update10="update clans set member10="+member10 +" where clanid=" +clanid;
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update2);
							stmt.executeUpdate(update3);
							stmt.executeUpdate(update4);
							stmt.executeUpdate(update5);
							stmt.executeUpdate(update6);
							stmt.executeUpdate(update7);
							stmt.executeUpdate(update8);
							stmt.executeUpdate(update9);
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member3 != null) {
						if(member3.contains(event.getMember().getUser().getId())) {
							member3 = member4;
							member4 = member5;
							member5 = member6;
							member6 = member7;
							member7 = member8;
							member8 = member9;
							member9 = member10;
							member10 = member11;
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update3="update clans set member3="+member3 +" where clanid=" +clanid;
							String update4="update clans set member4="+member4 +" where clanid=" +clanid;
							String update5="update clans set member5="+member5 +" where clanid=" +clanid;
							String update6="update clans set member6="+member6 +" where clanid=" +clanid;
							String update7="update clans set member7="+member7 +" where clanid=" +clanid;
							String update8="update clans set member8="+member8 +" where clanid=" +clanid;
							String update9="update clans set member9="+member9 +" where clanid=" +clanid;
							String update10="update clans set member10="+member10 +" where clanid=" +clanid;
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update3);
							stmt.executeUpdate(update4);
							stmt.executeUpdate(update5);
							stmt.executeUpdate(update6);
							stmt.executeUpdate(update7);
							stmt.executeUpdate(update8);
							stmt.executeUpdate(update9);
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member4 != null) {
						if(member4.contains(event.getMember().getUser().getId())) {
							member4 = member5;
							member5 = member6;
							member6 = member7;
							member7 = member8;
							member8 = member9;
							member9 = member10;
							member10 = member11;
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update4="update clans set member4="+member4 +" where clanid=" +clanid;
							String update5="update clans set member5="+member5 +" where clanid=" +clanid;
							String update6="update clans set member6="+member6 +" where clanid=" +clanid;
							String update7="update clans set member7="+member7 +" where clanid=" +clanid;
							String update8="update clans set member8="+member8 +" where clanid=" +clanid;
							String update9="update clans set member9="+member9 +" where clanid=" +clanid;
							String update10="update clans set member10="+member10 +" where clanid=" +clanid;
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update4);
							stmt.executeUpdate(update5);
							stmt.executeUpdate(update6);
							stmt.executeUpdate(update7);
							stmt.executeUpdate(update8);
							stmt.executeUpdate(update9);
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member5 != null) {
						if(member5.contains(event.getMember().getUser().getId())) {
							member5 = member6;
							member6 = member7;
							member7 = member8;
							member8 = member9;
							member9 = member10;
							member10 = member11;
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update5="update clans set member5="+member5 +" where clanid=" +clanid;
							String update6="update clans set member6="+member6 +" where clanid=" +clanid;
							String update7="update clans set member7="+member7 +" where clanid=" +clanid;
							String update8="update clans set member8="+member8 +" where clanid=" +clanid;
							String update9="update clans set member9="+member9 +" where clanid=" +clanid;
							String update10="update clans set member10="+member10 +" where clanid=" +clanid;
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update5);
							stmt.executeUpdate(update6);
							stmt.executeUpdate(update7);
							stmt.executeUpdate(update8);
							stmt.executeUpdate(update9);
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member6 != null) {
						if(member6.contains(event.getMember().getUser().getId())) {
							member6 = member7;
							member7 = member8;
							member8 = member9;
							member9 = member10;
							member10 = member11;
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update6="update clans set member6="+member6 +" where clanid=" +clanid;
							String update7="update clans set member7="+member7 +" where clanid=" +clanid;
							String update8="update clans set member8="+member8 +" where clanid=" +clanid;
							String update9="update clans set member9="+member9 +" where clanid=" +clanid;
							String update10="update clans set member10="+member10 +" where clanid=" +clanid;
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update6);
							stmt.executeUpdate(update7);
							stmt.executeUpdate(update8);
							stmt.executeUpdate(update9);
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member7 != null) {
						if(member7.contains(event.getMember().getUser().getId())) {;
							member7 = member8;
							member8 = member9;
							member9 = member10;
							member10 = member11;
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update7="update clans set member7="+member7 +" where clanid=" +clanid;
							String update8="update clans set member8="+member8 +" where clanid=" +clanid;
							String update9="update clans set member9="+member9 +" where clanid=" +clanid;
							String update10="update clans set member10="+member10 +" where clanid=" +clanid;
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update7);
							stmt.executeUpdate(update8);
							stmt.executeUpdate(update9);
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						
						if(member8 != null) {
						if(member8.contains(event.getMember().getUser().getId())) {
							member8 = member9;
							member9 = member10;
							member10 = member11;
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update8="update clans set member8="+member8 +" where clanid=" +clanid;
							String update9="update clans set member9="+member9 +" where clanid=" +clanid;
							String update10="update clans set member10="+member10 +" where clanid=" +clanid;
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update8);
							stmt.executeUpdate(update9);
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member9 != null) {
						if(member9.contains(event.getMember().getUser().getId())) {
							member9 = member10;
							member10 = member11;
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update9="update clans set member9="+member9 +" where clanid=" +clanid;
							String update10="update clans set member10="+member10 +" where clanid=" +clanid;
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update9);
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member10 != null) {
						if(member10.contains(event.getMember().getUser().getId())) {
							member10 = member11;
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update10="update clans set member10="+member10 +" where clanid=" +clanid;
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member11 != null) {
						if(member11.contains(event.getMember().getUser().getId())) {
							member11 = member12;
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update11="update clans set member11="+member11 +" where clanid=" +clanid;
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update11);
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member12 != null) {
						if(member12.contains(event.getMember().getUser().getId())) {
							member12 = member13;
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update12="update clans set member12="+member12 +" where clanid=" +clanid;
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update12);
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member13 != null) {
						if(member13.contains(event.getMember().getUser().getId())) {
							member13 = member14;
							member14 = member15;
							member15 = null;
							
							String update13="update clans set member13="+member13 +" where clanid=" +clanid;
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update13);
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member14 != null) {
						if(member14.contains(event.getMember().getUser().getId())) {
							member14 = member15;
							member15 = null;
							
							String update14="update clans set member14="+member14 +" where clanid=" +clanid;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update14);
							stmt.executeUpdate(update15);
						}	
						}
						if(member15 != null) {
						if(member15.contains(event.getMember().getUser().getId())) {
							member15 = null;
							String update15="update clans set member15="+member15 +" where clanid=" +clanid;
							stmt.executeUpdate(update15);
						}	
						}
						membercount = membercount - 1;
						String update1="update clans set membercount="+membercount +" where clanid=" +clanid;
						stmt.executeUpdate(update1);
						String update = "update userinformations set clan =0 where userids="+event.getMember().getUser().getId();
						stmt.executeUpdate(update);
						eb.setDescription("You left the clan!");
						eb.setColor(0x3dfc03);
						message.editMessage(eb.build()).queue();
						}
					}
				if (event.getReactionEmote().getName().equals("⛔")) {
					if(joindone == 0) {	
						joindone = 1;
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("**You didnt leave the clan**");
						eb.setColor(0xff0000);
						event.getChannel().sendMessage(eb.build()).queue();	
						}				
					}
				}				
			}

				con.close();
			}
		 catch(IndexOutOfBoundsException e1){return;} catch(Exception e){return;}

	}
				
} 



