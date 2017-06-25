package com.github.unchama.player.seichiskill.effect;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.effect.DelayEffectRunner;
import com.github.unchama.task.ArrowControlTaskRunnable;

public final class MeteoEffectRunner extends DelayEffectRunner {
	private static double p = 0.1;

	@Override
	protected int getDelayTick() {
		return 10;
	}

	@Override
	protected void explosionEffectInit(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void magicdriveEffectInit(GiganticPlayer gp,Block block,List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		Player player = PlayerManager.getPlayer(gp);
		Coordinate centerloc = range.getCenter(player);
		Block rb = block.getRelative(centerloc.getX(), centerloc.getY(), centerloc.getZ());
		launchFireball(player,rb.getLocation());
	}

	private void launchFireball(Player player, Location centerloc) {
		Location ploc = player.getLocation();
		Location launchloc = ploc.add((rnd.nextDouble() * 20)-10 ,(rnd.nextDouble() * 10) + 60 + centerloc.getY() - ploc.getY(),(rnd.nextDouble() * 20)-10);
		Vector vec = new Vector(centerloc.getX()-launchloc.getX(),centerloc.getY()-launchloc.getY(),centerloc.getZ()-launchloc.getZ());
		launchloc.setDirection(vec);
		vec.normalize();
		LargeFireball proj = player.getWorld().spawn(launchloc,LargeFireball.class);
		double k = 1.0;
        vec.setX(vec.getX() * k);
        vec.setY(vec.getY() * k);
        vec.setZ(vec.getZ() * k);
        proj.setShooter(player);
        proj.setMetadata("SkillEffect", new FixedMetadataValue(plugin, true));
        proj.setVelocity(vec);
        Gigantic.skilledEntityList.add(proj);
        new ArrowControlTaskRunnable((Projectile)proj,centerloc).runTaskTimer(plugin, 0, 1);
	}

	@Override
	protected void condensationEffectInit(GiganticPlayer gp, Block block, List<Block> liquidlist, BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void ruinfieldEffectInit(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void fairyaegisEffectonBreakInit(GiganticPlayer gp, Block block, List<Block> breaklist, boolean soundflag) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void fairyaegisEffectonBreakDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			boolean soundflag) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void condensationEffectDelayed(GiganticPlayer gp, Block block, List<Block> liquidlist, BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void ruinfieldEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
	}

	@Override
	protected void magicdriveEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			if(rnd.nextDouble() < p){
				b.getWorld().playEffect(b.getLocation(),Effect.EXPLOSION_HUGE,1);
				b.getWorld().playSound(b.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, (float)4.0F, (float)((rnd.nextDouble()*0.4)+0.8));
			}
		});

	}

	@Override
	protected void explosionEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isImproved(ActiveSkillType st) {
		switch (st) {
		case MAGICDRIVE:
			return true;
		default:
			return false;
		}
	}

	@Override
	protected void improvedFairyAegisEffectonSet(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			HashMap<Integer, List<Block>> breakMap) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
