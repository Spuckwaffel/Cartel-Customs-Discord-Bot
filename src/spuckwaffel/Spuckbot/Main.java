/*
 * From: Spuckwaffel#5000
 */

package spuckwaffel.Spuckbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import spuckwaffel.Spuckbot.Clans.ClanCreate;
import spuckwaffel.Spuckbot.Clans.ClanInfo;
import spuckwaffel.Spuckbot.Clans.ClanJoin;
import spuckwaffel.Spuckbot.Clans.ClanLeave;
import spuckwaffel.Spuckbot.Clans.ClanSettings;
import spuckwaffel.Spuckbot.Clans.ClanStore;
import spuckwaffel.Spuckbot.Clans.ClanMine.ClanMine;
import spuckwaffel.Spuckbot.Clans.ClanMine.ClanMineCollect;
import spuckwaffel.Spuckbot.Clans.ClanMine.ClanMineItems;
import spuckwaffel.Spuckbot.Clans.ClanMine.ClanMineSell;
import spuckwaffel.Spuckbot.Clans.ClanMine.ClanMineUpgrades;
import spuckwaffel.Spuckbot.Clans.Clansettings.Admin;
import spuckwaffel.Spuckbot.Clans.Clansettings.JoinStatus;
import spuckwaffel.Spuckbot.Clans.Clansettings.Kick;
import spuckwaffel.Spuckbot.Clans.Clansettings.Ownership;
import spuckwaffel.Spuckbot.Commands.Beg;
import spuckwaffel.Spuckbot.Commands.Bet;
//import spuckwaffel.Spuckbot.Events.GuildMemberLeave; <- not finished
import spuckwaffel.Spuckbot.Commands.Commands;
import spuckwaffel.Spuckbot.Commands.Commands2;
import spuckwaffel.Spuckbot.Commands.DMconvo;
import spuckwaffel.Spuckbot.Commands.Daily;
import spuckwaffel.Spuckbot.Commands.DailyCMD;
import spuckwaffel.Spuckbot.Commands.DailyCMDcreate;
import spuckwaffel.Spuckbot.Commands.Folder;
import spuckwaffel.Spuckbot.Commands.HigherLower;
import spuckwaffel.Spuckbot.Commands.RNGCommands;
import spuckwaffel.Spuckbot.Commands.ShowBan;
import spuckwaffel.Spuckbot.Commands.Slots;
//import spuckwaffel.Spuckbot.Events.ChangeName; <- removed
import spuckwaffel.Spuckbot.Events.Dmmessagereceived;
import spuckwaffel.Spuckbot.Events.GuildMemberJoin;
import spuckwaffel.Spuckbot.Events.GuildMessageReactionAdd;
import spuckwaffel.Spuckbot.Events.Ready;
//import spuckwaffel.Spuckbot.Events.TextChange; <- removed

@SuppressWarnings("unused")
public class Main extends ListenerAdapter
{
	public static String question = "";
	public static String askedby = "";
	public static String dmmessage = "none";
	public static String dmmember = "none";
	public static int usernum = 0;
	public static String memberID = "none";
	public static int wtickets = 0;
	public static int otickets = 0;
	public static int omessages = 0;
	public static JDA jda;
	public static String log = "\n";
	public static String prefix = "!";
	public static String ticketquota = "";
	public static String todaysdate = "empty";
	public static String todaystime = "empty";
	public static String nowtime = "empty";
	public static String lastmemberjointime = "2000-01-01T00:00:00.000Z";
	public static String lastmemberjoindate = "2000-01-01";
	
	// Main method here
	public static void main(String[] args) throws Exception, LoginException, FileNotFoundException, IOException{
		

		try{
				String url = "jdbc:mysql://xx/CCBot?serverTimezone=UTC";
				String username = "ccbot";
				String password = "zs4GCaL";
				System.out.println("Connected");
				Connection conn = DriverManager.getConnection(url,username,password);
				
				
		} catch(Exception e){
			System.out.println(e);
		}
		
		
		//////////////////////////////////////////////////////////////
		
		System.out.println("Yawn. The bot woke up! Here are some stats:");
		
		Date currentDate = new Date();
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("current date:" +dateformat.format(currentDate));
		Main.todaysdate = dateformat.format(currentDate);
		
		Date currentTime = new Date();
		
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
		System.out.println("[" +timeformat.format(currentTime) + "]");
		Main.todaystime = timeformat.format(currentTime);
		
		
	    
		// dev xx
		// main xx
        JDABuilder.createLight("token", GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES,
        		GatewayIntent.DIRECT_MESSAGE_REACTIONS,
        		GatewayIntent.GUILD_MEMBERS,
        		//GatewayIntent.GUILD_MESSAGE_REACTIONS,
        		GatewayIntent.DIRECT_MESSAGES)
        .setStatus(OnlineStatus.IDLE)
        .setMemberCachePolicy(MemberCachePolicy.ALL)
        
        
        .addEventListeners(new GuildMemberJoin())
		.addEventListeners(new GuildMessageReactionAdd())
		.addEventListeners(new Dmmessagereceived())
		
		
		//Commands [Staff/Admin Commands]
		
		.addEventListeners(new Commands())
		.addEventListeners(new DMconvo())
		.addEventListeners(new Commands2())
		.addEventListeners(new ShowBan())
		
		//Commands[RNG Commands]
		
		.addEventListeners(new DailyCMDcreate())
		.addEventListeners(new DailyCMD())
		.addEventListeners(new Slots())
		.addEventListeners(new Daily())
		.addEventListeners(new Beg())
		.addEventListeners(new Bet())
		.addEventListeners(new Ready())
		.addEventListeners(new RNGCommands())
		.addEventListeners(new HigherLower())

		
		//Pre release commands (a lot bugs can happen!)
		//Commands [Clan Commands]
		
		.addEventListeners(new Kick())
		.addEventListeners(new Ownership())
		.addEventListeners(new JoinStatus())
		.addEventListeners(new Admin())
		.addEventListeners(new ClanJoin())
		.addEventListeners(new ClanInfo())
		.addEventListeners(new ClanLeave())
		.addEventListeners(new ClanCreate())
		.addEventListeners(new ClanSettings())
		.addEventListeners(new ClanStore())
		.addEventListeners(new ClanMine())
		.addEventListeners(new ClanMineSell())
		.addEventListeners(new ClanMineItems())
		.addEventListeners(new ClanMineCollect())
		.addEventListeners(new ClanMineUpgrades())
		.addEventListeners(new Folder())

		
		
		//DEV but never undev lol (for a long time or until i want to)
		
		//jda.addEventListener(new GuildMemberLeave());
		//jda.addEventListener(new GetAccess());	
		.setActivity(Activity.streaming("discord.gg/cartelcustoms \n twitter.com/Cartel_Customs", "https://www.twitch.tv/professorfnbr"))
        .build();
		
	}
}







