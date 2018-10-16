package com.gmail.berndivader.speechbubblesaddon;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.SkillString;

public 
class 
Utils {
	
	public static Vector getSideOffsetVectorFixed(float vYa, double hO, boolean iy) {
		double y=0d;
		if (!iy) y=Math.toRadians(vYa);
		double xo=Math.cos(y)*hO;
		double zo=Math.sin(y)*hO;
		return new Vector(xo,y,zo);
	}
	
	public static Vector getFrontBackOffsetVector(Vector v, double o) {
		Vector d=v.clone();
		d.normalize();
		d.multiply(o);
		return d;
	}

	public static double round(double value, int places) {
		return new BigDecimal(value).round(new MathContext(places, RoundingMode.HALF_UP)).doubleValue();
	}
	
	public static String parseMobVariables(String s,SkillMetadata m,AbstractEntity c,AbstractEntity t,AbstractLocation l) {
		AbstractLocation l1=l!=null?l:t!=null?t.getLocation():null;
		s=SkillString.parseMobVariables(s,m.getCaster(),t,m.getTrigger());
		if (l1!=null&&s.contains("<target.l")) {
			s=s.replaceAll("<target.l.w>",l1.getWorld().getName());
			s=s.replaceAll("<target.l.x>",Double.toString(l1.getBlockX()));
			s=s.replaceAll("<target.l.y>",Double.toString(l1.getBlockY()));
			s=s.replaceAll("<target.l.z>",Double.toString(l1.getBlockZ()));
			if (s.contains("<target.l.d")) {
				s=s.replaceAll("<target.l.dx>",Double.toString(l1.getX()));
				s=s.replaceAll("<target.l.dy>",Double.toString(l1.getY()));
				s=s.replaceAll("<target.l.dz>",Double.toString(l1.getZ()));
			}
		}
		if (s.contains(".meta.")) s=parseMetaVar(s,c,t,l);
		return s;
	}
	
	private static String parseMetaVar(String s,AbstractEntity a1,AbstractEntity a2,AbstractLocation a3) {
		Entity e1=a1!=null?a1.getBukkitEntity():null;
		Entity e2=a2!=null?a2.getBukkitEntity():null;
		Location l1=a3!=null?BukkitAdapter.adapt(a3):null;
		if (s.contains("<target.meta")) {
			String[]p=s.split("<target.meta.");
			if (p.length>1) {
				String s1=p[1].split(">")[0];
				if (l1!=null&&l1.getWorld().getBlockAt(l1).hasMetadata(s1)) return s.replace("<target.meta."+s1+">"
						,l1.getWorld().getBlockAt(l1).getMetadata(s1).get(0).asString());
				if (e2!=null&&e2.hasMetadata(s1)) {
					return s.replaceAll("<target.meta."+s1+">",e2.getMetadata(s1).get(0).asString());
				}
			}
		} else if (s.contains("<mob.meta")) {
			String[]p=s.split("<mob.meta.");
			if (p.length>1) {
				String s1=p[1].split(">")[0];
				if (e1!=null&&e1.hasMetadata(s1)) return s.replaceAll("<mob.meta."+s1+">",e1.getMetadata(s1).get(0).asString());
			}
		}
		return s;
	}

	public static String[] wrapStr(String s, int l) {
		String r="";
		String d="&&br&&";
		int ldp=0;
		for (String t:s.split(" ",-1)) {
			if (r.length()-ldp+t.length()>l) {
				r=r+d+t;
				ldp=r.length()+1;
			} else {
				r+=(r.isEmpty()?"":" ")+t;
			}
		}
		return r.split(d);
	}
	
	
	
}
