package com.untamedears.citadel.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.untamedears.citadel.Citadel;
import com.valadian.nametracker.NameAPI;

/**
 * User: JonnyD & chrisrico
 * Date: 7/18/12
 * Time: 11:57 PM
 */

@Entity
@Table(name="faction_member",  uniqueConstraints={
		   @UniqueConstraint(columnNames={"faction_name", "member_name"})})
public class FactionMember implements Comparable {
	@Id private String factionName;
    // memberName now refers to the account UUID
	@Id private String memberName;
	
	public FactionMember(){}
	
	public FactionMember(String memberName, String factionName){
		this.memberName = memberName;
		this.factionName = factionName;
		
		if (memberName == null){
			Citadel.info("FactionMember.Java - Faction Member created without member name.  For faction name " + ((factionName==null)?"null":factionName));
		}
		if (factionName == null){
			Citadel.info("FactionMember.Java - Faction Member created without faction name.  For member name " + ((memberName==null)?"null":memberName));
		}
	}

	public String getMemberName(){
		return this.memberName;
	}
	
    // This is the account ID for the player (in UUID.toString format)
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}

    public String getPlayerName() {
        return NameAPI.getCurrentName(UUID.fromString(this.memberName));
    }

    public Player getPlayer() {
    	Player thePlayer;
    	
    	try {
    		thePlayer = Bukkit.getPlayer(UUID.fromString(memberName));
    	} catch (Exception ex) {
    		thePlayer = null;
    		
    		String errorMessage = "Exception in FactionMember.java for member name " + (memberName == null ? "null" : memberName) +
    				" that is part of faction " + (factionName == null ? "null" : factionName) +
    				ex.toString()
    				;
    		Citadel.warning(errorMessage);
    	}
    	
        return thePlayer; 
    }

	public String getFactionName(){
		return this.factionName;
	}
	
	public void setFactionName(String factionName){
		this.factionName = factionName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FactionMember)) return false;
        FactionMember that = (FactionMember) o;
        return factionName.equals(that.factionName) && memberName.equals(that.memberName);
    }

    @Override
    public int hashCode() {
        int result = factionName.hashCode();
        result = 31 * result + memberName.hashCode();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof FactionMember)) {
            throw new ClassCastException();
        }
        FactionMember other = (FactionMember)o;
        int compare = this.getFactionName().compareTo(other.getFactionName());
        if (compare != 0) {
            return compare;
        }
        return this.getMemberName().compareTo(other.getMemberName());
    }
}
