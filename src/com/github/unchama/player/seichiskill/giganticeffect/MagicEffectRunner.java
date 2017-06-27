package com.github.unchama.player.seichiskill.giganticeffect;

import java.util.HashMap;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;
import org.bukkit.metadata.FixedMetadataValue;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.effect.DelayEffectRunner;
import com.github.unchama.task.EntityRemoveTaskRunnable;

public final class MagicEffectRunner extends DelayEffectRunner {
	private static final DyeColor[] colors = { DyeColor.RED, DyeColor.BLUE, DyeColor.YELLOW, DyeColor.GREEN };
	private static final double p = 0.2;
	private static final double c_p = 0.05;

	@Override
	protected int getDelayTick() {
		return 10;
	}

	@Override
	protected void explosionEffectInit(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist, BreakRange range) {
		int n = rnd.nextInt(colors.length);
		alllist.forEach(b -> {
			b.setType(Material.WOOL);
			BlockState state = b.getState();
			Wool wool = (Wool)state.getData();
			wool.setColor(colors[n]);
			state.update();
		});
	}

	@Override
	protected void magicdriveEffectInit(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist, BreakRange range) {
		int n = rnd.nextInt(colors.length);
		alllist.forEach(b -> {
			b.setType(Material.WOOL);
			BlockState state = b.getState();
			Wool wool = (Wool)state.getData();
			wool.setColor(colors[n]);
			state.update();
		});
	}

	@Override
	protected void condensationEffectInit(GiganticPlayer gp, Block block, List<Block> liquidlist, BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void ruinfieldEffectInit(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist, BreakRange range) {
		int n = rnd.nextInt(colors.length);
		alllist.forEach(b -> {
			b.setType(Material.WOOL);
			BlockState state = b.getState();
			Wool wool = (Wool)state.getData();
			wool.setColor(colors[n]);
			state.update();
		});
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
			List<Block> liquidlist, List<Block> alllist, BreakRange range) {
		Player player = PlayerManager.getPlayer(gp);
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			if(rnd.nextDouble() < p){
				b.getWorld().spawnParticle(Particle.NOTE, b.getLocation().add(0.5, 0.5, 0.5), 1);
			}
			if(rnd.nextDouble() < c_p){
				Chicken chicken = (Chicken) player.getWorld().spawnEntity(b.getLocation(), EntityType.CHICKEN);
				chicken.setMetadata("SkillEffect", new FixedMetadataValue(plugin, true));
				chicken.playEffect(EntityEffect.WITCH_MAGIC);
				chicken.setInvulnerable(true);
				new EntityRemoveTaskRunnable((Entity) chicken).runTaskLater(plugin, 50);
				Gigantic.skilledEntityList.add((Entity)chicken);
				chicken.getWorld().playSound(b.getLocation(), Sound.ENTITY_WITCH_AMBIENT, 3.0F, 1.5F);
			}
		});
	}

	@Override
	protected void magicdriveEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist, BreakRange range) {
		Player player = PlayerManager.getPlayer(gp);
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			if(rnd.nextDouble() < p){
				b.getWorld().spawnParticle(Particle.NOTE, b.getLocation().add(0.5, 0.5, 0.5), 1);
			}
			if(rnd.nextDouble() < c_p){
				Chicken chicken = (Chicken) player.getWorld().spawnEntity(b.getLocation(), EntityType.CHICKEN);
				chicken.setMetadata("SkillEffect", new FixedMetadataValue(plugin, true));
				chicken.playEffect(EntityEffect.WITCH_MAGIC);
				chicken.setInvulnerable(true);
				new EntityRemoveTaskRunnable((Entity) chicken).runTaskLater(plugin, 50);
				Gigantic.skilledEntityList.add((Entity)chicken);
				chicken.getWorld().playSound(b.getLocation(), Sound.ENTITY_WITCH_AMBIENT, 3.0F, 1.5F);
			}
		});
	}

	@Override
	protected void explosionEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist, BreakRange range) {
		Player player = PlayerManager.getPlayer(gp);
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			if(rnd.nextDouble() < p){
				b.getWorld().spawnParticle(Particle.NOTE, b.getLocation().add(0.5, 0.5, 0.5), 1);
			}
			if(rnd.nextDouble() < c_p){
				Chicken chicken = (Chicken) player.getWorld().spawnEntity(b.getLocation(), EntityType.CHICKEN);
				chicken.setMetadata("SkillEffect", new FixedMetadataValue(plugin, true));
				chicken.playEffect(EntityEffect.WITCH_MAGIC);
				chicken.setInvulnerable(true);
				new EntityRemoveTaskRunnable((Entity) chicken).runTaskLater(plugin, 50);
				Gigantic.skilledEntityList.add((Entity)chicken);
				chicken.getWorld().playSound(b.getLocation(), Sound.ENTITY_WITCH_AMBIENT, 3.0F, 1.5F);
			}
		});
	}

	@Override
	public boolean isImproved(ActiveSkillType st) {
		switch(st){
		case CONDENSATION:
		case FAIRYAEGIS:
			return false;
		case MAGICDRIVE:
		case RUINFIELD:
		case EXPLOSION:
			return true;
		}
		return true;
	}

	@Override
	protected void improvedFairyAegisEffectonSet(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist, HashMap<Integer, List<Block>> breakMap) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
