package com.raphydaphy.raphybot.race;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.raphydaphy.raphybot.BotUtils;
import com.raphydaphy.raphybot.RaphyBot;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

public class Race
{
	private final List<Bet> bets;
	private int counter;
	private boolean raceStarted;
	private final IChannel channel;
	private IMessage raceInfo;
	private Timer raceTimer;
	private boolean isFinished;

	public Race(int time, IChannel channel)
	{
		bets = new ArrayList<>();
		counter = time;
		this.channel = channel;
		raceStarted = false;
		raceTimer = new Timer();
		isFinished = false;
		raceTimer.schedule(new RaceUpdater(), 0, 1000);
		makeBet(RaphyBot.client.getOurUser(), (int)(Math.random() * 25));
	}

	public void postNewMessage(EmbedObject message)
	{
		if (getRaceMessage() != null)
		{
			getRaceMessage().delete();
		}
		RequestBuffer.request(() ->
		{
			try
			{
				setRaceMessage(channel.sendMessage(message));
			} catch (DiscordException e)
			{
				System.err.println("There was an error sending a message. Printing stack trace... ");
				e.printStackTrace();
			}
		});
	}

	public boolean makeBet(IUser player, int amount)
	{
		if (!raceStarted)
		{
			for (Bet bet : bets)
			{
				if (bet.getPlayer().getLongID() == player.getLongID())
				{
					return false;
				}
			}
			bets.add(new Bet(player, amount));
			return true;
		}
		return false;
	}

	public IMessage getRaceMessage()
	{
		return raceInfo;
	}

	public void setRaceMessage(IMessage raceMessage)
	{
		this.raceInfo = raceMessage;
	}

	public boolean isFinished()
	{
		return isFinished;
	}

	private class RaceUpdater extends TimerTask
	{
		@Override
		public void run()
		{
			if (counter > 0 && !raceStarted)
			{
				counter--;

				if (counter % 5 == 0 && raceInfo != null)
				{
					EmbedBuilder builder = new EmbedBuilder();
					builder.withColor(BotUtils.messageColor.getRed(), BotUtils.messageColor.getGreen(),
							BotUtils.messageColor.getBlue());
					String betInfo = "";
					for (Bet bet : bets)
					{
						betInfo += bet.getIcon() + " " +  bet.getPlayer().getName() + ": " + bet.getAmount() + "\n";
					}
					if (bets.isEmpty())
					{
						betInfo = "Noone has placed a bet yet! Use `!race bet [amount]` to be the first :D";
					}
					builder.withTitle("Race Betting Open!");
					builder.withDesc(counter + " seconds to go!");
					builder.appendField("Bets", betInfo, false);
					raceInfo.edit(builder.build());
				}
			} else if (counter <= 0 && !raceStarted)
			{
				raceStarted = true;
				EmbedBuilder builder = new EmbedBuilder();
				builder.withColor(BotUtils.messageColor.getRed(), BotUtils.messageColor.getGreen(),
						BotUtils.messageColor.getBlue());
				builder.appendField("Race Really Started!",
						"The betting has closed for the race in " + channel + ". Race started!", true);
				postNewMessage(builder.build());
			} else
			{
				raceTimer.cancel();
				isFinished = true;
			}
		}

	}
}
