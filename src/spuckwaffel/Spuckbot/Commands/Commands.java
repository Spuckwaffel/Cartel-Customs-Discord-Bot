/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import spuckwaffel.Spuckbot.Main;


@SuppressWarnings("unused")
public class Commands extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		Date currentTime = new Date();
		
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
		Main.nowtime = "``[" + timeformat.format(currentTime) + "]`` ";
		
		try {
			String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
			String username = "ccbot";
			String password = "zs4GCaL";
			Connection conn = DriverManager.getConnection(url,username,password);
			java.sql.Statement stmt = conn.createStatement();
			
			String issuerid1 = event.getAuthor().getId();
			int messages1 = 0;
			ResultSet rs1 = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+issuerid1);
			
			while (rs1.next()) {
				messages1 = rs1.getInt("messages");
				messages1 = messages1 + 1;
			}
			String msgupdate = "update userinformations set messages='"+ messages1 + "' where userids=" + issuerid1;
			
			try {
				stmt.executeUpdate(msgupdate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String[] pp = new String[]{
					"8=D",
					"8==D",
					"8===D",
					"8====D",
					"8=====D",
					"8======D",
					"8=======D",
					"8========D",
					"8=========D",
					"8==========D",
					"8===========D",
					"**no pp found** :flushed:"
					
			};
			
			if(event.getChannel().getId().equals("718409439144574977")) {
				if (!event.getAuthor().isBot()) {
					int ran = new Random().nextInt(20);
					if(ran == 1) {
						long coins = 0;
						int rand = new Random().nextInt((20) + 50);
						event.getChannel().sendMessage("<@" + event.getMember().getId() + ">, you got **" + rand +"<:ccoin:753994248885633046> **from chatting!").queue();
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ event.getAuthor().getId());
						while (rs.next()) {
							coins =rs.getLong("coins");
						}
						coins = coins + rand;
						String coinsupdate = "update userinformations set coins='"+ coins + "' where userids=" + event.getAuthor().getId();
						
						try {
							stmt.executeUpdate(coinsupdate);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
			
			if(args[0].equalsIgnoreCase(Main.prefix + "pp")) {
				if (args.length >= 2) {
					if (event.getMember().hasPermission(Permission.MESSAGE_MENTION_EVERYONE)) {
						Random rand = new Random();
						int number = rand.nextInt(pp.length);
						EmbedBuilder ppsize = new EmbedBuilder();
						ppsize.setColor(0x03d7fc);
						ppsize.setTitle("pp size machine");
						ppsize.setDescription(args[1] + "'s pp: " + pp[number]);
						event.getChannel().sendMessage(ppsize.build()).queue();
						ppsize.clear();
					}
				}
			}
			
	        if (args[0].equalsIgnoreCase(Main.prefix+ "8ball")) {
	            if (args.length > 1) {
	                EmbedBuilder eb = new EmbedBuilder();
	                eb.setAuthor("Magic 8 Ball", null, "https://www.incandescentwaxmelts.com/wp-content/uploads/2017/07/8-BALL-SCENTSY-WARMER-2.png").setColor(660066);
	                eb.setDescription("Beratung der Orakel .....");
	                Message msg = event.getChannel().sendMessage(eb.build()).complete();
					ScheduledExecutorService executorService
				      = Executors.newSingleThreadScheduledExecutor();
					ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {
		                Random rand = new Random();
		                int n = rand.nextInt(20);
		                n += 1;
		                if (n == 1) {
		                    eb.setDescription("Es ist sicher.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 2) {
		                    eb.setDescription("Juckt?");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 3) {
		                    eb.setDescription("Ohne einen zweifel.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 4) {
		                    eb.setDescription("Ja - bestimmt.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 5) {
		                    eb.setDescription("Du kannst dich darauf verlassen.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 6) {
		                    eb.setDescription("So, wie ich es sehe, ja.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 7) {
		                    eb.setDescription("Höchstwahrscheinlich.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 8) {
		                    eb.setDescription("ka man");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 9) {
		                    eb.setDescription("Ja");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 10) {
		                    eb.setDescription("Sieht wie ein Ja aus");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 11) {
		                    eb.setDescription("Frag nochmal, ich konnte mir deine frage nicht merken.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 12) {
		                    eb.setDescription("Frag später.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 13) {
		                    eb.setDescription("Ich glaube, ich sollte das net sagen.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 14) {
		                    eb.setDescription("Kann ich gerade net sagen.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 15) {
		                    eb.setDescription("Konzentrier dich und fragen nochmal.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 16) {
		                    eb.setDescription("Glaub net daran.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 17) {
		                    eb.setDescription("Meine antwort ist nein.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 18) {
		                    eb.setDescription("Meine Quellen sagen Nein.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else if (n == 19) {
		                    eb.setDescription("Sieht net gut aus.");
		                    msg.editMessage(eb.build()).queue();
		                }
		                else {
		                    eb.setDescription("Sehr zweifelswert.");
		                    msg.editMessage(eb.build()).queue();
		                }
				    }, 1, TimeUnit.SECONDS);
	            }
	            else {
	                EmbedBuilder eb = new EmbedBuilder();
	                eb.setAuthor("Magic 8 Ball", null, "https://www.incandescentwaxmelts.com/wp-content/uploads/2017/07/8-BALL-SCENTSY-WARMER-2.png").setColor(660066);
	                eb.setDescription("An answer requires a question.");
	                Message msg = event.getChannel().sendMessage(eb.build()).complete();
	           
	            }
	               
	        }
	        
	        if(args[0].equalsIgnoreCase(Main.prefix + "t")) { 
		    	if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
		    		if(args.length == 2) {
		    			int reports = 0;
		    			event.getMessage().delete().queue();
		    			String id = event.getMessage().getMentionedMembers().get(0).getId();
		    			String name = event.getMessage().getMentionedUsers().get(0).getName();
						ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+ id);
						while (rs.next()) {
							reports = rs.getInt("reports");
						}
						reports = reports +1;
						String update1 = "update userinformations set reports ='" + reports + "' where userids=" + id;
						stmt.executeUpdate(update1);
						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle("**Thank you for your report "+name+"!**");
						eb.setDescription("**Keep reporting people to get cool extra perks such as the <@&736291596500205689> or <@&703793608376975400> role!** \n"
								+ "You reported a total of " + reports + " people!");
						eb.setColor(0x3dfc03);
						event.getChannel().sendMessage(eb.build()).queue();
		    		}
		    	}
	        }
			
			
			if(args[0].equalsIgnoreCase(Main.prefix + "ticketq")) {
				if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
					ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+event.getAuthor().getId());
					while (rs.next()) {
						Main.wtickets = rs.getInt("tickets");
						Main.otickets = rs.getInt("overalltickets");
					}
					
					String issuer = event.getAuthor().getName();
					EmbedBuilder ticketq = new EmbedBuilder();
					ticketq.setColor(0x03d7fc);
					ticketq.setTitle(issuer + "'s Ticket Quota");
					ticketq.setThumbnail(event.getAuthor().getAvatarUrl());
					ticketq.setDescription("_Quota resets every Monday @ 0AM MESZ_ \n");
					ticketq.addField("Tickets/week", Main.wtickets + "", true);
					ticketq.addField("Tickets overall", Main.otickets + "", true);
					event.getChannel().sendMessage(ticketq.build()).queue();
					ticketq.clear();
				}			
			}
			if(args[0].equalsIgnoreCase(Main.prefix + "resettickets")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					event.getChannel().sendMessage("resetting tickets this week").queue();
					event.getGuild().getMembersWithRoles(event.getGuild().getRoleById("709131925746483210")).forEach(member -> {
						String update1 = "update userinformations set tickets='0' where userids=" + member.getId();
						try {
							stmt.executeUpdate(update1);
							System.out.println("updated tickets for " + member.getId()+ " to 0");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					});
					event.getChannel().sendMessage("updated all test helper").queue();
					event.getGuild().getMembersWithRoles(event.getGuild().getRoleById("703761489726668850")).forEach(member -> {
						String update1 = "update userinformations set tickets='0' where userids=" + member.getId();
						try {
							stmt.executeUpdate(update1);
							System.out.println("updated tickets for " + member.getId()+ " to 0");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					event.getChannel().sendMessage("updated all helper").queue();
				}				
			}


			
			
			if(args[0].equalsIgnoreCase(Main.prefix + "fillitup")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					event.getChannel().sendMessage("yo i hope this will work lol.").queue();
					
					event.getGuild().getMembers().forEach(member -> {
						
						
						
						String timejoined = member.getTimeJoined().toString();
						String timejoined1 = timejoined.substring(0, 10);
						String timecreated = member.getTimeCreated().toString();
						String timecreated1 = timecreated.substring(0, 10);
						PreparedStatement ps;
						try {
							ps = conn.prepareStatement("SELECT userids FROM userinformations WHERE userids= " + member.getId());
							ResultSet rs = ps.executeQuery();
							if (!rs.next()) {
									int dailybonus = 0;
									int diamonds = 0;
									int coins = 1000;
									int usernum = 0;
									int tickets = 0;
									int messages = 0;
									int overalltickets = 0;
									String sql = "insert into userinformations"
											+" (userids, usernumber, tickets, overalltickets, messages, datejoin, accountcreated, coins, diamonds, dailybonus)"
											+" values ('" + member.getId() + "', '" + usernum + "', '"  + tickets + "', '" + overalltickets + "', '"+ messages + "', '"
											+ timejoined1 + "', '" + timecreated1 + "', '" + coins + "', '" + diamonds+ "', '" + dailybonus+ "')";
									try {
										stmt.executeUpdate(sql);
										System.out.println("adding user");
										event.getChannel().sendMessage("added!").queue();	
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}									
								}
							
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}


						
						
					});
					event.getChannel().sendMessage("lol.").queue();
				}				
			}
			if(args[0].equalsIgnoreCase(Main.prefix + "fillitup1")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					event.getChannel().sendMessage("yo i hope this will work lol.").queue();
					
					event.getGuild().getMembers().forEach(member -> {
						try {
							PreparedStatement statement = conn.prepareStatement("SELECT `userids` FROM `userinformations` WHERE userids = ? ");
							statement.setString(1, member.getId());
							ResultSet rs = statement.executeQuery();

							if (rs.next()) {
								System.out.println("adding user");
							} else {
								System.out.println("added already");
							}							
						} catch (Exception e) {
							e.printStackTrace();
						}

					});
				}
			}
			
			
			if(args[0].equalsIgnoreCase(Main.prefix + "embed")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					event.getChannel().sendMessage("```EmbedBuilder eb = new EmbedBuilder();\n" + 
							"eb.setDescription(\"\");\n" + 
							"eb.setColor(0x3dfc03);\n" + 
							"event.getChannel().sendMessage(eb.build()).queue();```").queue();					
				}

			}
			
			if(args[0].equalsIgnoreCase(Main.prefix + "updatememberf")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					String issuerid = event.getAuthor().getId();
					if(args.length == 10) {
						String arg1 = args[1].toString();
						String arg2 = args[2].toString();
						String arg3 = args[3].toString();
						String arg4 = args[4].toString();
						String arg5 = args[5].toString();
						String arg6 = args[6].toString();
						String arg7 = args[7].toString();
						String arg8 = args[8].toString();
						String arg9 = args[9].toString();
						String update1 = "update userinformations set userids='"+ arg1 + "' where userids=" + arg1;
						String update2 = "update userinformations set usernumber='"+ arg2 + "' where userids=" + arg1;
						String update4 = "update userinformations set tickets='"+ arg3 + "' where userids=" + arg1;
						String update5 = "update userinformations set overalltickets='"+ arg4 + "' where userids=" + arg1;
						String update6 = "update userinformations set messages='"+ arg5 + "' where userids=" + arg1;
						String update7 = "update userinformations set datejoin='"+ arg6 + "' where userids=" + arg1;
						String update8 = "update userinformations set accountcreated='"+ arg7 + "' where userids=" + arg1;
						String update9 = "update userinformations set coins='"+ arg8 + "' where userids=" + arg1;
						String update10 = "update userinformations set diamonds='"+ arg9 + "' where userids=" + arg1;
						
						try {

							stmt.executeUpdate(update2);
							stmt.executeUpdate(update4);
							stmt.executeUpdate(update5);
							stmt.executeUpdate(update6);
							stmt.executeUpdate(update7);
							stmt.executeUpdate(update8);
							stmt.executeUpdate(update9);
							stmt.executeUpdate(update10);
							stmt.executeUpdate(update1);							
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("**Changes to <@"+arg1 + ">**");
							eb.setTitle("Member got hard updated:" + update1 + "\n"+ update2 + "\n"+update4 + "\n"+ update5 + "\n"+ update6 + "\n"+ update7 + "\n"+ update8 + "\n"+ update9+ "\n"+ update10);
							event.getChannel().sendMessage(eb.build()).queue();
							event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"Member got hard updated:" + update1 + "\n"+ update2 +"\n"+ update4 + "\n"+ update5 + "\n"+ update6 + "\n"+ update7 + "\n"+ update8 + "\n"
									+ update9 +"\n" +update10 +" issued by " + issuerid).queue();
						} catch (Exception e) {
							e.printStackTrace();
							event.getChannel().sendMessage(":warning: I need userids usernumber tickets overalltickets messages datejoin accountcreated coins diamonds" +e.toString()).queue();	
						}
					}
					else {
						event.getChannel().sendMessage(":warning: 8 arguments are needed! The username field cant have spaces!").queue();						
					}

				}
			}
			if(args[0].equalsIgnoreCase(Main.prefix + "args")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				event.getChannel().sendMessage("```if(args[0].equalsIgnoreCase(Main.prefix + \"args\")) { \n \n }```").queue();
				}
			}
			
			if(args[0].equalsIgnoreCase(Main.prefix + "sendmessage")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				event.getChannel().sendMessage("```event.getChannel().sendMessage(\"\").queue();\nSystem.out.println(\"\");```").queue();					
				}
			 }
			
			if(args[0].equalsIgnoreCase("$delete")) {
				String issuerid = event.getAuthor().getId();
				if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
					event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +issuerid + " deleted a ticket (?)").queue();
					ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+issuerid);
					int currenttickets = 0;
					int overalltickets = 0;
					while (rs.next()) {
						currenttickets = rs.getInt("tickets");
						currenttickets = currenttickets + 1;
						overalltickets = rs.getInt("overalltickets");
						overalltickets = overalltickets + 1;
					}
					String update1 = "update userinformations set tickets='"+ currenttickets + "' where userids=" + issuerid;
					String update2 = "update userinformations set overalltickets='"+ overalltickets + "' where userids=" + issuerid;
					try {
						stmt.executeUpdate(update1);
						stmt.executeUpdate(update2);
						//event.getChannel().sendMessage("lol. "+ currenttickets).queue();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
			
			if(args[0].equalsIgnoreCase(Main.prefix + "memberinfo")) {
		        if (args.length != 2){
		            event.getChannel().sendMessage(":warning: Provide a name as Mention! For example: !uesrinfo @name").queue();
		        }else{
		        	if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
			            Member name;
			            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyy"); //Time formatter
			            try{
			            	//WE NEED TO ADD THE MEMBERINFO COMMAND TO THIS
			            	int reports = 0;
			                	name = event.getMessage().getMentionedMembers().get(0);
			                	String nameid = event.getMessage().getMentionedMembers().get(0).getId();
								ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations WHERE userids = "+nameid);
								while (rs.next()) {
									reports = rs.getInt("reports");
									Main.wtickets = rs.getInt("tickets");
									Main.otickets = rs.getInt("overalltickets");
									Main.memberID = rs.getString("userids");
									Main.usernum = rs.getInt("usernumber");
									Main.omessages = rs.getInt("messages");
								}
								String bans = "none";
								if (Main.usernum == 0) {
									bans = "none";
								}
								if (Main.usernum == 1) {
									bans = "1";
								}
								if (Main.usernum == 2) {
									bans = "2";
								}
								if (Main.usernum == 5) {
									bans = "none";
								}
								List<Role> rolesRaw = name.getRoles();
								List<String> roles = new ArrayList<String>();
								int n2 = rolesRaw.size();
								while (n2 > 0) {
									n2 = n2 - 1;
									Role r = rolesRaw.get(n2);
									String mention = r.getAsMention();
									roles.add(mention);
								}
								Collections.reverse(roles);
			                	EmbedBuilder eb = new EmbedBuilder();
			                	eb.setColor(0xbefc03);
			                	eb.setThumbnail(name.getUser().getAvatarUrl());
			                	eb.setAuthor("Information on " + name.getUser().getName(),"https://twitter.com/Spuckwaffel5000", name.getUser().getAvatarUrl());
			                	eb.setDescription("**General informations about <@" + nameid + ">: **");
			                	eb.addField("Status:", name.getOnlineStatus().toString(), true);
			                	eb.addField("Join time:", name.getTimeJoined().format(fmt), true);
			                	eb.addField("Creation time:", name.getTimeCreated().format(fmt), true);
			                	eb.addField("Reports:", reports + "", true);
			                	eb.addField("Tickets :", Main.wtickets + " | " +Main.otickets + "", true);
			                	eb.addField("Messages overall:", Main.omessages + "", true);
			                	eb.addField("ID:", Main.memberID , true);
			                	eb.addField("Usernum:", Main.usernum + "", true);
			                	eb.addField("Bans:", bans, true);
			                	eb.addField("Roles ["+roles.size()+"]", ""+roles, true);

			                	event.getChannel().sendMessage(eb.build()).queue();
			            	}
			            catch (IndexOutOfBoundsException e){
			            	event.getChannel().sendMessage(":warning: Provide a name as Mention!").queue();
			    		}
		        	}
		            
		        	}
				}
		
			
			
			if(args[0].equalsIgnoreCase(Main.prefix + "updatetickets")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					String issuerid = event.getAuthor().getId();
					if(args.length == 3) {
						String update = "update userinformations set tickets='"+ args[2] + "' where userids=" + args[1];
						
						try {
							stmt.executeUpdate(update);
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("<a:tickle:737328378109231184> Tickets got updated for "+ args[1] + " (<@" +args[1] + ">) -> " + args[2]);
							eb.setColor(0x3dfc03);
							event.getChannel().sendMessage(eb.build()).queue();
							event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"tickets got updated for <@" +args[1] + "> issued by <@" + issuerid+ "> -> " + args[2]).queue();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else {
						event.getChannel().sendMessage(":warning: Use !updatetickets <ID> <Tickets>").queue();
					}
				}
			}
			
			if(args[0].equalsIgnoreCase(Main.prefix + "updateoveralltickets")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					if(args.length == 3) {
						String issuerid = event.getAuthor().getId();
						String update = "update userinformations set overalltickets='"+ args[2] + "' where userids=" + args[1];
						
						try {
							stmt.executeUpdate(update);
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("<a:tickle:737328378109231184> Overalltickets got updated for "+ args[1] + " (<@" +args[1] + ">) -> " + args[2]);
							eb.setColor(0x3dfc03);
							event.getChannel().sendMessage(eb.build()).queue();
							event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"overalltickets got updated for <@" +args[1] + "> issued by <@" + issuerid+ "> -> " + args[2]).queue();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else {
						event.getChannel().sendMessage(":warning: Use !updateoveralltickets <ID> <Tickets>").queue();
					}
				}
			}
			
			if(args[0].equalsIgnoreCase(Main.prefix + "updatemessages")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					if(args.length == 3) {
						String issuerid = event.getAuthor().getId();
						String update = "update userinformations set messages='"+ args[2] + "' where userids=" + args[1];
						
						try {
							stmt.executeUpdate(update);
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("<a:tickle:737328378109231184> Member messages got updated for <@" +args[1] + "> -> " + args[2]);
							eb.setColor(0x3dfc03);
							event.getChannel().sendMessage(eb.build()).queue();
							event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"messages got updated for <@" +args[1] + "> issued by " + issuerid+ " -> " + args[2]).queue();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else {
						event.getChannel().sendMessage(":warning: Use !updatemessages <ID> <messages>").queue();
					}
				}
			}
			
			if(args[0].equalsIgnoreCase(Main.prefix + "userow")) {
				if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
					if(args.length == 3) {
						String update = "update userinformations set usernumber='"+ args[2] + "' where userids=" + args[1];
						String issuerid = event.getAuthor().getId();
						try {
							stmt.executeUpdate(update);
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("<a:tickle:737328378109231184> Member usernumber got updated for "+ args[1] + " (<@" +args[1] + ">) -> " + args[2]);
							eb.setColor(0x3dfc03);
							event.getChannel().sendMessage(eb.build()).queue();
							event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime +"usernum got updated for <@" +args[1] + "> issued by " + issuerid + " -> " + args[2]).queue();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else {
						event.getChannel().sendMessage(":warning: Use !updateusernum <ID> <0,1,2,5>").queue();
					}
				}
			}
	
			
			if(args[0].equalsIgnoreCase(Main.prefix + "adduserr")) {
				if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					if(args.length == 11) {
						String issuerid = event.getAuthor().getId();
						String sql = "insert into userinformations"
								+" (userids, usernumber, tickets, overalltickets, messages, datejoin, accountcreated, coins, diamonds, dailybonus, begbonus)"
								+" values ('" + args[1] + "', '" + args[2] + "', '" + args[3] + "', '" + args[4] + "', '" + args[5] + "', '"
								+ Main.todaysdate + "', '" + args[6] + "', '" + args[7] + "', '" + args[8] +  "', '" + args[9] + "', '" + args[10] +"')";
					stmt.executeUpdate(sql);
					EmbedBuilder eb = new EmbedBuilder();
					eb.setDescription("<a:tickle:737328378109231184> User got added: " +args[1] + "', '" + args[2] + "', '" + args[3] + "', '" + args[4] + "', '" + args[5] + "', '"
							+ Main.todaysdate + "', '" + args[6] + "', '" + args[7] + "', '" + args[8] +  "', '" + args[9] +"', '" + args[10] +"')");
					eb.setColor(0x3dfc03);
					event.getChannel().sendMessage(eb.build()).queue();
					System.out.println(Main.nowtime +"Insert complete");
					event.getGuild().getTextChannelById("743438841293045800").sendMessage(Main.nowtime + "user got added "
							+ args[1] + "', '" + "', '" + args[2] + "', '" + args[3] + "', '" + args[4] + "', '" + args[5] + "', '"
							+ Main.todaysdate + "', '" + args[6] + "', '" + args[7] + "', '" + args[8] +  "', '" + args[9] +"', '" + args[10] +"') issued by " + issuerid).queue();
					}
					event.getChannel().sendMessage("i need userid, usernumber, username, tickets, overalltickets, messages, accountcreated,coins, diamonds, dailybonus, begbonus").queue();
				}			
			}
			Message msg = event.getMessage();
		    if (msg.getContentRaw().equals(Main.prefix +"ping"))
		    {
		        if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					long start = System.currentTimeMillis();
					Message msg1 = event.getChannel().sendMessage("Testing Pong...").complete();
					long ping = System.currentTimeMillis() - start;
					msg1.editMessage(":ping_pong: Pong! `"+ping+"ms`").queue();
		                   };
		    }
		    if (args[0].equalsIgnoreCase(Main.prefix + "say")){
		    	if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			    	event.getMessage().delete().queue();
			    	event.getChannel().sendMessage(event.getMessage().getContentRaw().replace(event.getMessage().getContentRaw().split(" ")[0]+" ", "")).queue();		    		
		    	}
		    }
		    
		    if (args[0].equalsIgnoreCase(Main.prefix + "l")){
		    	event.getMessage().delete().queue();
		    	EmbedBuilder eb = new EmbedBuilder();
		    	eb.setTitle("You forgot something!");
		    	eb.addField("Please send us a video with proof! You can use", "**[streamable.com](https://streamable.com/) to upload the video."
		    			+ " Please give us the link if you uploaded it.**", false);
		    	eb.setThumbnail("https://i.ibb.co/S5t9Ghn/download.png");
		    	eb.setColor(0x3dfc03);
		    	event.getChannel().sendMessage(eb.build()).queue();
		    }

		    
		    
			if (args[0].equalsIgnoreCase("!serverinfo") || args[0].equalsIgnoreCase("!info")) {
				if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(0x005ef5);
					eb.setAuthor("Server Information", null, event.getGuild().getIconUrl());
					eb.addField("Server Name", ""+event.getGuild().getName(), true);
					eb.addField("Server ID", ""+event.getGuild().getId(), true);
					eb.addField("Total Members", event.getGuild().getMembers().size() + "", true);
					eb.addField("Owner", ""+event.getGuild().getOwner().getAsMention(), true);
					eb.addField("Host Reigon", ""+event.getGuild().getRegionRaw(), true);
					eb.addField("Creation Date", event.getGuild().getTimeCreated().getDayOfMonth()+"/"+event.getGuild().getTimeCreated().getMonthValue()+"/"+event.getGuild().getTimeCreated().getYear()
							+" @ "+event.getGuild().getTimeCreated().getHour()+":"+event.getGuild().getTimeCreated().getMinute(), true);
					eb.addField("Notification Level", ""+event.getGuild().getDefaultNotificationLevel(), true);
					eb.addField("Verification Level", ""+event.getGuild().getVerificationLevel(), true);
					eb.addField("Bot Version", "v2.8.8 Pre rel.", true);
					eb.setFooter("Bot made by Spuckwaffel#5000 for Professor#9999");
					event.getChannel().sendMessage(eb.build()).queue();					
				}
			}
			
			if(args[0].equalsIgnoreCase(Main.prefix + "uptime")) { 
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Up since:");
				eb.setDescription(Main.todaysdate + "\n" + Main.todaystime);
				eb.setColor(0x3dfc03);
				event.getChannel().sendMessage(eb.build()).queue();
			 }
			
			if(args[0].equalsIgnoreCase(Main.prefix + "lb")) {
				if(args[1].equals("weekly")) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Weekly tickets leaderboard");
					ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations ORDER BY tickets DESC");
				      int counter = 1;
				      while (counter <= 10 && rs.next()) {
				    	  String m = "unknown member";
				    	  String id = rs.getString("userids");
				    	  int tickets = rs.getInt("tickets");
				    	  try {
				    		  m=event.getGuild().getMemberById(rs.getString("userids")).getAsMention();
				    	  }catch (NullPointerException e1) { m = m + " (" + rs.getString("userids") + ")";}
				    	  eb.addField(tickets + "",m , false);
				    	  eb.setColor(0x3dfc03);
				    	  counter++;
				      }
				      event.getChannel().sendMessage(eb.build()).queue();
				}
				if(args[1].equals("overall")) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Overall tickets leaderboard");
					ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userinformations ORDER BY overalltickets DESC");
				      int counter = 1;
				      while (counter <= 10 && rs.next()) {
				    	  String m = "unknown member";
				    	  String id = rs.getString("userids");
				    	  int tickets = rs.getInt("overalltickets");
				    	  try {
				    		  m=event.getGuild().getMemberById(rs.getString("userids")).getAsMention();
				    	  }catch (NullPointerException e1) { m = m + " (" + rs.getString("userids") + ")";}
				    	  eb.addField(tickets + "",m , false);
				    	  eb.setColor(0x3dfc03);
				    	  counter++;
				      }
				      event.getChannel().sendMessage(eb.build()).queue();
				}
			 }
			
			conn.close();
		} catch(Exception e){
			event.getJDA().getGuildById("703751815077822564").getTextChannelById("753658962985091152").sendMessage(e.toString()).queue();
		}
	}
		
}
