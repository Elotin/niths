package no.niths.services.interfaces;

import no.niths.domain.FadderGroup;

public interface FadderGroupService extends GenericService<FadderGroup> {
	
	void updateForFadderLeaderAndChildren(FadderGroup group);
	
}