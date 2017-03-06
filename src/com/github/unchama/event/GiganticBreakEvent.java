package com.github.unchama.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class GiganticBreakEvent extends BlockBreakEvent{

	public GiganticBreakEvent(Block theBlock, Player player) {
		super(theBlock, player);
	}

}
