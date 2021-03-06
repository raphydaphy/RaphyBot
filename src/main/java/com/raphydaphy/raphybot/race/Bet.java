package com.raphydaphy.raphybot.race;

import java.math.BigInteger;
import java.util.Random;

import sx.blah.discord.handle.obj.IUser;

public class Bet
{
	private final IUser player;
	private final BigInteger amount;
	private final String icon;
	private int progress;

	private static final String[] icons = { ":cat:", ":hamster:", ":frog:", ":bear:", ":cow:", ":monkey:", ":camel:",
			":panda_face:", ":baby_chick:", ":chicken:", ":bug:", ":beetle:", ":tropical_fish:", ":whale2:", ":ram:",
			":tiger2:", ":goat:", ":pig2:", ":dragon_face:", ":dromedary_camel:", ":poodle:", ":smiling_imp:",
			":woman:", ":smiley_cat:", ":skull:", ":snowman:", ":dog:", ":rabbit:", ":tiger:", ":pig:", ":boar:",
			":horse:", ":sheep:", ":snake:", ":hatched_chick:", ":penguin:", ":bee:", ":snail:", ":fish:", ":dolphin:",
			":rat:", ":rabbit2:", ":rooster:", ":mouse2:", ":blowfish:", ":leopard:", ":mouse:", ":wolf:", ":koala:",
			":pig_nose:", ":monkey_face:", ":racehorse:", ":elephant:", ":bird:", ":hatching_chick:", ":turtle:",
			":ant:", ":octopus:", ":whale:", ":cow2:", ":water_buffalo:", ":dragon:", ":dog2:", ":ox:", ":crocodile:",
			":cat2:" };

	public Bet(IUser player, BigInteger amount, Random rand)
	{
		this.player = player;
		this.amount = amount;
		this.icon = icons[rand.nextInt(icons.length)];
		this.progress = 0;
	}

	public IUser getPlayer()
	{
		return player;
	}

	public BigInteger getAmount()
	{
		return amount;
	}

	public String getIcon()
	{
		return icon;
	}

	public int getProgress()
	{
		return progress;
	}

	public void setProgress(int progress)
	{
		this.progress = progress;
	}
}
